package com.PharmStock.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.PharmStock.entity.Drug;
import com.PharmStock.entity.User;
import com.PharmStock.service.DrugService;
import com.PharmStock.service.UserService;

@RestController
public class PharmStockController {

    @Autowired
    private DrugService drugService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ModelAndView homePage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView searchDrugs(String query){
        List<Drug> drugs = drugService.searchByName(query);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("drugs", drugs);
        return mav;
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        if(userService.getUserByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(409).build();
        }

        Optional<User> createdUser = userService.createUser(user);

        if(createdUser.isPresent()){
            return ResponseEntity.ok(createdUser.get());
        }
        return ResponseEntity.badRequest().build();
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        Optional<User> loggedInUser = userService.login(user.getUsername(), user.getPassword());

        if(loggedInUser.isPresent()){
            return ResponseEntity.ok(loggedInUser.get());
        }

        return ResponseEntity.status(401).build();

    }

    //create new drug
    @PostMapping("/drugs")
    public ResponseEntity<Drug> createNewDrug(@RequestBody Drug drug){
        Optional<Drug> createdDrug = drugService.createDrug(drug);

        if(createdDrug.isPresent()){
            return ResponseEntity.ok(createdDrug.get());
        }

        return ResponseEntity.badRequest().build();
    }

    //get all drugs
    @GetMapping("/drugs")
    public ResponseEntity<List<Drug>> getAllDrugs(){
        List<Drug> allDrugs = drugService.getAllDrugs();
        return ResponseEntity.ok(allDrugs);
    }

    //get drug by Id
    @GetMapping("/drugs/{drug_id}")
    public ResponseEntity<List<Drug>> getDrugById(@PathVariable Integer drug_id){
        List<Drug> drug = drugService.getDrugsByStockAmount(drug_id);
        return ResponseEntity.ok(drug);
    }

    //delete drug
    @DeleteMapping("/drugs/{drug_id}")
    public ResponseEntity<Integer> deleteDrugById(@PathVariable Integer drug_id){
        boolean deletedDrug = drugService.deleteDrug(drug_id);

        if(deletedDrug){
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    //update drug stock
    @PatchMapping("/drugs/{drug_id}")
    public ResponseEntity<Integer> updateDrugById(@PathVariable Integer drug_id, Integer newStockAmount){
        boolean updatedDrug = drugService.updateDrugStock(drug_id, newStockAmount);

        if(updatedDrug){
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }

    }

    
}
