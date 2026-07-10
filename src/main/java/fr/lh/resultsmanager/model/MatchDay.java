package fr.lh.resultsmanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MATCHDAY")
public class MatchDay {

    @Id
    @SequenceGenerator(name = "matchday_seq",
            sequenceName = "matchday_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matchday_seq")
    @Getter
    @Setter
    @Column(name="ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="COMPETITION_ID")
    private Competition competition;
    @Column(name="NUMBER")
    private String number;

    @Builder
    private MatchDay(Competition competition, String number){
        this.competition=competition;
        this.number=number;
    }

}
