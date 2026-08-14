package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.MatchDayRequestDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchDayService {

    private final MatchDayRepository matchDayRepository;
    private final CompetitionService competitionService;

    public MatchDay createMatchDay(MatchDayRequestDto matchDayRequestDto){
        MatchDay matchDay = MatchDay.builder()
                .competition(competitionService.getCompetitionById(matchDayRequestDto.getCompetitionId()))
                .label(matchDayRequestDto.getLabel())
                .position(matchDayRequestDto.getPosition())
                .build();
        return matchDayRepository.save(matchDay);
    }

    public List<MatchDay> createMatchDays(List<MatchDayRequestDto> matchDayRequestDtos){
        List<MatchDay> matchDays = matchDayRequestDtos.stream()
                .map(matchDayRequestDto -> MatchDay.builder()
                        .competition(competitionService.getCompetitionById(matchDayRequestDto.getCompetitionId()))
                        .label(matchDayRequestDto.getLabel())
                        .position(matchDayRequestDto.getPosition())
                        .build())
                .toList();
        return matchDayRepository.saveAll(matchDays);
    }

    public List<MatchDay> getAllMatchDays(){ return matchDayRepository.findAll(); }

    public MatchDay getMatchDayById(Long matchDayId) {
        return matchDayRepository.findById(matchDayId).orElseThrow(() -> new ResourceNotFoundException("MatchDay with id " + matchDayId + " not found"));
    }
}
