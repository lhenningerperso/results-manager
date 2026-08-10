package fr.lh.resultsmanager.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompetitionResponseDto {

    private Long id;
    private String season;
    private LeagueSummaryDto league;

}
