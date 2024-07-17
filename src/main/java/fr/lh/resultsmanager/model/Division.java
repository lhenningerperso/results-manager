package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DIVISION")
public class Division {

    @Id
    @SequenceGenerator(name = "division_seq",
            sequenceName = "division_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_seq")
    @Getter @Setter
    @Column(name="DIVISION_ID")
    private Long divisionId;
    @Getter @Setter
    @Column(name="LABEL")
    private String divisionLabel;
    @Getter @Setter
    @Column(name="LEVEL")
    private int divisionLevel;
    @Getter @Setter
    @Column(name="COUNTRY")
    private String country;

    public Division() {
    }

    public Division(Long divisionId, String divisionLabel, int divisionLevel) {
        this.divisionId = divisionId;
        this.divisionLabel = divisionLabel;
        this.divisionLevel = divisionLevel;
    }
}
