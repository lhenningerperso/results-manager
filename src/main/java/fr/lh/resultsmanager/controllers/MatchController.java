package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.MatchDto;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.service.MatchService;
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
public class MatchController {

    @Autowired
    MatchService matchService;

    @PostMapping(value = "/game")
    @Operation(operationId = "putGame", summary= "Save a new game in database")
    public ResponseEntity<Object> putGame(@RequestBody MatchDto matchDto){
        Match matchSaved;
        try {
            matchSaved = matchService.createMatch(matchDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(matchSaved);
    }

    @PostMapping(value = "/games")
    @Operation(operationId = "putGames", summary= "Save a list of games in database")
    public ResponseEntity<Object> putGames(@RequestBody List<MatchDto> gamesDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(matchService.createMatchs(gamesDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/games")
    @Operation(operationId = "getAllGames", summary= "Get all the games")
    public ResponseEntity<Object> getAllGames(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(matchService.getAllGames());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
