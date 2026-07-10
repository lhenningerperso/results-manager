package fr.lh.resultsmanager.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionRequestDto {

    private String season;
    private Long championshipId;

}
