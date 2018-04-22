package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Profesor.
 */
@RestController
@RequestMapping("/api")
public class AsignaturaProfesorResource {

    private final Logger log = LoggerFactory.getLogger(AsignaturaProfesorResource.class);

    private static final String ENTITY_NAME = "asignaturaProfesor";

    private final AsignaturaProfesorRepository asignaturaProfesorRepository;
    private final ProfesorRepository profesorRepository;

    public AsignaturaProfesorResource(AsignaturaProfesorRepository asignaturaprofesorRepository, ProfesorRepository profesorRepository) {
        this.asignaturaProfesorRepository = asignaturaprofesorRepository;
        this.profesorRepository = profesorRepository;
    }

    /**
     * GET  /subjectsprofesors/subjects/:id : get the "asignaturas" profesor.
     *
     * @param id  of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subjects, or with status 404 (Not Found)
     */
    //@GetMapping("/asignaturaprofesors/subjects/{id}")
    @RequestMapping(value = "/asignaturaprofesors/subjects/{id}",
        method= RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Asignatura>> getProfesorSubjects(@PathVariable Long id) {
        log.debug("REST request to get Profesor Subjects: {}", id);

        //List<Asignatura> subjects = asignaturaProfesorRepository.getProfesorSubjects(id);

        Profesor asigProfesor = profesorRepository.findOne(id);
        log.debug("Recuperado profesor: {} ",asigProfesor);
        log.debug("REST get Profesor Subjects: {}", asigProfesor.getAsignaturas());
        List<Asignatura> asignaturas = new ArrayList<>();
        asignaturas = asigProfesor.getAsignaturas();
        // JSONObject json = new JSONObject();



        return Optional.ofNullable(asignaturas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asigProfesor.getAsignaturas()));
    }
}
