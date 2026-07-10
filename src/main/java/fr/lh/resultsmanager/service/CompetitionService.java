package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.CompetitionRequestDto;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    LeaguesService leaguesService;

    public Competition createCompetition(CompetitionRequestDto competitionRequestDto){
        Competition competition = Competition.builder()
                .season(competitionRequestDto.getSeason())
                .league(leaguesService.getLeagueById(competitionRequestDto.getChampionshipId()))
                .build();
        return competitionRepository.save(competition);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Competition getCompetitionById(Long competitionId) {
        return competitionRepository.findById(competitionId).orElseThrow(() -> new EntityNotFoundException("Division not found"));
    }

}
