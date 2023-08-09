package com.booklink.trainingback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookDto {
    private String title;
    private Long isbn;
    private String publishDate;
    private Long author_id;
}
