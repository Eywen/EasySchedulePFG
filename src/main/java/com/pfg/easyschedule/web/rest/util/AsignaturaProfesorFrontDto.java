package com.pfg.easyschedule.web.rest.util;

import com.pfg.easyschedule.domain.AsignaturaProfesor;

import java.io.Serializable;

public class AsignaturaProfesorFrontDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id_profesor;
    private long id_asig;
    private String fecha_seleccion;
    private  Long num_creditos;

    public AsignaturaProfesorFrontDto(long id_profesor, long id_asig, String fecha_seleccion, Long num_creditos) {
        this.id_profesor = id_profesor;
        this.id_asig = id_asig;
        this.fecha_seleccion = fecha_seleccion;
        this.num_creditos = num_creditos;
    }
    public AsignaturaProfesorFrontDto (AsignaturaProfesor asignaturaProfesor){
        this.id_profesor = asignaturaProfesor.getProfAsigpk().getId_profesor();
        this.id_asig = asignaturaProfesor.getProfAsigpk().getId_asignatura();
        this.fecha_seleccion = asignaturaProfesor.getProfAsigpk().getFechaSeleccion().toString();
        this.num_creditos = asignaturaProfesor.getNum_creditos();
    }

    public AsignaturaProfesorFrontDto(Long id_prof, Long id_asignatura, String fechaseleccion) {
        this.id_profesor = id_prof;
        this.id_asig = id_asignatura;
        this.fecha_seleccion = fechaseleccion;
    }

    public AsignaturaProfesorFrontDto() {

    }

    public long getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(long id_profesor) {
        this.id_profesor = id_profesor;
    }

    public long getId_asig() {
        return id_asig;
    }

    public void setId_asig(long id_asig) {
        this.id_asig = id_asig;
    }

    public String getFecha_seleccion() {
        return fecha_seleccion;
    }

    public void setFecha_seleccion(String fecha_seleccion) {
        this.fecha_seleccion = fecha_seleccion;
    }

    public Long getNum_creditos() {
        return num_creditos;
    }

    public void setNum_creditos(Long num_creditos) {
        this.num_creditos = num_creditos;
    }

    @Override
    public String toString() {
        return "AsignaturaProfesorFrontDto{" +
            "id_profesor=" + id_profesor +
            ", id_asig=" + id_asig +
            ", fecha_seleccion='" + fecha_seleccion + '\'' +
            ", num_creditos=" + num_creditos +
            '}';
    }
}
