package com.pfg.easyschedule.repository;

import com.pfg.easyschedule.domain.Profesor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Profesor entity.
 */
@SuppressWarnings("unused")
public interface ProfesorRepository extends JpaRepository<Profesor,Long> {

}
