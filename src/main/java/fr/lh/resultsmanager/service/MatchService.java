package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.MatchRequestDto;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    MatchDayService matchDayService;

    public Match createMatch(MatchRequestDto matchRequestDto){
        Match match = Match.builder()
                .matchDay(matchDayService.getMatchDayById(matchRequestDto.getMatchdayId()))
                .homeTeam(teamService.getTeamById(matchRequestDto.getHomeTeamId()))
                .awayTeam(teamService.getTeamById(matchRequestDto.getAwayTeamId()))
                .homeScore(matchRequestDto.getHomeScore())
                .awayScore(matchRequestDto.getAwayScore())
                .status(matchRequestDto.getStatus())
                .build();
        return matchRepository.save(match);
    }

    public List<Match> createMatchs(List<MatchRequestDto> matchRequestDtos){
        List<Match> matches = matchRequestDtos.stream()
                .map(matchRequestDto -> Match.builder()
                    .matchDay(matchDayService.getMatchDayById(matchRequestDto.getMatchdayId()))
                    .homeTeam(teamService.getTeamById(matchRequestDto.getHomeTeamId()))
                    .awayTeam(teamService.getTeamById(matchRequestDto.getAwayTeamId()))
                    .homeScore(matchRequestDto.getHomeScore())
                    .awayScore(matchRequestDto.getAwayScore())
                    .status(matchRequestDto.getStatus())
                    .build())
                .toList();
        return matchRepository.saveAll(matches);
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId).orElseThrow(() -> new EntityNotFoundException("Match not found"));
    }

    public List<Match> getAllGames() {
        return matchRepository.findAll();
    }
}
