package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    @JoinColumn(name="CHAMPIONSHIP_ID")
    private League league;

    public Competition() {
    }

    public Competition(Long id, String season, League league) {
        this.id = id;
        this.season = season;
        this.league = league;
    }
}
