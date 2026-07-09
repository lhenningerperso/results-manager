package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.MatchDayDto;
import fr.lh.resultsmanager.dtos.MatchDto;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDayService {

    @Autowired
    MatchDayRepository matchDayRepository;

    public Match createMatchDay(MatchDayDto matchDayDto){
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

    public MatchDay getMatchDayById(Long matchDayId) {
        return matchDayRepository.findById(matchDayId).orElseThrow(() -> new EntityNotFoundException("Matchday not found"));
    }
}
