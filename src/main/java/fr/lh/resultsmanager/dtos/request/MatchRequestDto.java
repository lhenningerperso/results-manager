package fr.lh.resultsmanager.dtos.request;

import fr.lh.resultsmanager.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchRequestDto {

    private Long matchdayId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeScore;
    private Integer awayScore;
    private Status status;
}
