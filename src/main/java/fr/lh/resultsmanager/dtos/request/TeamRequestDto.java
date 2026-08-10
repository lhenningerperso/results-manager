package fr.lh.resultsmanager.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamRequestDto {

    @NotBlank(message="Team name is required")
    private String name;
    private String shortName;
    private String city;

}
