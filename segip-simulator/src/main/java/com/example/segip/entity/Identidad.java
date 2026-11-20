
package com.example.segip.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "identidades",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_identidades_ci_complemento",
                        columnNames = {"ci", "complemento"}
                )
        }
)
public class Identidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String ci;

    @Column(nullable = false, length = 5)
    private String complemento = "";

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(
            name = "primer_apellido",
            nullable = false,
            length = 70
    )
    private String primerApellido;

    @Column(
            name = "segundo_apellido",
            length = 70
    )
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoIdentidad estado = EstadoIdentidad.ACTIVO;

    public Identidad() {
    }

    public Long getId() {
        return id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoIdentidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoIdentidad estado) {
        this.estado = estado;
    }
}
