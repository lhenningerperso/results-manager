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
    private Long matchdayId;
    private Long homeTeamId;
    private Long awayTeamId;
    private int homeScore;
    private int awayScore;
    private Status status;
}
