package com.ead.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ModuloDto {

    @NotBlank(message = "Not permited values enpty or null")
    private String title;

    @NotBlank(message = "Not permited values enpty or null")
    private String description;

    @NotNull
    private UUID courseId;

}
