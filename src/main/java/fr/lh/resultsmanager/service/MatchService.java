package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.MatchDto;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
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

    public Match createMatch(MatchDto matchDto){
        Match match = Match.builder()
                .matchDay(matchDayService.getMatchDayById(matchDto.getMatchdayId()))
                .homeTeam(teamService.getTeamById(matchDto.getHomeTeamId()))
                .awayTeam(teamService.getTeamById(matchDto.getAwayTeamId()))
                .homeScore(matchDto.getHomeScore())
                .awayScore(matchDto.getAwayScore())
                .status(matchDto.getStatus())
                .build();
        return matchRepository.save(match);
    }

    public List<Match> createMatchs(List<MatchDto> matchDtos){
        List<Match> matches = matchDtos.stream()
                .map(matchDto -> Match.builder()
                    .matchDay(matchDayService.getMatchDayById(matchDto.getMatchdayId()))
                    .homeTeam(teamService.getTeamById(matchDto.getHomeTeamId()))
                    .awayTeam(teamService.getTeamById(matchDto.getAwayTeamId()))
                    .homeScore(matchDto.getHomeScore())
                    .awayScore(matchDto.getAwayScore())
                    .status(matchDto.getStatus())
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
