package com.pfg.easyschedule.domain;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by kara_ on 01/11/2017.
 */
@Embeddable
public class AsignaturaProfesorId {

    /*private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private Profesor profesor;

    public AsignaturaProfesorId() {
    }

    public AsignaturaProfesorId(Asignatura asignatura, Profesor profesor) {
        this.asignatura = asignatura;
        this.profesor = profesor;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignaturaProfesorId)) return false;

        AsignaturaProfesorId that = (AsignaturaProfesorId) o;

        if (!getAsignatura().equals(that.getAsignatura())) return false;
        return getProfesor().equals(that.getProfesor());
    }

    @Override
    public int hashCode() {
        int result = getAsignatura().hashCode();
        result = 31 * result + getProfesor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AsignaturaProfesorId{" +
            "asignatura=" + asignatura +
            ", profesor=" + profesor +
            '}';
    }*/
}
