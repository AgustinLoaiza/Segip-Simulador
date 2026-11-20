
package com.example.segip.service;

import com.example.segip.dto.IdentidadRequest;
import com.example.segip.entity.EstadoIdentidad;
import com.example.segip.entity.Identidad;
import com.example.segip.repository.IdentidadRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class IdentidadService {

    private final IdentidadRepository repository;

    public IdentidadService(IdentidadRepository repository) {
        this.repository = repository;
    }

    // REGISTRAR IDENTIDAD FICTICIA
    @Transactional
    public Identidad registrar(IdentidadRequest request) {

        String ci = request.ci().strip();
        String complemento = normalizar(request.complemento());

        if (repository.existsByCiAndComplemento(ci, complemento)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una identidad con ese CI y complemento"
            );
        }

        Identidad identidad = new Identidad();

        identidad.setCi(ci);
        identidad.setComplemento(complemento);
        identidad.setNombres(normalizar(request.nombres()));
        identidad.setPrimerApellido(
                normalizar(request.primerApellido())
        );
        identidad.setSegundoApellido(
                normalizarOpcional(request.segundoApellido())
        );
        identidad.setFechaNacimiento(request.fechaNacimiento());
        identidad.setEstado(EstadoIdentidad.ACTIVO);

        try {
            return repository.saveAndFlush(identidad);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se pudo registrar la identidad"
            );
        }
    }

    // VERIFICAR IDENTIDAD
    @Transactional(readOnly = true)
    public boolean verificar(IdentidadRequest request) {

        String ci = request.ci().strip();
        String complemento = normalizar(request.complemento());

        return repository.findByCiAndComplemento(ci, complemento)
                .filter(identidad ->
                        identidad.getEstado() == EstadoIdentidad.ACTIVO
                )
                .filter(identidad ->
                        coincide(identidad.getNombres(), request.nombres())
                )
                .filter(identidad ->
                        coincide(
                                identidad.getPrimerApellido(),
                                request.primerApellido()
                        )
                )
                .filter(identidad ->
                        coincide(
                                identidad.getSegundoApellido(),
                                request.segundoApellido()
                        )
                )
                .filter(identidad ->
                        identidad.getFechaNacimiento()
                                .equals(request.fechaNacimiento())
                )
                .isPresent();
    }

    // NORMALIZACION DE TEXTO
    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.strip()
                .replaceAll("\\s+", " ")
                .toUpperCase(Locale.ROOT);
    }

    private String normalizarOpcional(String valor) {
        String resultado = normalizar(valor);
        return resultado.isEmpty() ? null : resultado;
    }

    private boolean coincide(String registrado, String recibido) {
        return normalizar(registrado).equals(normalizar(recibido));
    }
}
