package com.ead.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class LessonDto {

    @NotBlank(message = "Title is requered")
    private String title;

    @NotBlank(message = "description is requered")
    private String description;

    private String videoUrl;

    @NotNull
    private UUID moduloId;
}
