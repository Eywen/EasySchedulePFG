package com.pfg.easyschedule.web.rest.util;

import com.pfg.easyschedule.domain.AsignaturaProfesor;

import java.util.List;

public class ProfesorFrontDto {


    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private Integer codProfesor;
    private String email;
    private String categoria;
    private Integer numCreditosImpartir;
    private Integer prioridad;
    private String usuAlta;
    private String login;
    private List<AsignaturaProfesor> asignaturaProfesors;
    private Long creditosLibres;

    public ProfesorFrontDto() {
    }

    public ProfesorFrontDto(Long id, String nombre, String primerApellido, String segundoApellido,
                            Integer codProfesor, String email, String categoria,
                            Integer numCreditosImpartir, Integer prioridad, String usuAlta,
                            String login, List<AsignaturaProfesor> asignaturaProfesors) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.codProfesor = codProfesor;
        this.email = email;
        this.categoria = categoria;
        this.numCreditosImpartir = numCreditosImpartir;
        this.prioridad = prioridad;
        this.usuAlta = usuAlta;
        this.login = login;
        this.asignaturaProfesors = asignaturaProfesors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Integer getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(Integer codProfesor) {
        this.codProfesor = codProfesor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getNumCreditosImpartir() {
        return numCreditosImpartir;
    }

    public void setNumCreditosImpartir(Integer numCreditosImpartir) {
        this.numCreditosImpartir = numCreditosImpartir;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getUsuAlta() {
        return usuAlta;
    }

    public void setUsuAlta(String usuAlta) {
        this.usuAlta = usuAlta;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<AsignaturaProfesor> getAsignaturaProfesors() {
        return asignaturaProfesors;
    }

    public void setAsignaturaProfesors(List<AsignaturaProfesor> asignaturaProfesors) {
        this.asignaturaProfesors = asignaturaProfesors;
    }

    public Long getCreditosLibres() {
        return creditosLibres;
    }

    public void setCreditosLibres(Long creditosLibres) {
        this.creditosLibres = creditosLibres;
    }
}
