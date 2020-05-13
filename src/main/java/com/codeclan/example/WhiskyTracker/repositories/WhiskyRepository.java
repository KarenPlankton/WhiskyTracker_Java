package com.codeclan.example.WhiskyTracker.repositories;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhiskyRepository extends JpaRepository<Whisky, Long> {

    List<Whisky> findByYear(int year);

    List<Whisky> findByDistilleryNameAndAge(String distilleryName, int age);

    List<Whisky> findByDistilleryRegion(String distilleryRegion);

    List<Whisky> findByNameContaining(String nameContains);

    List<Whisky> findByNameStartingWith(String nameStartsWith);
}
