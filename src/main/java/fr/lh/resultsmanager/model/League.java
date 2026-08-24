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
@Table(name = "LEAGUES")
public class League {

    @Id
    @SequenceGenerator(name = "leagues_seq",
            sequenceName = "leagues_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagues_seq")
    @Column(name="ID")
    private Long id;
    @Column(name = "EXTERNAL_ID")
    private Long externalId;
    @Column(name="LABEL")
    private String label;
    @Column(name="LEVEL")
    private int level;
    @Column(name="COUNTRY")
    private String country;
    @Column(name="LEAGUE_GROUP")
    private String group;

    @Builder
    private League(String label, Long externalId, int level, String country, String group){
        this.label=label;
        this.externalId=externalId;
        this.level=level;
        this.country=country;
        this.group=group;
    }
}
