package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.TeamDto;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CompetitionService competitionService;

    /**
     * Get team by ID. The service will send the team data else will throw the exception.
     * @param teamId
     * @return CustomerData
     */
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team not found"));
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(TeamDto teamDto){
        Team team = new Team();
        team.setTeamLabel(teamDto.getTeamLabel());
        team.setTeamCity(teamDto.getTeamCity());

        team.setCompetition(competitionService.getCompetitionById(teamDto.getCompetitionId()));
        return teamRepository.save(team);
    }

    public List<Team> createTeams(List<TeamDto> teamsDto){
        List<Team> teams = teamsDto.stream().map(teamDto -> {
            Team team = new Team();
            team.setTeamLabel(teamDto.getTeamLabel());
            team.setTeamCity(teamDto.getTeamCity());
            team.setCompetition(competitionService.getCompetitionById(teamDto.getCompetitionId()));
            return team;
        }).toList();
        return teamRepository.saveAll(teams);
    }

}
