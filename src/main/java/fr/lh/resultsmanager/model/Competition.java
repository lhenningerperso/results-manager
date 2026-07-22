package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMPETITION")
public class Competition {

    @Id
    @SequenceGenerator(name = "competition_seq",
            sequenceName = "competition_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competition_seq")
    @Column(name="ID")
    private Long id;
    @Column(name="SEASON")
    private String season;
    @ManyToOne
    @JoinColumn(name="LEAGUE_ID")
    private League league;

    @Builder
    private Competition(String season, League league){
        this.season=season;
        this.league=league;
    }

}
