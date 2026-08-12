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
    @Column(name = "EXTERNAL_ID", nullable = false, unique = true)
    private Long externalId;
    @ManyToOne(optional = false)
    @JoinColumn(name="MATCHDAY_ID", nullable = false)
    private MatchDay matchDay;
    @ManyToOne(optional = false)
    @JoinColumn(name="HOME_TEAM_ID", nullable = false)
    private Team homeTeam;
    @ManyToOne(optional = false)
    @JoinColumn(name="AWAY_TEAM_ID", nullable = false)
    private Team awayTeam;
    @Column(name="HOME_SCORE")
    private Integer homeScore;
    @Column(name="AWAY_SCORE")
    private Integer awayScore;
    @Column(name="STATUS")
    private Status status;

    @Builder
    private Match(MatchDay matchDay, Long externalId, Team homeTeam, Team awayTeam, Integer homeScore, Integer awayScore, Status status){
        this.matchDay=matchDay;
        this.externalId=externalId;
        this.homeTeam=homeTeam;
        this.awayTeam=awayTeam;
        this.homeScore=homeScore;
        this.awayScore=awayScore;
        this.status=status;
    }

}
