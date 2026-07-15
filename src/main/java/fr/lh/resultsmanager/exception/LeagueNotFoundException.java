package fr.lh.resultsmanager.exception;

public class LeagueNotFoundException extends RuntimeException {

    public LeagueNotFoundException(Long id) {
        super("League with id " + id + " not found");
    }

}
