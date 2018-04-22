package com.pfg.easyschedule.repository;
import com.pfg.easyschedule.domain.Asignatura;
import org.springframework.data.jpa.repository.*;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import java.util.List;
/**
 * Spring Data JPA repository for the AsignaturaProfesor entity.
 */
@SuppressWarnings("unused")
public interface AsignaturaProfesorRepository extends JpaRepository <AsignaturaProfesor, AsignaturaProfesorId> {

    //@Query("SELECT ap.profAsigpk.asignatura FROM AsignaturaProfesor ap WHERE ap.profAsigpk.profesor.id =  ?1")
    //public List<Asignatura> getProfesorSubjects(Long idProfesor);
}
