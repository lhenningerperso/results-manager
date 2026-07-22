package fr.lh.resultsmanager.controllers;

import fr.lh.resultsmanager.dtos.request.MatchDayRequestDto;
import fr.lh.resultsmanager.dtos.response.MatchDayResponseDto;
import fr.lh.resultsmanager.mapper.MatchDayMapper;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.service.MatchDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/matchdays")
@Tag(name = "MatchDay", description = "MatchDays management")
public class MatchDayController {

    private final MatchDayService matchDayService;
    private final MatchDayMapper matchDayMapper;

    @PostMapping
    @Operation(operationId = "postMatchDay", summary= "Save a new matchday in database")
    public ResponseEntity<MatchDayResponseDto> postMatchDay(
            @Valid @RequestBody MatchDayRequestDto matchDayRequestDto){
        MatchDay matchDaySaved = matchDayService.createMatchDay(matchDayRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(matchDayMapper.toDto(matchDaySaved));
    }

    @GetMapping
    @Operation(operationId = "getAllMatchDays", summary= "Get all the matchdays")
    public ResponseEntity<List<MatchDayResponseDto>> getAllMatchDays(){
        List<MatchDay> competitions = matchDayService.getAllMatchDays();
        return ResponseEntity.status(HttpStatus.OK).body(matchDayMapper.toDto(competitions));
    }

    @GetMapping("/{id}")
    @Operation(operationId = "getMatchDayById", summary= "Get a matchday by its id")
    public ResponseEntity<MatchDayResponseDto> getMatchDayById(@PathVariable Long id){
        return ResponseEntity.ok(matchDayMapper.toDto(matchDayService.getMatchDayById(id)));
    }
}
