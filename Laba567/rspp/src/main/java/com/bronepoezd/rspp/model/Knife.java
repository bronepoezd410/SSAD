package com.bronepoezd.rspp.model;

import jakarta.persistence.*;

@Entity
public class Knife {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID
    private Long id;
    private String name;
    private String material;
    private Double length;

    public Knife() {}

    public Knife(String name, String material, Double length) {
        this.name = name;
        this.material = material;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
