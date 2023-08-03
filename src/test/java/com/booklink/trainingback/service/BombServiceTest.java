package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.bomb.BombDto;
import com.booklink.trainingback.dto.bomb.CreateBombDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.BombType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BombServiceTest {
    @Autowired
    private BombService bombService;

    @Test
    void happyPathTest() {
        assertTrue(bombService.getAllBombs().isEmpty());

        CreateBombDto createBombDto = CreateBombDto.builder()
                .name("Little Boy")
                .type(BombType.ATOMIC)
                .radius(10.0)
                .build();
        BombDto savedBomb = bombService.createBomb(createBombDto);

        List<BombDto> allBombs = bombService.getAllBombs();
        assertFalse(allBombs.isEmpty());
        assertEquals(1, allBombs.size());

        BombDto myBomb = allBombs.get(0);
        assertEquals(myBomb, savedBomb);

        BombDto savedUpdatedBomb = bombService.changeBombName(myBomb.getId(), "Fat Man");

        List<BombDto> updatedBombs = bombService.getAllBombs();
        assertFalse(updatedBombs.isEmpty());
        assertEquals(1, updatedBombs.size());

        BombDto myUpdatedBomb = updatedBombs.get(0);
        assertEquals(myUpdatedBomb, savedUpdatedBomb);

        bombService.deleteBomb(myUpdatedBomb.getId());
        assertTrue(bombService.getAllBombs().isEmpty());
    }

    @Test
    void exceptionTest() {
        assertThrows(NotFoundException.class, () -> bombService.changeBombName(1L, "Non existent bomb name"));
    }
}