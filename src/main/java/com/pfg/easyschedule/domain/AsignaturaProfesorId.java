package com.pfg.easyschedule.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by kara_ on 01/11/2017.
 */
@Embeddable
public class AsignaturaProfesorId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "id_profesor")
    private long id_profesor;

    @NotNull
    @Column(name = "id_asignatura")
    private long id_asig;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_seleccion")
    private Date fecha_seleccion;


    public AsignaturaProfesorId() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public long getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(long id_profesor) {
        this.id_profesor = id_profesor;
    }

    public  long getId_asignatura() {
        return id_asig;
    }

    public void setId_asignatura(long id_asignatura) {
        this.id_asig = id_asignatura;
    }

    public Date getFechaSeleccion() {
        return fecha_seleccion;
    }

    public void setFechaSeleccion(Date fecha_seleccion) {
        this.fecha_seleccion = fecha_seleccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignaturaProfesorId)) return false;

        AsignaturaProfesorId that = (AsignaturaProfesorId) o;

        if (getId_profesor() != that.getId_profesor()) return false;
        if (getId_asignatura() != that.getId_asignatura()) return false;
        return getFechaSeleccion().equals(that.getFechaSeleccion());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId_profesor() ^ (getId_profesor() >>> 32));
        result = 31 * result + (int) (getId_asignatura() ^ (getId_asignatura() >>> 32));
        result = 31 * result + getFechaSeleccion().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AsignaturaProfesorId{" +
            "id_profesor=" + id_profesor +
            ", id_asig=" + id_asig +
            ", fechaSeleccion=" + fecha_seleccion +
            '}';
    }
}
