package com.example.metro.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_station_a", insertable = false, updatable = false)
    private Station A; // previous

    @ManyToOne
    @JoinColumn(name = "id_station_b", insertable = false, updatable = false)
    private Station B; // next

    @Column
    private Integer time;

    /**
     * @return true if stations {@link #A} and {@link #B} has not equals colors
     */
    public boolean isMulticolor() {
        return !A.getColor().equals(B.getColor());
    }

    /**
     * @return true if {@param station} equals {@link #A} or {@link #B}
     */
    public boolean hasStation(Station station) {
        return A.equals(station) || B.equals(station);
    }

    /**
     * @return false if {@param from} equals {@link #A} and {@param to} equals {@link #B}, true if reverse
     */
    public boolean isReverse(Station from, Station to) {
        if (A.equals(from) && B.equals(to))
            return false;
        else if (A.equals(to) && B.equals(from))
            return true;

        throw new IllegalArgumentException("Station \"from\" or \"to\" not equal stations in link");
    }
}
