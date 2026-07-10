package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.MatchDayRequestDto;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDayService {

    @Autowired
    MatchDayRepository matchDayRepository;

    @Autowired
    CompetitionService competitionService;

    public MatchDay createMatchDay(MatchDayRequestDto matchDayRequestDto){
        MatchDay matchDay = MatchDay.builder()
                .competition(competitionService.getCompetitionById(matchDayRequestDto.getCompetitionId()))
                .number(matchDayRequestDto.getNumber())
                .build();
        return matchDayRepository.save(matchDay);
    }

    public List<MatchDay> createMatchs(List<MatchDayRequestDto> matchDayRequestDtos){
        List<MatchDay> matchDays = matchDayRequestDtos.stream()
                .map(matchDayRequestDto -> MatchDay.builder()
                        .competition(competitionService.getCompetitionById(matchDayRequestDto.getCompetitionId()))
                        .number(matchDayRequestDto.getNumber())
                        .build())
                .toList();
        return matchDayRepository.saveAll(matchDays);
    }

    public MatchDay getMatchDayById(Long matchDayId) {
        return matchDayRepository.findById(matchDayId).orElseThrow(() -> new EntityNotFoundException("Matchday not found"));
    }
}
