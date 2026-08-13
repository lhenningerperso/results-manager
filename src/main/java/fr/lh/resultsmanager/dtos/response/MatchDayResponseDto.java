package fr.lh.resultsmanager.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatchDayResponseDto {

    private Long id;
    private CompetitionSummaryDto competition;
    private String label;
}
