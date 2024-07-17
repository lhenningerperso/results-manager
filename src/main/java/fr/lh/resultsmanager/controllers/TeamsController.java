package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.service.TeamService;
import fr.lh.resultsmanager.dtos.TeamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Teams", description = "Teams management")
public class TeamsController {

    @Autowired
    TeamService teamService;

    @GetMapping(value = "/team/{id}")
    @Operation(operationId = "findTeamById", summary= "Get a team by its teamId")
    public ResponseEntity<Object> findTeamById(@PathVariable(value ="id") Long id){
        Team team = new Team();
        try {
            team = teamService.getTeamById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @GetMapping(value = "/teams")
    @Operation(operationId = "getAllTeams", summary= "Get all the teams")
    public ResponseEntity<Object> getAllTeams(){
        List<Team> teams = new ArrayList<>();
        try {
            teams = teamService.getAllTeams();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    @PostMapping(value = "/team")
    @Operation(operationId = "putTeam", summary= "Save a new team in database")
    public ResponseEntity<Object> putTeam(@RequestBody TeamDto teamDto){
        Team teamSaved;
        try {
            teamSaved = teamService.createTeam(teamDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(teamSaved);
    }

}
