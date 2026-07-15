package fr.lh.resultsmanager.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueRequestDto {

    @NotBlank(message = "Label is required")
    private String label;
    @Min(value = 1, message = "Level must be greater than or equal to 1")
    private Integer level;
    @NotBlank(message = "Country is required")
    private String country;
    private String group;
}
