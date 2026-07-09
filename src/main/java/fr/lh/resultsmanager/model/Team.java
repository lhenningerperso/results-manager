package fr.lh.resultsmanager.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
