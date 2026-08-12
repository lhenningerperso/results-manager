package fr.lh.resultsmanager.dtos.external;

public record ExternalVenueDto(
        Long id,
        String name,
        String address,
        String city,
        Integer capacity,
        String surface,
        String image
) {
}
