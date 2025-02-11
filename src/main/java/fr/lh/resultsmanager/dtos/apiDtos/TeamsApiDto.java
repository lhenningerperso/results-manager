package fr.lh.resultsmanager.dtos.apiDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamsApiDto {
    private TeamApiDto home;
    private TeamApiDto away;

}
