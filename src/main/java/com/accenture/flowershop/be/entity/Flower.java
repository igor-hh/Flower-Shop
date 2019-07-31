package com.accenture.flowershop.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "flowers")
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    public Flower() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
