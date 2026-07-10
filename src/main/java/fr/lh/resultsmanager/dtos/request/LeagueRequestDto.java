package fr.lh.resultsmanager.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueRequestDto {

    private String label;
    private int level;
    private String country;
    private String group;
}
