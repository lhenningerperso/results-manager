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
    @Column(name="DATE_EVENT")
    private String dateEvent;
    @ManyToOne
    @JoinColumn(name="CHAMPIONSHIP_ID")
    private Championship championship;

    public Competition() {
    }

    public Competition(Long id, String dateEvent, Championship championship) {
        this.id = id;
        this.dateEvent = dateEvent;
        this.championship = championship;
    }
}
