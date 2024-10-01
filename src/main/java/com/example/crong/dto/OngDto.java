package com.example.crong.dto;

import jakarta.validation.constraints.NotBlank;

public record OngDto(@NotBlank String name, @NotBlank String description, @NotBlank String phone, @NotBlank String email) {
}
