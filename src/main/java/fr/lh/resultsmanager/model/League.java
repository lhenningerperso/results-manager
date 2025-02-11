package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LEAGUES")
public class League {

    @Id
    @SequenceGenerator(name = "leagues_seq",
            sequenceName = "leagues_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagues_seq")
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
    @Getter @Setter
    @Column(name="LEAGUE_GROUP")
    private String group;
}
