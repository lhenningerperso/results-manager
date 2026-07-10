package fr.lh.resultsmanager.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDayRequestDto {

    private Long competitionId;
    private String number;
}
