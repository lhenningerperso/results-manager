package fr.lh.resultsmanager.dtos.response;

import fr.lh.resultsmanager.model.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatchResponseDto {

    private Long id;
    private MatchDaySummaryDto matchday;
    private TeamSummaryDto homeTeam;
    private TeamSummaryDto awayTeam;
    private ScoreDto score;
    private Status status;
}
