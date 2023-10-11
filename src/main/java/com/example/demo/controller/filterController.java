package com.example.demo.controller;

import com.example.demo.service.FilterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class filterController {
    private final FilterService filterService;

    public filterController(FilterService filterService) {
        this.filterService = filterService;

    }

}
