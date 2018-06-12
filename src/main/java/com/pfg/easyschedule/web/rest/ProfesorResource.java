package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.Profesor;

import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import com.pfg.easyschedule.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Profesor.
 */
@RestController
@RequestMapping("/api")
public class ProfesorResource {

    private final Logger log = LoggerFactory.getLogger(ProfesorResource.class);

    private static final String ENTITY_NAME = "profesor";

    private final ProfesorRepository profesorRepository;

    public ProfesorResource(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * POST  /profesors : Create a new profesor.
     *
     * @param profesor the profesor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesor, or with status 400 (Bad Request) if the profesor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> createProfesor(@Valid @RequestBody Profesor profesor) throws URISyntaxException {
        log.debug("REST request to save Profesor : {}", profesor);
        if (profesor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profesor cannot already have an ID")).body(null);
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.created(new URI("/api/profesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /profesors : Updates an existing profesor.
     *
     * @param profesor the profesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesor,
     * or with status 400 (Bad Request) if the profesor is not valid,
     * or with status 500 (Internal Server Error) if the profesor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profesors")
    @Timed
    public ResponseEntity<Profesor> updateProfesor(@Valid @RequestBody Profesor profesor) throws URISyntaxException {
        log.debug("REST request to update Profesor : {}", profesor);
        if (profesor.getId() == null) {
            return createProfesor(profesor);
        }
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesor.getId().toString()))
            .body(result);
    }



    /**
     * GET  /profesors : get all the profesors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of profesors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/profesors")
    @Timed
    public ResponseEntity<List<Profesor>> getAllProfesors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Profesors");
        Page<Profesor> page = profesorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profesors");
        log.debug("Page all Profesors"+page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /profesors/:id : get the "id" profesor.
     *
     * @param id the id of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesor, or with status 404 (Not Found)
     */
    @GetMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Profesor> getProfesor(@PathVariable Long id) {
        log.debug("REST request to get Profesor : {}", id);
        Profesor profesor = profesorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesor));
    }

    /**
     * GET  /profesors/:login : get the "login" profesor.
     *
     * @param login the login of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesor, or with status 404 (Not Found)
     */
    @GetMapping("/profesors/login/{login}")
    @Timed
    public ResponseEntity<Profesor> getProfesorLogin(@PathVariable String login) {
        log.debug("REST request to get Profesor : {}", login);
        Profesor profesor = profesorRepository.getLogin(login);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesor));
    }

    /**
     * DELETE  /profesors/:id : delete the "id" profesor.
     *
     * @param id the id of the profesor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        log.debug("REST request to delete Profesor : {}", id);
        profesorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /profesors/subjects/:id : get the "asignaturas" profesor.
     *
     * @param id  of the profesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subjects, or with status 404 (Not Found)
     */
    //@GetMapping("/asignaturaprofesors/subjects/{id}")
    @RequestMapping(value = "/profesors/subjects/{id}",
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
