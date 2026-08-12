package fr.lh.resultsmanager.dtos.external;

public record ExternalTeamResponse(
        ExternalTeamDto team,
        ExternalVenueDto venue
) {
}
