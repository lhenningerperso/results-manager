package fr.lh.resultsmanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "health-check", description = "Health Check")
public class HealthCheckController {

    @Value("${fr.lh.resultsmanager.version}")
    private String projectVersion;

    @GetMapping(value = "/health-check")
    @Operation(operationId = "health-check", summary= "Health Check of the API")
    public ResponseEntity<String> healthcheck() {
        return ResponseEntity.ok("""
                                API OK

                                Version %s

                                """
                .formatted(projectVersion));
    }

}
