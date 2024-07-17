package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.DivisionDto;
import fr.lh.resultsmanager.model.Division;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.service.DivisionService;
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
@Tag(name = "Division", description = "Divisions management")
public class DivisionController {

    @Autowired
    DivisionService divisionService;

    @PostMapping(value = "/division")
    @Operation(operationId = "putDivision", summary= "Save a new division in database")
    public ResponseEntity<Object> putDivision(@RequestBody DivisionDto divisionDto){
        Division divisionSaved = new Division();
        try {
            divisionSaved = divisionService.createDivision(divisionDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(divisionSaved);
    }

    @GetMapping(value = "/divisions")
    @Operation(operationId = "getAllDivisions", summary= "Get all the divisions")
    public ResponseEntity<Object> getAllDivisions(){
        List<Division> divisions = new ArrayList<>();
        try {
            divisions = divisionService.getAllDivisions();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(divisions);
    }

}
