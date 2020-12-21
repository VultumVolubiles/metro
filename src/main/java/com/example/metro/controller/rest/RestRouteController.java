package com.example.metro.controller.rest;

import com.example.metro.dto.Route;
import com.example.metro.dto.RouteFilter;
import com.example.metro.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RestRouteController {
    private final RouteService service;

    @PostMapping("search")
    public List<Route> search(@RequestBody RouteFilter filter) {
        return service.searchRoutes(filter.getFrom(), filter.getTo(), filter.getLimit());
    }
}
