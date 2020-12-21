package com.example.metro.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Route {
    private List<LinkWrapper> links = new ArrayList<>();

    public Route() {}

    public Route(List<LinkWrapper> links) {
        this.links = new ArrayList<>(links);
    }

    public void append(LinkWrapper link) {
        links.add(link);
    }

    public List<LinkWrapper> getLinks() {
        return links;
    }

    public int getWeight() {
        int weight = 0;
        for (LinkWrapper link : links)
            weight+=link.getTime();

        return weight;
    }

    private String withColor(StationWrapper station) {
        return "[" + station.getColor() + "] " + station.getName();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (LinkWrapper link : links){
            if (result.length() == 0) {
                result.append(withColor(link.getA()));
            }
            result.append(" --").append(link.getTime()).append("--> ")
                    .append(withColor(link.getB()));
        }
        result.append(" = ").append(getWeight()).append(" minutes");

        return result.toString();
    }

    /**
     * @param reverse if true sorting by ASC, else by DESC
     * @return comparator
     */
    public static Comparator<Route> getComparator(boolean reverse) {
        if (reverse)
            return (o1, o2) -> Integer.compare(o2.getWeight(), o1.getWeight());
        else
            return Comparator.comparingInt(Route::getWeight);
    }
}
