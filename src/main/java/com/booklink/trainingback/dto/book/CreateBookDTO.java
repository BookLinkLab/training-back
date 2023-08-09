package com.booklink.trainingback.dto.book;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookDTO {
    private String title;
    private String ISBN;
    private Date publicationDate;
    private Long authorId;
}
