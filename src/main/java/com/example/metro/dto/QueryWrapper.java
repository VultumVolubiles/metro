package com.example.metro.dto;

import com.example.metro.entity.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryWrapper {
    private Long id;
    private StationWrapper from;
    private StationWrapper to;
    private Long time;

    public QueryWrapper() {}

    public QueryWrapper(Query query) {
        id = query.getId();
        from = new StationWrapper(query.getFrom());
        to = new StationWrapper(query.getTo());
        time = query.getTime();
    }

    @Override
    public String toString() {
        return from.getName() + " -> " +to.getName();
    }
}
