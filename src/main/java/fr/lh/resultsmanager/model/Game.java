package fr.lh.resultsmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GAMES")
public class Game {


    @Id
    @SequenceGenerator(name = "game_seq",
            sequenceName = "game_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @Column(name="ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="COMPETITION_ID")
    private Competition competition;
    @Column(name="MATCHDAY")
    private String matchday;
    @ManyToOne
    @JoinColumn(name="LOCAL_TEAM")
    private Team localTeam;
    @ManyToOne
    @JoinColumn(name="VISITOR_TEAM")
    private Team visitorTeam;
    @Column(name="LOCAL_SCORE")
    private int localScore;
    @Column(name="VISITOR_SCORE")
    private int visitorScore;

    public Game() {
    }

    public Game(Long id, Competition competition, String matchday, Team localTeam, Team visitorTeam, int localScore, int visitorScore) {
        this.id = id;
        this.competition = competition;
        this.matchday = matchday;
        this.localTeam = localTeam;
        this.visitorTeam = visitorTeam;
        this.localScore = localScore;
        this.visitorScore = visitorScore;
    }

}
