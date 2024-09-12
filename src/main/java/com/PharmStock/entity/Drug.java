package com.PharmStock.entity;

import jakarta.persistence.*;

@Entity
@Table(name="drugs")
public class Drug {

    @Id
    @Column(name="drugId")
    private Integer drugId;

    @Column(name="name")
    private String name;

    @Column(name="stock")
    private Integer stock;

    @Column(name="location")
    private String location;

    

    public Drug() {
    }
   

    public Drug(String name, Integer stock, String location) {
        this.name = name;
        this.stock = stock;
        this.location = location;
    }

    
    public Drug(Integer drugId, String name, Integer stock, String location) {
        this.drugId = drugId;
        this.name = name;
        this.stock = stock;
        this.location = location;
    }


    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    
}
