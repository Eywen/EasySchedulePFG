package com.pfg.easyschedule.domain;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by kara_ on 01/11/2017.
 */
@Embeddable
public class AsignaturaProfesorId implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "id_profesor")
    private long id_profesor;

    @Column(name = "id_asignatura")
    private long id_asignatura;


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
        return id_asignatura;
    }

    public void setId_asignatura(long id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignaturaProfesorId)) return false;

        AsignaturaProfesorId that = (AsignaturaProfesorId) o;

        if (getId_profesor() != that.getId_profesor()) return false;
        return getId_asignatura() == that.getId_asignatura();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId_profesor() ^ (getId_profesor() >>> 32));
        result = 31 * result + (int) (getId_asignatura() ^ (getId_asignatura() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AsignaturaProfesorId{" +
             ", id_profesor=" + id_profesor +
            ", id_asignatura=" + id_asignatura +
            '}';
    }
}
