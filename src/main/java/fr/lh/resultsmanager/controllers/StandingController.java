package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.response.StandingResponseDto;
import fr.lh.resultsmanager.service.StandingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competitions")
@Tag(name = "Standings", description = "Competition standings")
public class StandingController {

    private final StandingService standingService;

    @GetMapping("/{competitionId}/standings/{matchDayLabel}")
    @Operation(
            operationId = "getStandings",
            summary = "Get competition standings after a matchday"
    )
    public ResponseEntity<List<StandingResponseDto>> getStandings(
            @PathVariable Long competitionId,
            @PathVariable String matchDayLabel) {

        return ResponseEntity.ok(
                standingService.getStandings(
                        competitionId,
                        matchDayLabel
                )
        );
    }
}
