package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.LeagueResponseDto;
import fr.lh.resultsmanager.model.League;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeagueMapper {

    public LeagueResponseDto toDto(League league) {
        return LeagueResponseDto.builder()
                .id(league.getId())
                .label(league.getLabel())
                .level(league.getLevel())
                .country(league.getCountry())
                .group(league.getGroup())
                .build();
    }

    public List<LeagueResponseDto> toDto(List<League> leagues){
        return leagues.stream()
                .map(this::toDto)
                .toList();
    }
}
