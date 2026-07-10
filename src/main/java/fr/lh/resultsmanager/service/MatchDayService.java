package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.MatchDayDto;
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

    public MatchDay createMatchDay(MatchDayDto matchDayDto){
        MatchDay matchDay = MatchDay.builder()
                .competition(competitionService.getCompetitionById(matchDayDto.getCompetitionId()))
                .number(matchDayDto.getNumber())
                .build();
        return matchDayRepository.save(matchDay);
    }

    public List<MatchDay> createMatchs(List<MatchDayDto> matchDayDtos){
        List<MatchDay> matchDays = matchDayDtos.stream()
                .map(matchDayDto -> MatchDay.builder()
                        .competition(competitionService.getCompetitionById(matchDayDto.getCompetitionId()))
                        .number(matchDayDto.getNumber())
                        .build())
                .toList();
        return matchDayRepository.saveAll(matchDays);
    }

    public MatchDay getMatchDayById(Long matchDayId) {
        return matchDayRepository.findById(matchDayId).orElseThrow(() -> new EntityNotFoundException("Matchday not found"));
    }
}
