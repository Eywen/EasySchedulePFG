package com.pfg.easyschedule.repository;

import com.pfg.easyschedule.domain.Asignatura;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Asignatura entity.
 */
@SuppressWarnings("unused")
public interface AsignaturaRepository extends JpaRepository<Asignatura,Long> {

    @Query("select distinct asignatura from Asignatura asignatura left join fetch asignatura.profesors")
    List<Asignatura> findAllWithEagerRelationships();

    @Query("select asignatura from Asignatura asignatura left join fetch asignatura.profesors where asignatura.id =:id")
    Asignatura findOneWithEagerRelationships(@Param("id") Long id);

}
