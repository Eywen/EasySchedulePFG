package com.pfg.easyschedule.repository;

import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.Profesor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Profesor entity.
 */
@SuppressWarnings("unused")
public interface ProfesorRepository extends JpaRepository<Profesor,Long> {
    @Query("SELECT p FROM Profesor p WHERE p.login = ?1")
    Profesor getLogin (String login);

    /*String query = "select \n" +
        "asignatura0_.id_profesor as id_profesor, \n" +
        "asignatura0_.id_asignatura as id_asignatura, \n" +
        "asignatura0_.fecha_seleccion as fecha_seleccion, \n" +
        "asignatura0_.num_creditos as num_creditos_seleccion \n" +
        "from \n" +
        "asignatura_profesor asignatura0_ \n" +
        "where asignatura0_.id_profesor:=";
    @Query(
        value = query + profId,
        nativeQuery = true)
    List<Object> findasignaturas(Long profId);*/

}
