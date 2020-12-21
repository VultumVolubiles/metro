package com.example.metro.controller.rest;

import com.example.metro.dto.QueryWrapper;
import com.example.metro.dto.Route;
import com.example.metro.service.QueryService;
import com.example.metro.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class RestQueryController {
    private final RouteService routeService;
    private final QueryService service;

    @GetMapping("last")
    public List<QueryWrapper> last(@RequestParam(name ="limit", defaultValue = "10", required = false) int limit) {
        return service.getLastQueries(limit);
    }

    @GetMapping("result")
    public List<Route> result(@RequestParam(name = "id") Long id) {
        QueryWrapper query = service.get(id);
        return routeService.searchRoutes(query.getFrom().getId(), query.getTo().getId(), 0);
    }
}
