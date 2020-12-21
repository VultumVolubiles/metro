package com.example.metro.service;

import com.example.metro.dto.StationWrapper;
import com.example.metro.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository repository;

    public List<StationWrapper> getAll() {
        return repository.findAll().stream().map(StationWrapper::new).collect(Collectors.toList());
    }
}
