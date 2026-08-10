package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.MatchResponseDto;
import fr.lh.resultsmanager.model.Match;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchMapper {

    public MatchResponseDto toDto(Match match) {
        return MatchResponseDto.builder()
                .id(match.getId())
                .matchdayId(match.getMatchDay().getId())
                .homeTeamId(match.getHomeTeam().getId())
                .awayTeamId(match.getAwayTeam().getId())
                .homeScore(match.getHomeScore())
                .awayScore(match.getAwayScore())
                .status(match.getStatus())
                .build();
    }

    public List<MatchResponseDto> toDto(List<Match> matches){
        return matches.stream()
                .map(this::toDto)
                .toList();
    }

}
