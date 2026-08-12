package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.client.ApiFootballClient;
import fr.lh.resultsmanager.dtos.external.ExternalMatchesResponse;
import fr.lh.resultsmanager.dtos.external.ExternalTeamsResponse;
import fr.lh.resultsmanager.dtos.external.result.ImportResultDto;
import fr.lh.resultsmanager.service.MatchImportService;
import fr.lh.resultsmanager.service.TeamImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/import")
@Tag(name = "Data Import", description = "Import data from external sources")
public class ImportController {

    private final MatchImportService matchImportService;
    private final TeamImportService teamImportService;

    @PostMapping("/matches")
    @Operation(
            operationId = "importMatches",
            summary = "Import matches from external API data"
    )
    public ResponseEntity<Void> importMatches(
            @RequestBody ExternalMatchesResponse response) {

        matchImportService.importMatches(response);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/teams")
    @Operation(
            operationId = "importTeams",
            summary = "Import teams from external API data"
    )
    public ResponseEntity<ImportResultDto> importTeams(
            @RequestBody ExternalTeamsResponse response) {

        return ResponseEntity.ok(
                teamImportService.importTeams(response)
        );
    }

}