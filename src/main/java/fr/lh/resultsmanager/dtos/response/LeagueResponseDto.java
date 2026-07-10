package fr.lh.resultsmanager.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueResponseDto {

    private String label;
    private int level;
    private String country;
    private String group;
}
