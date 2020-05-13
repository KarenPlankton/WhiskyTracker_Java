package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/distilleries")
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping
    public ResponseEntity<List<Distillery>> getAllDistilleries (
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "whiskyAge", required = false) Integer whiskyAge,
            @RequestParam(name = "whiskyNameStarts", required = false) String whiskyNameStarts
    ){
        if (region != null){
            return new ResponseEntity<>(distilleryRepository.findByRegion(region), HttpStatus.OK);
        }

        if (whiskyAge != null){
            return new ResponseEntity<>(distilleryRepository.findByWhiskiesAge(whiskyAge), HttpStatus.OK);
        }

        if (whiskyNameStarts != null){
            return new ResponseEntity<>(distilleryRepository.findByWhiskiesNameStartingWith(whiskyNameStarts), HttpStatus.OK);
        }

        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/are12")
    public ResponseEntity<List<Distillery>> getAllWhiskyWithAge12 () {
        return new ResponseEntity<>(distilleryRepository.findByWhiskiesAge(12), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Distillery>> getDistilleryById(@PathVariable Long id){
        return new ResponseEntity<>(distilleryRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Distillery> postDistillery(@RequestBody Distillery newDistillery){
        return new ResponseEntity<Distillery>(distilleryRepository.save(newDistillery), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Distillery> putDistillery(@PathVariable Long id, @RequestBody Distillery distillery){
        if(distillery.getId().longValue() != id) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(distilleryRepository.save(distillery), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Distillery>> deleteDistillery(@PathVariable Long id){
        distilleryRepository.deleteById(id);
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

}
