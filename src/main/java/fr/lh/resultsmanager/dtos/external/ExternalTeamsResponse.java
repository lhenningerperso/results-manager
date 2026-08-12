package fr.lh.resultsmanager.dtos.external;

import java.util.List;

public record ExternalTeamsResponse(
        String get,
        ExternalTeamsParameters parameters,
        List<String> errors,
        int results,
        ExternalPaging paging,
        List<ExternalTeamResponse> response
) {
}
