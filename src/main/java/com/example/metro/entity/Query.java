package com.example.metro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "queries")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_from_station")
    private Station from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_to_station")
    private Station to;

    private Long time;
}
