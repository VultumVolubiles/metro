package com.example.metro.service;

import com.example.metro.dto.LinkWrapper;
import com.example.metro.dto.Route;
import com.example.metro.entity.Link;
import com.example.metro.entity.Station;
import com.example.metro.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final StationRepository repository;
    private final QueryService queryService;

    /**
     * Find routes between {@param from} and {@param to} points, after a successful search save this query
     *
     * @param from route starting point
     * @param to route endpoint
     * @param limit results max count
     * @return found routes
     */
    public List<Route> searchRoutes(Long from, Long to, int limit) {
        Station fromStation = repository.findOneById(from);
        if(fromStation == null)
            throw new NullPointerException("Station From not found");

        Station toStation = repository.findOneById(to);
        if(toStation == null)
            throw new NullPointerException("Station To not found");

        if (fromStation.getColor().equals(toStation.getColor())) {
            Route route = solidColorRoute(fromStation, toStation);
            queryService.save(from, to);
            return Collections.singletonList(route);
        } else {
            List<Route> routes = multiColorRoutes(fromStation, toStation, new HashSet<>());
            routes.sort(Route.getComparator(false));

            queryService.save(from, to);
            if (limit < 1)
                return routes;
            else
                return routes.stream().limit(limit).collect(Collectors.toList());
        }
    }

    /**
     * Generate route between two {@link Station} located on the same {@link com.example.metro.entity.Line}
     *
     * @param from route starting point
     * @param to route endpoint
     * @return route
     */
    private Route solidColorRoute(Station from, Station to) {
        Route route = new Route();
        Station current = from;

        if (from.getOrderInLine() > to.getOrderInLine()) // check is reverse order
            while (current.getOrderInLine() > to.getOrderInLine()) {
                int order = current.getOrderInLine();

                // find Link with previous Station
                Optional<Link> optional = current.getLinks()
                        .stream()
                        .filter(l -> order > l.getA().getOrderInLine() && !l.isMulticolor())
                        .findFirst();

                if (!optional.isPresent())
                    throw new IllegalArgumentException("Reached extreme station in route");

                Link link = optional.get();
                route.append(new LinkWrapper(link, true)); // if From > To = reverse order

                current = link.getA(); // set params for next iteration
            }
        else
            while (current.getOrderInLine() < to.getOrderInLine()) {
                int order = current.getOrderInLine();

                // find Link with next Station
                Optional<Link> optional = current.getLinks()
                        .stream()
                        .filter(l -> order < l.getB().getOrderInLine() && !l.isMulticolor())
                        .findFirst();

                if (!optional.isPresent())
                    throw new IllegalArgumentException("Reached extreme station in route");

                Link link = optional.get();
                route.append(new LinkWrapper(link));

                current = link.getB(); // set params for next iteration
            }

        return route;
    }

    /**
     * Find routes between {@param from} and {@param to} points, if they located on different lines
     *
     * @param from route starting point
     * @param to routes endpoint
     * @param exclude visited stations
     * @return all found routes
     */
    private List<Route> multiColorRoutes(Station from, Station to, Set<Station> exclude) {
        List<Route> result = new ArrayList<>();
        // Adding station from which we came to list of stations where we have already been
        Set<Station> afterExclude = new HashSet<>(exclude);
        Set<Station> beforeExclude = new HashSet<>(exclude);
        afterExclude.add(from);
        beforeExclude.add(from);

        // Find all stations that are on same line with 'from' and are connected to other metro lines
        List<Station> toHandle = repository.findAllByLine(from.getLine())
                .stream()
                .filter(Station::hasMulticolorLinks)
                .collect(Collectors.toList());

        List<Station> beforeFrom = toHandle.stream()
                .filter(s -> s.getOrderInLine() < from.getOrderInLine())
                .sorted((o1, o2) -> Integer.compare(o2.getOrderInLine(), o1.getOrderInLine()))
                .collect(Collectors.toList());
        List<Station> afterFrom = toHandle.stream()
                .filter(s -> s.getOrderInLine() >= from.getOrderInLine())
                .sorted(Comparator.comparingInt(Station::getOrderInLine))
                .collect(Collectors.toList());

        beforeFrom.forEach(s -> {
            // Find all Links of this station which connected to other metro lines stations
            beforeExclude.add(s);
            List<Link> multicolorLinks = s.getLinks().stream().filter(Link::isMulticolor).collect(Collectors.toList());
            for (Link l : multicolorLinks)
                handleMulticolorLink(from, to, result, beforeExclude, l, s);
        });

        afterFrom.forEach(s -> {
            // Find all Links of this station which connected to other metro lines stations
            afterExclude.add(s);
            List<Link> multicolorLinks = s.getLinks().stream().filter(Link::isMulticolor).collect(Collectors.toList());
            for (Link l : multicolorLinks)
                handleMulticolorLink(from, to, result, afterExclude, l, s);
        });

        return result;
    }

    /**
     * Get next {@link Station} in {@param link} for this route. If next {@link Station} has color equals
     * color of {@param to} build route to next station, else use next station as {@param from} in call
     * {@link #multiColorRoutes(Station, Station, Set)}
     *
     * @param from route starting point
     * @param to routes endpoint
     * @param result possible routes
     * @param exclude visited stations
     * @param s current station
     * @param link handled link
     */
    private void handleMulticolorLink(Station from, Station to, List<Route> result, Set<Station> exclude,
                                      Link link, Station s) {
        boolean reverse;
        if (link.getA().equals(s))
            reverse = link.isReverse(s, link.getB());
        else
            reverse = link.isReverse(s, link.getA());

        // Get next station from current link
        Station next = reverse ? link.getA() : link.getB();

        // if already have been in next station then skip her
        if (exclude.contains(next)) {
            return;
        }

        // If s != from, build route from 'from' to 's', else create new Route
        Route route = s.equals(from) ? new Route() : solidColorRoute(from, s);
        route.append(new LinkWrapper(link, reverse));

        // Check if the next line has the same color as endpoint of our route
        if (next.getColor().equals(to.getColor())) {
            if (link.hasStation(to)) {// If next station is route endpoint - add
                result.add(route);
            }
            else { // build route from next station to route endpoint
                Route end = solidColorRoute(next, to);
                result.add(concat(route, end));
            }
        } else {
            // find options for continuing the route and combine them with the route begin
            List<Route> endings = multiColorRoutes(next, to, exclude);
            result.addAll(concat(route, endings));
        }
    }

    /**
     * Concat a begin of route with various endings
     *
     * @param beginRoute begin route part
     * @param endRoutes ending parts
     * @return combined routes
     */
    public List<Route> concat(Route beginRoute, List<Route> endRoutes) {
        List<Route> results = new ArrayList<>();
        endRoutes.forEach(end -> {
            Route route = new Route(beginRoute.getLinks());
            end.getLinks().forEach(route::append);
            results.add(route);
        });

        return results;
    }

    public Route concat(Route beginRoute, Route endRoute) {
        Route result = new Route(beginRoute.getLinks());
        endRoute.getLinks().forEach(result::append);
        return result;
    }
}
