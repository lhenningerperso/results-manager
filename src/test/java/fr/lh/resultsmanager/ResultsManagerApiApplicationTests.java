package fr.lh.resultsmanager;

import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import fr.lh.resultsmanager.repository.LeagueRepository;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import fr.lh.resultsmanager.repository.MatchRepository;
import fr.lh.resultsmanager.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class ResultsManagerApiApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres =
			new PostgreSQLContainer<>("postgres:17");

	@Autowired
	private LeagueRepository leagueRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private CompetitionRepository competitionRepository;

	@Autowired
	private MatchDayRepository matchDayRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void databaseIsInitializedWithExpectedData() {

		assertThat(leagueRepository.count()).isEqualTo(3);
		assertThat(teamRepository.count()).isEqualTo(23);
		assertThat(competitionRepository.count()).isEqualTo(2);
		assertThat(matchDayRepository.count()).isEqualTo(5);
		assertThat(matchRepository.count()).isEqualTo(18);
	}

	@Test
	void initialTeamsContainExpectedData() {

		Team psg = teamRepository.findByExternalId(85L)
				.orElseThrow();

		assertThat(psg.getName()).isEqualTo("Paris Saint-Germain");
		assertThat(psg.getShortName()).isEqualTo("PSG");
		assertThat(psg.getCity()).isEqualTo("Paris");

		Team saintEtienne = teamRepository.findByExternalId(1063L)
				.orElseThrow();

		assertThat(saintEtienne.getName()).isEqualTo("AS Saint-étienne");
		assertThat(saintEtienne.getShortName()).isEqualTo("ASSE");
		assertThat(saintEtienne.getCity()).isEqualTo("Saint-étienne");
	}

	@Test
	void initialLeaguesContainExpectedData() {

		League ligue1 = leagueRepository.findByExternalId(61L)
				.orElseThrow();

		assertThat(ligue1.getLabel()).isEqualTo("Ligue 1");
		assertThat(ligue1.getLevel()).isEqualTo(1);
		assertThat(ligue1.getCountry()).isEqualTo("France");
	}

	@Test
	void initialMatchesContainExpectedData() {

		Match match = matchRepository.findByExternalId(1213754L)
				.orElseThrow();

		assertThat(match.getHomeScore()).isEqualTo(1);
		assertThat(match.getAwayScore()).isEqualTo(4);
	}
}
