package com.booklink.trainingback.dto.bomb;

import com.booklink.trainingback.model.BombType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBombDto {
    private String name;
    private BombType type;
    private Double radius;
}
