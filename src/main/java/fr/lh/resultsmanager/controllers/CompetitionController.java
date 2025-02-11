package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.CompetitionDto;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.service.CompetitionService;
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
@Tag(name = "Competition", description = "Competitions management")
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @PostMapping(value = "/competition")
    @Operation(operationId = "putCompetition", summary= "Save a new competition in database")
    public ResponseEntity<Object> putDivision(@RequestBody CompetitionDto competitionDto){
        Competition competitionSaved = new Competition();
        try {
            competitionSaved = competitionService.createCompetition(competitionDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(competitionSaved);
    }

    @GetMapping(value = "/competitions")
    @Operation(operationId = "getAllCompetitions", summary= "Get all the competitions")
    public ResponseEntity<Object> getAllCompetitions(){
        List<Competition> competitions = new ArrayList<>();
        try {
            competitions = competitionService.getAllCompetitions();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(competitions);
    }

}
