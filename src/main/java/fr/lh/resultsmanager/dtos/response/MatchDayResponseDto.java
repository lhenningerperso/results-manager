package fr.lh.resultsmanager.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDayResponseDto {

    private Long id;
    private Long competitionId;
    private String number;
}
