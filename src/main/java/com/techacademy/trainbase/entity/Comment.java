package com.techacademy.trainbase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long taskId;
    private String author;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
