package org.example.neonarkintaketracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatureRequest(
        @NotBlank String name,
        @NotBlank String species,
        @NotBlank String dangerLevel,
        @NotBlank String condition,
        @Size(max = 500) String notes,
        @NotNull Long habitatId
) {}