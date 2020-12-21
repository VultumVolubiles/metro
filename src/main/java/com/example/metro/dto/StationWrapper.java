package com.example.metro.dto;

import com.example.metro.entity.Station;
import com.example.metro.enums.MetroLineColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationWrapper {
    private Long id;
    private String name;
    private MetroLineColor color;

    public StationWrapper(Station station) {
        id = station.getId();
        name = station.getName();
        color = station.getColor();
    }
}
