package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.request.MatchRequestDto;
import fr.lh.resultsmanager.dtos.response.MatchResponseDto;
import fr.lh.resultsmanager.mapper.MatchMapper;
import fr.lh.resultsmanager.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(path = "/matches")
@Tag(name = "Matches", description = "Matches management")
public class MatchController {

    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @PostMapping
    @Operation(operationId = "postMatches", summary= "Save a list of matches in database")
    public ResponseEntity<List<MatchResponseDto>> postMatches(@RequestBody List<MatchRequestDto> matchesDto){
        return ResponseEntity.status(HttpStatus.OK).body(matchMapper.toDto(matchService.createMatches(matchesDto)));
    }

    @GetMapping
    @Operation(operationId = "getAllMatches", summary= "Get all matches")
    public ResponseEntity<Object> getAllMatches(){
        return ResponseEntity.status(HttpStatus.OK).body(matchMapper.toDto(matchService.getAllMatches()));
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getMatchById", summary= "Get a match by its id")
    public ResponseEntity<MatchResponseDto> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchMapper.toDto(matchService.getMatchById(id)));
    }

}
