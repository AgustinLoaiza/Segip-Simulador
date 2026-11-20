
package com.example.segip.repository;

import com.example.segip.entity.Identidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentidadRepository
        extends JpaRepository<Identidad, Long> {

    Optional<Identidad> findByCiAndComplemento(
            String ci,
            String complemento
    );

    boolean existsByCiAndComplemento(
            String ci,
            String complemento
    );
}
