package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.bomb.CreateBombDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bomb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BombType type;

    private Double radius;

    public static Bomb from(CreateBombDto dto) {
        return Bomb.builder()
                .name(dto.getName())
                .type(dto.getType())
                .radius(dto.getRadius())
                .build();
    }

}
