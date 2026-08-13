package fr.lh.resultsmanager.dtos.response;

public record StandingResponseDto(
        TeamSummaryDto team,
        int played,
        int wins,
        int draws,
        int losses,
        int goalsFor,
        int goalsAgainst,
        int goalDifference,
        int points
) {
}
