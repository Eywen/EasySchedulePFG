package com.pfg.easyschedule.domain;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import java.io.Serializable;

/**
 * Created by kara_ on 01/11/2017.
 */
@Entity
@Table(name="asignatura_profesor")

public class AsignaturaProfesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public AsignaturaProfesorId profAsigpk;

    public AsignaturaProfesor() {
    }
    public AsignaturaProfesor(AsignaturaProfesorId profAsigpk) {
        this.profAsigpk = profAsigpk;
    }



    public AsignaturaProfesorId getProfAsigpk() {
        return profAsigpk;
    }

    public void setProfAsigpk(AsignaturaProfesorId profAsigpk) {
        this.profAsigpk = profAsigpk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignaturaProfesor)) return false;

        AsignaturaProfesor that = (AsignaturaProfesor) o;

        return getProfAsigpk().equals(that.getProfAsigpk());
    }

    @Override
    public int hashCode() {
        return getProfAsigpk().hashCode();
    }

    @Override
    public String toString() {
        return "AsignaturaProfesor{" +
            "profAsigpk=" + profAsigpk +
            '}';
    }

//
//    @NotNull
//    @Column(name = "estado", nullable = false)
//    private char estado;


}
