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
    private int homeScore;
    private int awayScore;
    private Status status;
}
