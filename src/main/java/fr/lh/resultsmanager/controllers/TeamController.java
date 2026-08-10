package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.response.TeamResponseDto;
import fr.lh.resultsmanager.mapper.TeamMapper;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.service.TeamService;
import fr.lh.resultsmanager.dtos.request.TeamRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/teams")
@Tag(name = "Team", description = "Team management")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    @Operation(operationId = "putTeam", summary= "Save a new team in database")
    public ResponseEntity<TeamResponseDto> putTeam(@RequestBody TeamRequestDto teamRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(teamService.createTeam(teamRequestDto)));
    }

    @PostMapping(value = "/bulk")
    @Operation(operationId = "putTeams", summary= "Save a list of teams in database")
    public ResponseEntity<List<TeamResponseDto>> putTeams(@RequestBody List<TeamRequestDto> teamsDto){
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(teamService.createTeams(teamsDto)));
    }

    @GetMapping(value = "/{id}")
    @Operation(operationId = "findTeamById", summary= "Get a team by its teamId")
    public ResponseEntity<TeamResponseDto> findTeamById(@PathVariable(value ="id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(teamService.getTeamById(id)));
    }

    @GetMapping
    @Operation(operationId = "getAllTeams", summary= "Get all the teams")
    public ResponseEntity<List<TeamResponseDto>> getAllTeams(){
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(teamService.getAllTeams()));
    }

}
