package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.request.TeamRequestDto;
import fr.lh.resultsmanager.dtos.response.TeamResponseDto;
import fr.lh.resultsmanager.mapper.TeamMapper;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/{id}")
    @Operation(
            operationId = "updateTeam",
            summary = "Update a team"
    )
    public ResponseEntity<TeamResponseDto> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamRequestDto teamRequestDto) {

        Team team = teamService.updateTeam(id, teamRequestDto);

        return ResponseEntity.ok(
                teamMapper.toDto(team)
        );
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
