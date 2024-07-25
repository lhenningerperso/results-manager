package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.GameDto;
import fr.lh.resultsmanager.model.Game;
import fr.lh.resultsmanager.service.GamesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Games", description = "Games management")
public class GamesController {

    @Autowired
    GamesService gamesService;

    @PostMapping(value = "/game")
    @Operation(operationId = "putGame", summary= "Save a new game in database")
    public ResponseEntity<Object> putGame(@RequestBody GameDto gameDto){
        Game gameSaved = new Game();
        try {
            gameSaved = gamesService.createGame(gameDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameSaved);
    }

    @PostMapping(value = "/games")
    @Operation(operationId = "putGames", summary= "Save a list of games in database")
    public ResponseEntity<Object> putGames(@RequestBody List<GameDto> gamesDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gamesService.createGames(gamesDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/games")
    @Operation(operationId = "getAllGames", summary= "Get all the games")
    public ResponseEntity<Object> getAllGames(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(gamesService.getAllGames());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
