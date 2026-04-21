package com.web.demo.controls;

import com.web.demo.services.AggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRestController {

    private final AggregatorService aggregatorService;

    public ApiRestController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/fetch")
    public String fetchAll() throws Exception {
        return aggregatorService.getAllResponses();
    }
}
