package com.booklink.trainingback.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookDto {
    private String title;
    private Long isbn;
    private LocalDate publishDate;
    private Long authorId;
}
