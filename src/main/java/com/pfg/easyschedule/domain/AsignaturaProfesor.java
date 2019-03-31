package com.pfg.easyschedule.domain;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by kara_ on 01/11/2017.
 */
@Entity
@Table(name="asignatura_profesor")

public class AsignaturaProfesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public AsignaturaProfesorId profasigpk;

    @NotNull
    @Column (name = "num_creditos")
    private  Long num_creditos;

    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("id_profesor")
    @JoinColumn(referencedColumnName = "id",name = "id_profesor", insertable=false, updatable=false)
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    //@MapsId("id_asignatura")
    @JoinColumn(referencedColumnName = "id",name = "id_asignatura", insertable=false, updatable=false)
    @JsonIgnore
    private Asignatura asignatura;

    public AsignaturaProfesor() {
    }

    public AsignaturaProfesor(AsignaturaProfesorId profAsigpk, Long num_creditos) {
        this.profasigpk = profAsigpk;
        this.num_creditos = num_creditos;
    }

    public AsignaturaProfesor(Profesor profesor, Asignatura asignatura, long num_creditos){
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.num_creditos = num_creditos;
    }

    public AsignaturaProfesorId getProfAsigpk() {
        return profasigpk;
    }

    public void setProfAsigpk(AsignaturaProfesorId profAsigpk) {
        this.profasigpk = profAsigpk;
    }
    public AsignaturaProfesor profasigpk (AsignaturaProfesorId profasigpk) {
        this.profasigpk = profasigpk;
        return this;
    }

    public Long getNum_creditos() {
        return num_creditos;
    }

    public void setNum_creditos(Long num_creditos) {
        this.num_creditos = num_creditos;
    }
    public AsignaturaProfesor num_creditos (Long num_creditos) {
        this.num_creditos = num_creditos;
        return this;
    }
    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AsignaturaProfesor that = (AsignaturaProfesor) o;
        return Objects.equals(profesor, that.asignatura) &&
            Objects.equals(asignatura, that.profesor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesor, asignatura);
    }

    @Override
    public String toString() {
        return "AsignaturaProfesor{" +
            "profAsigpk=" + profasigpk +
            '}';
    }

//
//    @NotNull
//    @Column(name = "estado", nullable = false)
//    private char estado;


}
