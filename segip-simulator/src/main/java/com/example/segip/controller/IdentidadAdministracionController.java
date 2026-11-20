
package com.example.segip.controller;

import com.example.segip.dto.IdentidadRequest;
import com.example.segip.dto.RegistroResponse;
import com.example.segip.entity.Identidad;
import com.example.segip.service.IdentidadService;

import jakarta.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("dev")
@RequestMapping("/api/v1/identidades")
public class IdentidadAdministracionController {

    private final IdentidadService service;

    public IdentidadAdministracionController(
            IdentidadService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> registrar(
            @Valid @RequestBody IdentidadRequest request
    ) {

        Identidad identidad = service.registrar(request);

        RegistroResponse respuesta = new RegistroResponse(
                identidad.getId(),
                "Identidad registrada correctamente"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }
}
