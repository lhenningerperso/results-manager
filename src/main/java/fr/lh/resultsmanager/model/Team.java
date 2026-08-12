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
@Table(
        name = "TEAM",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_team_external_id", columnNames = "EXTERNAL_ID")
        }
)
public class Team {

    @Id
    @SequenceGenerator(name = "team_seq",
            sequenceName = "team_sequence",
            initialValue = 5, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @Column(name="ID")
    private Long id;
    @Column(name = "EXTERNAL_ID")
    private Long externalId;
    @Column(name="NAME")
    private String name;
    @Column(name="SHORTNAME")
    private String shortName;
    @Column(name="CITY")
    private String city;

    @Builder
    private Team(Long externalId, String name, String city, String shortName){
        this.externalId=externalId;
        this.name=name;
        this.city=city;
        this.shortName=shortName;
    }

}
