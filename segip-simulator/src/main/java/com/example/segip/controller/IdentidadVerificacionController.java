
package com.example.segip.controller;

import com.example.segip.dto.IdentidadRequest;
import com.example.segip.dto.VerificacionResponse;
import com.example.segip.service.IdentidadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identidades")
public class IdentidadVerificacionController {

    private final IdentidadService service;

    public IdentidadVerificacionController(IdentidadService service) {
        this.service = service;
    }

    @PostMapping("/verificar")
    public VerificacionResponse verificar(
            @Valid @RequestBody IdentidadRequest request
    ) {

        boolean coincide = service.verificar(request);

        String mensaje = coincide
                ? "Datos verificados correctamente"
                : "No se pudo verificar la identidad";

        return new VerificacionResponse(coincide, mensaje);
    }
}
