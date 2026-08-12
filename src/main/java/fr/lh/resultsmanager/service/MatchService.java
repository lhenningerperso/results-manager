package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.MatchRequestDto;
import fr.lh.resultsmanager.dtos.request.MatchUpdateRequestDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamService teamService;
    private final MatchDayService matchDayService;

    public Match createMatch(MatchRequestDto matchRequestDto){
        return matchRepository.save(buildMatch(matchRequestDto));
    }

    public List<Match> createMatches(List<MatchRequestDto> matchRequestDtos){
        return matchRepository.saveAll(
                matchRequestDtos.stream()
                        .map(this::buildMatch)
                        .toList());
    }

    public Match updateMatch(Long id, MatchUpdateRequestDto dto) {
        Match match = getMatchById(id);
        match.setHomeScore(dto.getHomeScore());
        match.setAwayScore(dto.getAwayScore());
        match.setStatus(dto.getStatus());
        return matchRepository.save(match);
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId).orElseThrow(() -> new ResourceNotFoundException("Match with id " + matchId + " not found"));
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    private Match buildMatch(MatchRequestDto dto) {
        return Match.builder()
                .matchDay(matchDayService.getMatchDayById(dto.getMatchdayId()))
                .homeTeam(teamService.getTeamById(dto.getHomeTeamId()))
                .awayTeam(teamService.getTeamById(dto.getAwayTeamId()))
                .homeScore(dto.getHomeScore())
                .awayScore(dto.getAwayScore())
                .status(dto.getStatus())
                .build();
    }
}
