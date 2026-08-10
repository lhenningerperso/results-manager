package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.CompetitionSummaryDto;
import fr.lh.resultsmanager.dtos.response.MatchDayResponseDto;
import fr.lh.resultsmanager.model.MatchDay;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDayMapper {

    public MatchDayResponseDto toDto(MatchDay md) {
        return MatchDayResponseDto.builder()
                .id(md.getId())
                .competition(toCompetitionSummaryDto(md))
                .number(md.getNumber())
                .build();
    }

    public List<MatchDayResponseDto> toDto(List<MatchDay> mds){
        return mds.stream()
                .map(this::toDto)
                .toList();
    }

    private CompetitionSummaryDto toCompetitionSummaryDto(MatchDay md){
        return new CompetitionSummaryDto(
                md.getCompetition().getId(),
                md.getCompetition().getLeague().getLabel(),
                md.getCompetition().getSeason()
        );
    }

}
