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
    @Query("SELECT ap FROM AsignaturaProfesor ap WHERE ap.profasigpk.id_profesor =  ?1 and ap.profasigpk.id_asig = ?2")
    List<AsignaturaProfesor> findByprofyasig(Long id_profesor, Long id_asignatura);

    @Query("SELECT ap FROM AsignaturaProfesor ap WHERE  ap.profasigpk.id_asig = ?1")
    List<AsignaturaProfesor> findByAsignatura(Long id);

    @Query("SELECT ap FROM AsignaturaProfesor ap WHERE  ap.profasigpk.id_profesor =  ?1")
    List<AsignaturaProfesor> findByProfesor(Long id);
}
