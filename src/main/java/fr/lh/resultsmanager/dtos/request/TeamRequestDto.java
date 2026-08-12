package fr.lh.resultsmanager.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamRequestDto {

    @NotBlank(message="Team name is required")
    private String name;
    @NotBlank(message = "Short name is required")
    private String shortName;
    @NotBlank(message = "City is required")
    private String city;

}
