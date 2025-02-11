package fr.lh.resultsmanager.dtos.apiDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueApiDto {

    private int id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private int season;
    private String round;
    private boolean standings;
}
