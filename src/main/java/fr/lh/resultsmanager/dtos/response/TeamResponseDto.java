package fr.lh.resultsmanager.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TeamResponseDto {

    private Long id;
    private String teamLabel;
    private String teamCity;

}
