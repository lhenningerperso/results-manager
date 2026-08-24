package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.LeagueRequestDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public League createLeague(LeagueRequestDto leagueRequestDto){
        League league = League.builder()
                .label(leagueRequestDto.getLabel())
                .level(leagueRequestDto.getLevel())
                .country(leagueRequestDto.getCountry())
                .group(leagueRequestDto.getGroup())
                .build();
        return leagueRepository.save(league);
    }

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeagueById(Long leagueId) {
        return leagueRepository.findById(leagueId).orElseThrow(() -> new ResourceNotFoundException("League with id " + leagueId + " not found"));
    }

}
