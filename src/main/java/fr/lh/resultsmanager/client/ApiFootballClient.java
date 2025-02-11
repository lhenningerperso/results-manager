package fr.lh.resultsmanager.client;

import fr.lh.resultsmanager.dtos.apiDtos.MatchApiDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiFootballClient {

    private final RestTemplate restTemplate;
    private final static String AUTH_TOKEN = "135a60cb5cad6a272d6544ada704c840";

    @Autowired
    public ApiFootballClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<MatchApiDto> getMatchsByRoundAndLeagueAndSeason(String round, int idLeague, String season){
        String url = String.format("https://v3.football.api-sports.io/fixtures?league=%d&round=%s&season=%s",idLeague,round,season);
        ResponseEntity<MatchApiDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(null, getHttpHeaders()),
                MatchApiDto[].class
        );
       return response.getBody() != null ? Arrays.asList(response.getBody()) : null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-apisports-key", AUTH_TOKEN);
        return httpHeaders;
    }
}
