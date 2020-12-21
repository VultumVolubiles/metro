package com.example.metro.entity;

import com.example.metro.enums.MetroLineColor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "order_in_line")
    private int orderInLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_line")
    private Line line;

    @OneToMany(mappedBy = "A", fetch = FetchType.LAZY)
    private Set<Link> linksAsA = new HashSet<>();

    @OneToMany(mappedBy = "B", fetch = FetchType.LAZY)
    private Set<Link> linksAsB = new HashSet<>();

    public Set<Link> getLinks() {
        Set<Link> links = new HashSet<>(linksAsA);
        links.addAll(linksAsB);
        return links;
    }

    public MetroLineColor getColor() {
        return line.getColor();
    }

    public boolean hasMulticolorLinks () {
        return getLinks().stream().anyMatch(Link::isMulticolor);
    }


}