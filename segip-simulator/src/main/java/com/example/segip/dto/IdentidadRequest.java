
package com.example.segip.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record IdentidadRequest(

        @NotBlank(message = "El CI es obligatorio")
        @Pattern(
                regexp = "[0-9]{4,12}",
                message = "El CI debe contener entre 4 y 12 dígitos"
        )
        String ci,

        @Pattern(
                regexp = "[a-zA-Z0-9]{0,5}",
                message = "Complemento inválido"
        )
        String complemento,

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 100)
        String nombres,

        @NotBlank(message = "El primer apellido es obligatorio")
        @Size(max = 70)
        String primerApellido,

        @Size(max = 70)
        String segundoApellido,

        @NotNull(message = "La fecha es obligatoria")
        @Past(message = "La fecha debe ser anterior a hoy")
        LocalDate fechaNacimiento

) {}
