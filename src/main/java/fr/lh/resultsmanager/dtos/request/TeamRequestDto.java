package fr.lh.resultsmanager.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamRequestDto {

    @NotBlank(message="Team label is required")
    private String teamLabel;
    private String teamCity;

}
