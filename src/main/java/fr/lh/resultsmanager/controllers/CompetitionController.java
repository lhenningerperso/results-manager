package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.request.CompetitionRequestDto;
import fr.lh.resultsmanager.dtos.response.CompetitionResponseDto;
import fr.lh.resultsmanager.mapper.CompetitionMapper;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.service.CompetitionService;
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
@RequestMapping(path = "/competitions")
@Tag(name = "Competition", description = "Competitions management")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;

    @PostMapping
    @Operation(operationId = "postCompetition", summary= "Save a new competition in database")
    public ResponseEntity<CompetitionResponseDto> putDivision(
            @Valid @RequestBody CompetitionRequestDto competitionRequestDto){
        Competition competitionSaved = competitionService.createCompetition(competitionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionMapper.toDto(competitionSaved));
    }

    @GetMapping
    @Operation(operationId = "getAllCompetitions", summary= "Get all the competitions")
    public ResponseEntity<List<CompetitionResponseDto>> getAllCompetitions(){
        List<Competition> competitions = competitionService.getAllCompetitions();
        return ResponseEntity.status(HttpStatus.OK).body(competitionMapper.toDto(competitions));
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getCompetitionById", summary= "Get a competition by its id")
    public ResponseEntity<CompetitionResponseDto> getCompetitionById(@PathVariable Long id){
        return ResponseEntity.ok(competitionMapper.toDto(competitionService.getCompetitionById(id)));
    }

}
