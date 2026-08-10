package fr.lh.resultsmanager.dtos.request;

import fr.lh.resultsmanager.model.Status;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchUpdateRequestDto {

    @Min(value = 0, message = "Score can not be negative")
    private int homeScore;
    @Min(value = 0, message = "Score can not be negative")
    private int awayScore;
    private Status status;
}
