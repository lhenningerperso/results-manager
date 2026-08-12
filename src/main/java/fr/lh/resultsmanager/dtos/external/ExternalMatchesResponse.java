package fr.lh.resultsmanager.dtos.external;

import java.util.List;

public record ExternalMatchesResponse(
        ExternalPaging paging,
        List<ExternalMatchDto> response
) {}
