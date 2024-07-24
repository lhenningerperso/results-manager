package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.ChampionshipDto;
import fr.lh.resultsmanager.model.Championship;
import fr.lh.resultsmanager.service.ChampionshipService;
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
@Tag(name = "Championship", description = "Championships management")
public class ChampionshipController {

    @Autowired
    ChampionshipService championshipService;

    @PostMapping(value = "/championship")
    @Operation(operationId = "putChampionship", summary= "Save a new championship in database")
    public ResponseEntity<Object> putDivision(@RequestBody ChampionshipDto championshipDto){
        Championship championshipSaved = new Championship();
        try {
            championshipSaved = championshipService.createChampionship(championshipDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(championshipSaved);
    }

    @GetMapping(value = "/chamionships")
    @Operation(operationId = "getAllChampionships", summary= "Get all the championships")
    public ResponseEntity<Object> getAllChampionships(){
        List<Championship> championships = new ArrayList<>();
        try {
            championships = championshipService.getAllChampionships();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(championships);
    }
}
