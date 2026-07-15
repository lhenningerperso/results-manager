package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.LeagueRequestDto;
import fr.lh.resultsmanager.dtos.response.LeagueResponseDto;
import fr.lh.resultsmanager.exception.LeagueNotFoundException;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.repository.LeaguesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    @Autowired
    LeaguesRepository leaguesRepository;

    public League createLeague(LeagueRequestDto leagueRequestDto){
        League league = new League();
        league.setLabel(leagueRequestDto.getLabel());
        league.setLevel(leagueRequestDto.getLevel());
        league.setCountry(leagueRequestDto.getCountry());
        league.setGroup(leagueRequestDto.getGroup());
        return leaguesRepository.save(league);
    }

    public List<League> getAllLeagues() {
        return leaguesRepository.findAll();
    }

    public League getLeagueById(Long leagueId) {
        return leaguesRepository.findById(leagueId).orElseThrow(() -> new LeagueNotFoundException(leagueId));
    }

}
