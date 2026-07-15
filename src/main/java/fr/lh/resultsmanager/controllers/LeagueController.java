package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.request.LeagueRequestDto;
import fr.lh.resultsmanager.dtos.response.LeagueResponseDto;
import fr.lh.resultsmanager.mapper.LeagueMapper;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.service.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/leagues")
@Tag(name = "Leagues", description = "Leagues management")
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueMapper leagueMapper;

    @PostMapping
    @Operation(operationId = "createLeague", summary= "Save a new league in database")
    public ResponseEntity<LeagueResponseDto> createLeague(
            @Valid @RequestBody LeagueRequestDto leagueRequestDto){
        League leagueSaved = leagueService.createLeague(leagueRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(leagueMapper.toDto(leagueSaved));
    }

    @GetMapping
    @Operation(operationId = "getAllLeagues", summary= "Get all the leagues")
    public ResponseEntity<List<LeagueResponseDto>> getAllLeagues() {
        return ResponseEntity.ok(leagueMapper.toDto(leagueService.getAllLeagues()));
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getLeagueById", summary= "Get a league by its id")
    public ResponseEntity<LeagueResponseDto> getLeagueById(@PathVariable Long id){
        return ResponseEntity.ok(leagueMapper.toDto(leagueService.getLeagueById(id)));
    }
}
