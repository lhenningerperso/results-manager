package fr.lh.resultsmanager.dtos.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalStatusDto(
        @JsonProperty("long")
        String longStatus,
        @JsonProperty("short")
        String shortStatus
) {}
