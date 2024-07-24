package fr.lh.resultsmanager.model;

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
    @JoinColumn(name="COMPETITION_ID")
    private Competition competition;

    public Team() {
    }

    public Team(Long teamId, String teamLabel, String teamCity, Competition competition) {
        this.teamId = teamId;
        this.teamLabel = teamLabel;
        this.teamCity = teamCity;
        this.competition = competition;
    }
}
