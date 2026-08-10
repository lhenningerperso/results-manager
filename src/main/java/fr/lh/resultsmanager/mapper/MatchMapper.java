package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.MatchDaySummaryDto;
import fr.lh.resultsmanager.dtos.response.MatchResponseDto;
import fr.lh.resultsmanager.dtos.response.ScoreDto;
import fr.lh.resultsmanager.dtos.response.TeamSummaryDto;
import fr.lh.resultsmanager.model.Match;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchMapper {

    public MatchResponseDto toDto(Match match) {
        return MatchResponseDto.builder()
                .id(match.getId())
                .matchday(new MatchDaySummaryDto(match.getMatchDay().getId(),match.getMatchDay().getNumber()))
                .homeTeam(new TeamSummaryDto(match.getHomeTeam().getId(),match.getHomeTeam().getName(), match.getHomeTeam().getShortName()))
                .awayTeam(new TeamSummaryDto(match.getAwayTeam().getId(),match.getAwayTeam().getName(),match.getAwayTeam().getShortName()))
                .score(new ScoreDto(match.getHomeScore(), match.getAwayScore()))
                .status(match.getStatus())
                .build();
    }

    public List<MatchResponseDto> toDto(List<Match> matches){
        return matches.stream()
                .map(this::toDto)
                .toList();
    }

}
