package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.GameDto;
import fr.lh.resultsmanager.model.Game;
import fr.lh.resultsmanager.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    CompetitionService competitionService;

    public Game createGame(GameDto gameDto){
        Game game = new Game();
        game.setMatchday(gameDto.getMatchday());
        game.setLocalTeam(teamService.getTeamById(gameDto.getLocalTeamId()));
        game.setVisitorTeam(teamService.getTeamById(gameDto.getVisitorTeamId()));
        game.setCompetition(competitionService.getCompetitionById(gameDto.getCompetitionId()));
        game.setLocalScore(gameDto.getLocalScore());
        game.setVisitorScore(gameDto.getVisitorScore());
        return gamesRepository.save(game);
    }

    public List<Game> createGames(List<GameDto> gamesDto){
        List<Game> games = gamesDto.stream().map(gameDto -> {
            Game game = new Game();
            game.setMatchday(gameDto.getMatchday());
            game.setLocalTeam(teamService.getTeamById(gameDto.getLocalTeamId()));
            game.setVisitorTeam(teamService.getTeamById(gameDto.getVisitorTeamId()));
            game.setCompetition(competitionService.getCompetitionById(gameDto.getCompetitionId()));
            game.setLocalScore(gameDto.getLocalScore());
            game.setVisitorScore(gameDto.getVisitorScore());
            return game;
        }).toList();
        return gamesRepository.saveAll(games);
    }

    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }
}
