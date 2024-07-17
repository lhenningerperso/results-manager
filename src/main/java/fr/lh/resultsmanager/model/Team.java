package fr.lh.resultsmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TEAM")
public class Team {

    @Id
    @SequenceGenerator(name = "team_seq",
            sequenceName = "team_sequence",
            initialValue = 5, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @Column(name="TEAMID")
    private Long teamId;
    @Column(name="TEAMLABEL")
    private String teamLabel;
    @Column(name="TEAMCITY")
    private String teamCity;
    @ManyToOne
    @JoinColumn(name="DIVISION_ID")
    private Division division;

    public Team() {
    }

    public Team(Long teamId, String teamLabel, String teamCity, Division division) {
        this.teamId = teamId;
        this.teamLabel = teamLabel;
        this.teamCity = teamCity;
        this.division = division;
    }
}
