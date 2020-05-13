package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/whiskies")
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @GetMapping
    public ResponseEntity<List<Whisky>> getAllWhiskies(
            @RequestParam(name="year", required = false) Integer year,
            @RequestParam(name="age", required = false) Integer age,
            @RequestParam(name="distilleryName", required = false) String distilleryName,
            @RequestParam(name="distilleryRegion", required = false) String distilleryRegion,
            @RequestParam(name="nameContains", required = false) String nameContains,
            @RequestParam(name="nameStartsWith", required =false) String nameStartsWith
    ){
        if (year != null) {
            return new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
        }

        if (distilleryName != null && age != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryNameAndAge(distilleryName, age), HttpStatus.OK);

        }

        if (distilleryRegion != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryRegion(distilleryRegion), HttpStatus.OK);
        }

        if (nameContains != null){
            return new ResponseEntity<>(whiskyRepository.findByNameContaining(nameContains), HttpStatus.OK);
        }

        if (nameStartsWith != null){
            return new ResponseEntity<>(whiskyRepository.findByNameStartingWith(nameStartsWith), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Whisky>> getWhiskyById(@PathVariable Long id){
        return new ResponseEntity<>(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Whisky> postWhisky(@RequestBody Whisky newWhisky){
        return new ResponseEntity<Whisky>(whiskyRepository.save(newWhisky), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Whisky> putWhisky(@PathVariable Long id, @RequestBody Whisky whisky){
        if(whisky.getId().longValue() != id){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(whiskyRepository.save(whisky), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Whisky>> deleteWhisky(@PathVariable Long id){
        whiskyRepository.deleteById(id);
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

}
