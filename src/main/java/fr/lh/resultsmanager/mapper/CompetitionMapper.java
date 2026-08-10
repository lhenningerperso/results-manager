package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.CompetitionResponseDto;
import fr.lh.resultsmanager.dtos.response.LeagueSummaryDto;
import fr.lh.resultsmanager.model.Competition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitionMapper {

    public CompetitionResponseDto toDto(Competition comp) {
        return CompetitionResponseDto.builder()
                .id(comp.getId())
                .season(comp.getSeason())
                .league(toLeagueSummary(comp))
                .build();
    }

    public List<CompetitionResponseDto> toDto(List<Competition> comps){
        return comps.stream()
                .map(this::toDto)
                .toList();
    }

    private LeagueSummaryDto toLeagueSummary(Competition comp){
        return new LeagueSummaryDto(
                comp.getLeague().getId(),
                comp.getLeague().getLabel()
        );
    }
}
