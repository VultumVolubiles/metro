package com.example.metro.controller.rest;

import com.example.metro.dto.StationWrapper;
import com.example.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/station")
@RequiredArgsConstructor
public class RestStationController {
    private final StationService service;

    @GetMapping("list")
    public List<StationWrapper> list() {
        return service.getAll();
    }
}
