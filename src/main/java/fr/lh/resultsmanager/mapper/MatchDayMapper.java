package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.MatchDayResponseDto;
import fr.lh.resultsmanager.model.MatchDay;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDayMapper {

    public MatchDayResponseDto toDto(MatchDay md) {
        return MatchDayResponseDto.builder()
                .id(md.getId())
                .competitionId(md.getCompetition().getId())
                .number(md.getNumber())
                .build();
    }

    public List<MatchDayResponseDto> toDto(List<MatchDay> mds){
        return mds.stream()
                .map(this::toDto)
                .toList();
    }

}
