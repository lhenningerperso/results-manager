package fr.lh.resultsmanager.dtos;

import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDto {

    private Long competitionId;
    private String matchday;
    private Long localTeamId;
    private Long visitorTeamId;
    private int localScore;
    private int visitorScore;
}
