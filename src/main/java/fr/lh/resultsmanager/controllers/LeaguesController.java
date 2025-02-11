package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.LeagueDto;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.service.LeaguesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Leagues", description = "Leagues management")
public class LeaguesController {

    @Autowired
    LeaguesService leaguesService;

    @PostMapping(value = "/league")
    @Operation(operationId = "putLeague", summary= "Save a new league in database")
    public ResponseEntity<Object> putLeague(@RequestBody LeagueDto leagueDto){
        League leagueSaved = new League();
        try {
            leagueSaved = leaguesService.createLeague(leagueDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(leagueSaved);
    }

    @GetMapping(value = "/leagues")
    @Operation(operationId = "getAllLeagues", summary= "Get all the leagues")
    public ResponseEntity<Object> getAllLeagues(){
        List<League> leagues = new ArrayList<>();
        try {
            leagues = leaguesService.getAllLeagues();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(leagues);
    }
}
