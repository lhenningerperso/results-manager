package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.ChampionshipDto;
import fr.lh.resultsmanager.model.Championship;
import fr.lh.resultsmanager.repository.ChampionshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipService {

    @Autowired
    ChampionshipRepository championshipRepository;

    public Championship createChampionship(ChampionshipDto championshipDto){
        Championship championship = new Championship();
        championship.setLabel(championshipDto.getLabel());
        championship.setLevel(championshipDto.getLevel());
        championship.setCountry(championshipDto.getCountry());
        return championshipRepository.save(championship);
    }

    public List<Championship> getAllChampionships() {
        return championshipRepository.findAll();
    }

    public Championship getChampionshipById(Long championshipId) {
        return championshipRepository.findById(championshipId).orElseThrow(() -> new EntityNotFoundException("Championship not found"));
    }

}
