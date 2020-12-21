package com.example.metro.repository;

import com.example.metro.entity.Line;
import com.example.metro.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    Station findOneById(Long id);

    List<Station> findAllByLine(Line line);

}
