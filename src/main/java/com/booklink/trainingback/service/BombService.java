package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.bomb.BombDto;
import com.booklink.trainingback.dto.bomb.CreateBombDto;
import com.booklink.trainingback.model.BombType;

import java.util.List;

public interface BombService {
    BombDto createBomb(CreateBombDto bomb);
    BombDto getBomb(Long id);
    List<BombDto> getAllBombs();
    BombDto changeBombName(Long id, String name);
    void deleteBomb(Long id);
    List<BombDto> getBombsByType(BombType type);
}
