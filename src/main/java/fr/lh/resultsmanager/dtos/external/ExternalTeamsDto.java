package fr.lh.resultsmanager.dtos.external;

public record ExternalTeamsDto(
        ExternalTeamDto home,
        ExternalTeamDto away
) {}
