package com.example.metro.controller.view;

import com.example.metro.dto.QueryWrapper;
import com.example.metro.dto.Route;
import com.example.metro.dto.RouteFilter;
import com.example.metro.service.QueryService;
import com.example.metro.service.RouteService;
import com.example.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService service;
    private final RouteService routeService;
    private final QueryService queryService;

    @GetMapping("search")
    public String searchView(WebRequest request, Model model) {
        model.addAttribute("stations", service.getAll());
        model.addAttribute("filter", new RouteFilter());
        model.addAttribute("queries", queryService.getLastQueries(10));
        return "search";
    }

    @PostMapping("search")
    public ModelAndView searchRoutes(@ModelAttribute("filter") RouteFilter filter, Model model,
                                     HttpServletRequest request) {
        List<Route> results = routeService.searchRoutes(filter.getFrom(), filter.getTo(), filter.getLimit());

        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("routes", results);
        modelAndView.addObject("stations", service.getAll());
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("queries", queryService.getLastQueries(10));

        return modelAndView;
    }

    @PostMapping("query")
    public ModelAndView query(@ModelAttribute("query") RouteFilter filter,
                              HttpServletRequest request) {
        QueryWrapper query = queryService.get(filter.getQuery());
        List<Route> results = routeService.searchRoutes(query.getFrom().getId(),
                query.getTo().getId(), filter.getLimit());
        filter.setQuery(null);

        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("routes", results);
        modelAndView.addObject("stations", service.getAll());
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("queries", queryService.getLastQueries(10));

        return modelAndView;
    }
}
