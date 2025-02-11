package fr.lh.resultsmanager.dtos.apiDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamApiDto {

    private int id;
    private String name;
    private String logo;
    private boolean winner;
}
