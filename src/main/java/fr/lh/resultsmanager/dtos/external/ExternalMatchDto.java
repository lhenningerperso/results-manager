package fr.lh.resultsmanager.dtos.external;

public record ExternalMatchDto(
        ExternalFixtureDto fixture,
        ExternalLeagueDto league,
        ExternalTeamsDto teams,
        ExternalGoalsDto goals
) {}
