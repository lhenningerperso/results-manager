package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.external.ExternalMatchDto;
import fr.lh.resultsmanager.dtos.external.ExternalMatchesResponse;
import fr.lh.resultsmanager.dtos.external.ExternalStatusDto;
import fr.lh.resultsmanager.dtos.external.ExternalTeamDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.model.Status;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import fr.lh.resultsmanager.repository.LeagueRepository;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import fr.lh.resultsmanager.repository.MatchRepository;
import fr.lh.resultsmanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchImportService {

    private final LeagueRepository leagueRepository;
    private final CompetitionRepository competitionRepository;
    private final MatchDayRepository matchDayRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public void importMatches(ExternalMatchesResponse response) {

        for (ExternalMatchDto dto : response.response()) {
            importMatch(dto);
        }
    }

    private void importMatch(ExternalMatchDto dto) {

        League league = findLeague(dto);

        Competition competition = findCompetition(dto, league);

        MatchDay matchDay = findMatchDay(dto, competition);

        Team homeTeam = findTeam(dto.teams().home());

        Team awayTeam = findTeam(dto.teams().away());

        createOrUpdateMatch(
                dto,
                matchDay,
                homeTeam,
                awayTeam
        );
    }

    private League findLeague(ExternalMatchDto dto) {

        Long externalId = dto.league().id();

        return leagueRepository.findByExternalId(externalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "League with externalId " + externalId + " not found"
                        )
                );
    }

    private Competition findCompetition(
            ExternalMatchDto dto,
            League league) {

        String season = String.valueOf(dto.league().season());

        return competitionRepository
                .findByLeagueAndSeason(league, season)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Competition for league " + league.getId()
                                        + " and season " + season
                        )
                );
    }

    private String convertRoundToMatchDayNumber(String round) {

        Pattern pattern = Pattern.compile("Regular Season - (\\d+)");
        Matcher matcher = pattern.matcher(round);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Unsupported round format: " + round
            );
        }

        return "J" + matcher.group(1);
    }

    private MatchDay findMatchDay(
            ExternalMatchDto dto,
            Competition competition) {

        String number = convertRoundToMatchDayNumber(
                dto.league().round()
        );

        return matchDayRepository
                .findByCompetitionAndNumber(competition, number)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MatchDay " + number
                                        + " not found for competition "
                                        + competition.getId()
                        )
                );
    }

    private Team findTeam(ExternalTeamDto dto) {

        return teamRepository
                .findByExternalId(dto.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Team with external id "
                                        + dto.id()
                                        + " not found"
                        )
                );
    }

    private void createOrUpdateMatch(
            ExternalMatchDto dto,
            MatchDay matchDay,
            Team homeTeam,
            Team awayTeam) {

        Match match = matchRepository
                .findByExternalId(dto.fixture().id())
                .orElseGet(() -> Match.builder()
                        .externalId(dto.fixture().id())
                        .matchDay(matchDay)
                        .homeTeam(homeTeam)
                        .awayTeam(awayTeam)
                        .homeScore(dto.goals().home())
                        .awayScore(dto.goals().away())
                        .status(toStatus(dto.fixture().status()))
                        .build());

        match.setExternalId(dto.fixture().id());
        match.setMatchDay(matchDay);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setHomeScore(dto.goals().home());
        match.setAwayScore(dto.goals().away());
        match.setStatus(toStatus(dto.fixture().status()));

        matchRepository.save(match);
    }

    private Status toStatus(ExternalStatusDto status) {

        return switch (status.shortStatus()) {

            case "NS" -> Status.SCHEDULED;

            case "1H", "2H", "HT", "ET", "P" -> Status.LIVE;

            case "FT", "AET", "PEN" -> Status.FINISHED;

            case "PST", "CANC" -> Status.CANCELLED;

            default -> throw new IllegalArgumentException(
                    "Unsupported external status: "
                            + status.shortStatus()
            );
        };
    }
}
