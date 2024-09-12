package com.PharmStock.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PharmStock.entity.Drug;
import com.PharmStock.repository.DrugRepository;
import com.PharmStock.repository.UserRepository;

@Service
public class DrugService {

    @Autowired
    public DrugRepository drugRepository;

    @Autowired
    public UserRepository userRepository;

    public List<Drug> searchByName(String name){
        return drugRepository.findByNameContainingIgnoreCaseOrderByStockDesc(name);
    }

    //create new drug
    //name != null, location != null, stock > 0, drug name !exist
    public Optional<Drug> createDrug(Drug drug){
        if(drug.getName() == null || drug.getName().isEmpty() || drug.getLocation() == null || drug.getLocation().isEmpty()){
            return Optional.empty();
        }
        
        if(drug.getStock() < 0){
            return Optional.empty();
        }

        Optional<Drug> foundDrug = drugRepository.findById(drug.getDrugId());
        if(foundDrug.isPresent()){
            return Optional.empty();
        }

        Drug savedDrug = drugRepository.save(drug);
        return Optional.of(savedDrug);
    }

    //Get All Drugs
    public List<Drug> getAllDrugs(){
        return drugRepository.findAll();
    }

    //get drug by Id
    public Optional<Drug> getDrugById(Integer drugId){
        return drugRepository.findById(drugId);
    }

    //Delete drug by Id
    public boolean deleteDrug(Integer drugId){
        if(drugRepository.findById(drugId).isPresent()){
            drugRepository.deleteById(drugId);
            return true;
        }
        return false;
    }

    //update drug stock
    //drug id exists
    public boolean updateDrugStock(Integer drugId, Integer newStockAmount){
        if(drugRepository.findById(drugId).isPresent()){
            Optional<Drug> optDrug = drugRepository.findById(drugId);
            Drug drug = optDrug.get();
            drug.setStock(newStockAmount);
            drugRepository.save(drug);
            return true;
        }
        return false;
    }

    //get all drugs by Stock amount
    public List<Drug> getDrugsByStockAmount(Integer drugId){
        return drugRepository.findByDrugIdOrderByStockDesc(drugId);
    }
    
}
