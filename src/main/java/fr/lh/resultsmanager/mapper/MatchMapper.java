package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.MatchDaySummaryDto;
import fr.lh.resultsmanager.dtos.response.MatchResponseDto;
import fr.lh.resultsmanager.dtos.response.ScoreDto;
import fr.lh.resultsmanager.dtos.response.TeamSummaryDto;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchMapper {

    public MatchResponseDto toDto(Match match) {
        return MatchResponseDto.builder()
                .id(match.getId())
                .matchday(toMatchDaySummary(match.getMatchDay()))
                .homeTeam(toTeamSummary(match.getHomeTeam()))
                .awayTeam(toTeamSummary(match.getAwayTeam()))
                .score(toScore(match))
                .status(match.getStatus())
                .build();
    }

    public List<MatchResponseDto> toDto(List<Match> matches){
        return matches.stream()
                .map(this::toDto)
                .toList();
    }

    private MatchDaySummaryDto toMatchDaySummary(MatchDay matchDay) {
        return new MatchDaySummaryDto(
                matchDay.getId(),
                matchDay.getNumber()
        );
    }

    private TeamSummaryDto toTeamSummary(Team team) {
        return new TeamSummaryDto(
                team.getId(),
                team.getName(),
                team.getShortName()
        );
    }

    private ScoreDto toScore(Match match) {
        return new ScoreDto(
                match.getHomeScore(),
                match.getAwayScore()
        );
    }
}
