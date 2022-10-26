package fr.lh.resultsmanager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Test {

    @GetMapping(value = "/test")
    @Operation(operationId = "test", summary= "Only for testing purpose")
    public String test(){
        return "Hello world!";
    }

}
