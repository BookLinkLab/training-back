package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.bomb.BombDto;
import com.booklink.trainingback.dto.bomb.CreateBombDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Bomb;
import com.booklink.trainingback.model.BombType;
import com.booklink.trainingback.repository.BombRepository;
import com.booklink.trainingback.service.BombService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BombServiceImpl implements BombService {
    private final BombRepository bombRepository;

    public BombServiceImpl(BombRepository bombRepository) {
        this.bombRepository = bombRepository;
    }

    @Override
    public BombDto createBomb(CreateBombDto bomb) {
        Bomb bombToSave = Bomb.from(bomb);
        Bomb savedBomb = bombRepository.save(bombToSave);
        return BombDto.from(savedBomb);
    }

    @Override
    public BombDto getBomb(Long id) {
        Optional<Bomb> optionalBomb = bombRepository.findById(id);
        Bomb bomb = optionalBomb.orElseThrow(() -> new NotFoundException("Bomb %d not found".formatted(id)));
        return BombDto.from(bomb);
    }

    @Override
    public List<BombDto> getAllBombs() {
        List<Bomb> bombs = bombRepository.findAll();
        return bombs.stream().map(BombDto::from).toList();
    }

    @Override
    public BombDto changeBombName(Long id, String name) {
        Bomb bomb = bombRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bomb %d not found".formatted(id)));
        bomb.setName(name);
        Bomb savedBomb = bombRepository.save(bomb);
        return BombDto.from(savedBomb);
    }

    @Override
    public void deleteBomb(Long id) {
        bombRepository.deleteById(id);
    }

    @Override
    public List<BombDto> getBombsByType(BombType type) {
        List<Bomb> bombs = bombRepository.findByType(type);
        return bombs.stream().map(BombDto::from).toList();
    }
}
