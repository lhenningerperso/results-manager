package fr.lh.resultsmanager.dtos.external;

public record ExternalLeagueDto(
        Long id,
        String name,
        String country,
        Integer season,
        String round
) {}
