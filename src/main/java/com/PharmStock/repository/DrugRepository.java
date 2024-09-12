package com.PharmStock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PharmStock.entity.Drug;

public interface DrugRepository extends JpaRepository<Drug, Integer>{
    List<Drug> findByDrugIdOrderByStockDesc(Integer drugId);
    List<Drug> findByNameContainingIgnoreCaseOrderByStockDesc(String name);
}
