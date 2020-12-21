package com.example.metro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteFilter {
    private Long query;
    private Long from;
    private Long to;
    private int limit = 0;
}
