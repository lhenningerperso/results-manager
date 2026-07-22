package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.CompetitionResponseDto;
import fr.lh.resultsmanager.model.Competition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitionMapper {

    public CompetitionResponseDto toDto(Competition comp) {
        return CompetitionResponseDto.builder()
                .id(comp.getId())
                .season(comp.getSeason())
                .leagueId(comp.getLeague().getId())
                .build();
    }

    public List<CompetitionResponseDto> toDto(List<Competition> comps){
        return comps.stream()
                .map(this::toDto)
                .toList();
    }
}
