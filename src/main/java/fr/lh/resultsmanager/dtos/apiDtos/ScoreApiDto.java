package fr.lh.resultsmanager.dtos.apiDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScoreApiDto {

    private HalftimeApiDto halftime;
    private HalftimeApiDto fulltime;
    private HalftimeApiDto extratime;
    private HalftimeApiDto penalty;
}
