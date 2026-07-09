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
@Table(name = "MATCHS")
public class Match {


    @Id
    @SequenceGenerator(name = "match_seq",
            sequenceName = "match_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_seq")
    @Column(name="ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="MATCHDAY_ID")
    private MatchDay matchDay;
    @ManyToOne
    @JoinColumn(name="HOME_TEAM_ID")
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name="AWAY_TEAM_ID")
    private Team awayTeam;
    @Column(name="HOME_SCORE")
    private int homeScore;
    @Column(name="AWAY_SCORE")
    private int awayScore;
    @Column(name="STATUS")
    private Status status;

    @Builder
    private Match(MatchDay matchDay, Team homeTeam, Team awayTeam, int homeScore, int awayScore, Status status){
        this.matchDay=matchDay;
        this.homeTeam=homeTeam;
        this.awayTeam=awayTeam;
        this.homeScore=homeScore;
        this.awayScore=awayScore;
        this.status=status;
    }

}
