package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.client.ApiFootballClient;
import fr.lh.resultsmanager.dtos.apiDtos.MatchApiDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Import", description = "Endpoints to import data")
public class ImportController {

    @GetMapping(value="/matches")
    @Operation(operationId = "matches", summary = "TODO : summary", description ="TODO : description ")
    public ResponseEntity<String> matchesImport(
            @Parameter(description = "League id", required = true, example = "62") @RequestParam int idLeague,
            @Parameter(description = "Round", required = true, example = "Regular Season - 1") @RequestParam String round,
            @Parameter(description = "Season", required = true, example = "2024") @RequestParam String season
    ){
        ApiFootballClient apiClient = new ApiFootballClient(new RestTemplateBuilder());
        List<MatchApiDto> matches = apiClient.getMatchsByRoundAndLeagueAndSeason(round,idLeague,season);
        return ResponseEntity.ok("Import OK");
    }

}