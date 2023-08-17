package com.booklink.trainingback.dto.bomb;

import com.booklink.trainingback.model.Bomb;
import com.booklink.trainingback.model.BombType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BombDto {
    private Long id;
    private String name;
    private BombType type;
    private Double radius;

    public static BombDto from(Bomb bomb) {
        return BombDto.builder()
                .id(bomb.getId())
                .name(bomb.getName())
                .type(bomb.getType())
                .radius(bomb.getRadius())
                .build();
    }
}
