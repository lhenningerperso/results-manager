package fr.lh.resultsmanager.dtos.apiDtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FixtureApiDto {
    private int id;
    private String referee;
    private String timezone;
    private String date;
    private long timestamp;
    private PeriodsApiDto periodsApiDto;
    private VenueApiDto venueApiDto;
    private StatusApiDto statusApiDto;
}
