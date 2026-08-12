package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.CompetitionRequestDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    LeagueService leagueService;

    public Competition createCompetition(CompetitionRequestDto competitionRequestDto){
        Competition competition = Competition.builder()
                .season(competitionRequestDto.getSeason())
                .league(leagueService.getLeagueById(competitionRequestDto.getLeagueId()))
                .build();
        return competitionRepository.save(competition);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public Competition getCompetitionById(Long competitionId) {
        return competitionRepository.findById(competitionId).orElseThrow(() -> new ResourceNotFoundException("Competition with id " + competitionId + " not found"));
    }

}
