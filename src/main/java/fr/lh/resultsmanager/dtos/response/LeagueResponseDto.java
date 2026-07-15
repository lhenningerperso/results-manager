package fr.lh.resultsmanager.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LeagueResponseDto {

    private Long id;
    private String label;
    private int level;
    private String country;
    private String group;
}
