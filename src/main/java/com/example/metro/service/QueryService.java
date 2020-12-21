package com.example.metro.service;

import com.example.metro.dto.QueryWrapper;
import com.example.metro.entity.Query;
import com.example.metro.entity.Station;
import com.example.metro.repository.QueryRepository;
import com.example.metro.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository repository;
    private final StationRepository stationRepository;

    public QueryWrapper get(Long id) {
        Query query = repository.findOneById(id);
        if (query == null)
            throw new NullPointerException("Query not found");

        return new QueryWrapper(query);
    }

    public QueryWrapper save(Long from, Long to) {
        Station fromStation = stationRepository.findOneById(from);
        Station toStation = stationRepository.findOneById(to);
        if (fromStation == null || toStation == null)
            throw new NullPointerException("Station not found");

        Query query = new Query();
        query.setFrom(fromStation);
        query.setTo(toStation);
        long timeNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        query.setTime(timeNow);

        return new QueryWrapper(repository.save(query));
    }

    /**
     * @param limit max count
     * @return last search queries
     */
    public List<QueryWrapper> getLastQueries(int limit) {
        if (limit < 1)
            limit = 10;

        return repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "time")))
                .toList()
                .stream()
                .map(QueryWrapper::new)
                .collect(Collectors.toList());
    }
}
