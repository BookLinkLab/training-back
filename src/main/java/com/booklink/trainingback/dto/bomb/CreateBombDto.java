package com.booklink.trainingback.dto.bomb;

import com.booklink.trainingback.model.BombType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBombDto {
    @NotBlank(message = "Name must contain at least one non-whitespace character")
    private String name;
    @NotNull(message = "Type is mandatory")
    private BombType type;
    @NotNull
    @Min(value = 0, message = "Radius must be positive")
    private Double radius;
}
