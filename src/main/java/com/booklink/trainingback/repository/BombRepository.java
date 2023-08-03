package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Bomb;
import com.booklink.trainingback.model.BombType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BombRepository extends JpaRepository<Bomb, Long> {
    Bomb findByName(String name);
    List<Bomb> findByType(BombType type);
}
