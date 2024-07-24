package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHAMPIONSHIP")
public class Championship {

    @Id
    @SequenceGenerator(name = "championship_seq",
            sequenceName = "championship_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "championship_seq")
    @Getter
    @Setter
    @Column(name="ID")
    private Long id;
    @Getter @Setter
    @Column(name="LABEL")
    private String label;
    @Getter @Setter
    @Column(name="LEVEL")
    private int level;
    @Getter @Setter
    @Column(name="COUNTRY")
    private String country;
}
