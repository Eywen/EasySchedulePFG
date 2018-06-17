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
     * POST  /subjectsprofesors : to assign one subject to teacher.
     *
     * @param ,  the id_prof,id_asig to assign a subject to teacher
     * @return the ResponseEntity with status 201 (Created) and with body the new asignatura, or with status 400 (Bad Request) if the asignatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asignaturaprofesors")
     public ResponseEntity<Profesor> createAsignaturaProfesor(@RequestBody Map<String, String> json) throws URISyntaxException {
        log.debug("REST request to save AsignaturaProfesor profesor : {}", json.get("id_asignatura"));
        log.debug("REST request to save AsignaturaProfesor asignatura  : {}", json.get("id_profesor"));
        String idProfesor = json.get("id_profesor");
        String idAsignatura = json.get("id_asignatura");
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asig = Long.parseLong(idAsignatura, 10);;

        Profesor profesor = profesorRepository.findOne(id_prof);
        Asignatura asignatura =asignaturaRepository.findOne(id_asig);
        log.debug("REST request to save AsignaturaProfesor asignatura  : {} prof:{}", profesor , asignatura);
        profesor.getAsignaturas().add(asignatura);
        Profesor result = profesorRepository.save(profesor);
        return Optional.ofNullable(result)
            .map(myresult -> new ResponseEntity<>(
                myresult,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
