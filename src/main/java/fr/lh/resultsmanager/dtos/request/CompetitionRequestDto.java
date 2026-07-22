package fr.lh.resultsmanager.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompetitionRequestDto {

    @NotBlank(message="Season label is required")
    private String season;
    private Long leagueId;

}
