package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.bomb.BombDto;
import com.booklink.trainingback.dto.bomb.CreateBombDto;
import com.booklink.trainingback.model.BombType;
import com.booklink.trainingback.service.BombService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bomb")
public class BombController {
    private final BombService bombService;

    public BombController(BombService bombService) {
        this.bombService = bombService;
    }

    @PostMapping
    public BombDto createBomb(@RequestBody CreateBombDto dto) {
        return bombService.createBomb(dto);
    }

    @GetMapping
    public List<BombDto> getAllBombs() {
        return bombService.getAllBombs();
    }

    @GetMapping("/{id}")
    public BombDto getBomb(@PathVariable Long id) {
        return bombService.getBomb(id);
    }

    @PutMapping("/{id}")
    public BombDto changeBombName(@PathVariable Long id, @RequestParam String name) {
        return bombService.changeBombName(id, name);
    }

    @DeleteMapping
    public void deleteBomb(@RequestParam Long id) {
        bombService.deleteBomb(id);
    }

    @GetMapping("/by-type")
    public List<BombDto> getBombsByType(@RequestParam BombType type) {
        return bombService.getBombsByType(type);
    }
}
