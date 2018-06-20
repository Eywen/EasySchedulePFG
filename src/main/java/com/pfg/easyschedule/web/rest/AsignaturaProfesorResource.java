package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing AsignaturaProfesor.
 */
@RestController
@RequestMapping("/api")
public class AsignaturaProfesorResource {

    private final Logger log = LoggerFactory.getLogger(AsignaturaProfesorResource.class);

    private static final String ENTITY_NAME = "asignaturaProfesor";
    private final ProfesorRepository profesorRepository;
    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaProfesorResource(AsignaturaRepository asignaturaRepository, ProfesorRepository profesorRepository ) {
        this.profesorRepository = profesorRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
    *DELETE  /asignaturaprofesors/delete the  asignaturaProfesors.
     * @return the ResponseEntity with status 200 (OK)
     *
    */
    @DeleteMapping("/asignaturaprofesors/deleteselection/{id_profesor}/{id_asignatura}")
    //@DeleteMapping("/asignaturaprofesors/deleteselection")
    @Timed
    public ResponseEntity<Void> deleteAsignaturaProfesors(@PathVariable Long id_profesor, @PathVariable Long id_asignatura) {
        log.debug("REST request to delete AsignaturaProfesors id_profesor : {}", id_profesor );
        log.debug("REST request to delete AsignaturaProfesors id_asignatura : {}", id_asignatura );
        Profesor profesor = profesorRepository.findOne(id_profesor);
        Asignatura asignatura =asignaturaRepository.findOne(id_asignatura);
        log.debug("REST request to delete AsignaturaProfesor asignatura  : {} prof:{}", profesor , asignatura);
        profesor.getAsignaturas().remove(asignatura);
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, asignatura.getId().toString())).build();
    }


}
