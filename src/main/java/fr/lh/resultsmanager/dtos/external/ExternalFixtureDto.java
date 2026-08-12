package fr.lh.resultsmanager.dtos.external;

public record ExternalFixtureDto(
        Long id,
        String date,
        ExternalStatusDto status
) {}
