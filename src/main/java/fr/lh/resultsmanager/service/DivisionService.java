package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.DivisionDto;
import fr.lh.resultsmanager.model.Division;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.DivisionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionService {

    @Autowired
    DivisionRepository divisionRepository;

    public Division createDivision(DivisionDto divisionDto){
        Division division = new Division();
        division.setDivisionLabel(divisionDto.getDivisionLabel());
        division.setDivisionLevel(divisionDto.getDivisionLevel());
        division.setCountry(divisionDto.getCountry());
        return divisionRepository.save(division);
    }

    public List<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }

    public Division getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId).orElseThrow(() -> new EntityNotFoundException("Division not found"));
    }

}
