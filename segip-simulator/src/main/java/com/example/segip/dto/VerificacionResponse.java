
package com.example.segip.dto;

public record VerificacionResponse(
        boolean coincide,
        String mensaje
) {}
