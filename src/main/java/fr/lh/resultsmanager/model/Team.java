package fr.lh.resultsmanager.model;

import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(name="ID")
    private Long id;
    @Column(name="TEAMLABEL")
    private String teamLabel;
    @Column(name="TEAMCITY")
    private String teamCity;

    @Builder
    private Team(String teamLabel, String teamCity){
        this.teamLabel=teamLabel;
        this.teamCity=teamCity;
    }

}
