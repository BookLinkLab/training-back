package com.booklink.trainingback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookDto {
    private Long isbn;
    private String title;
    private String publishDate;
    private List<Long> authorsId;
}
