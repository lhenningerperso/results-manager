package fr.lh.resultsmanager.dtos.external.result;

public record ImportResultDto(
        int total,
        int created,
        int ignored,
        int failed
) {
}
