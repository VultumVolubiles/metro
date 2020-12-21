package com.example.metro.dto;

import com.example.metro.entity.Link;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkWrapper {
    private StationWrapper A;
    private StationWrapper B;
    private int time;
    private boolean reverse;

    public LinkWrapper(Link link) {
        this(link, false);
    }

    public LinkWrapper(Link link, boolean reverse) {
        if (reverse) {
            A = new StationWrapper(link.getB());
            B = new StationWrapper(link.getA());
        } else {
            A = new StationWrapper(link.getA());
            B = new StationWrapper(link.getB());
        }
        time = link.getTime();
    }
}
