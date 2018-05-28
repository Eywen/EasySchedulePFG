package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import com.pfg.easyschedule.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
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
import java.util.Set;

/**
 * REST controller for managing AsignaturaProfesor.
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
     * POST  /subjectsprofesors : to assign one subject to teacher.
     *
     * @param ,  the id_prof,id_asig to assign a subject to teacher
     * @return the ResponseEntity with status 201 (Created) and with body the new asignatura, or with status 400 (Bad Request) if the asignatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@RequestMapping(value = "/asignaturaprofesors/addsubject",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)*/
    @PostMapping("/asignaturaprofesors")
    //@Timed
    public ResponseEntity<AsignaturaProfesor> createAsignaturaProfesor(@Valid @RequestBody AsignaturaProfesorId asignaturaProfesorId) throws URISyntaxException {
        log.debug("REST request to save AsignaturaProfesor : {}", asignaturaProfesorId);

       /* if (AsignaturaProfesorId.getId_asignatura() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new asignatura cannot already have an ID")).body(null);
        }*/

       if (asignaturaProfesorRepository.exists(asignaturaProfesorId)){
           return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idasignacionexists", "this asignatura  already exists to this teacher")).body(null);
       }

        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor(asignaturaProfesorId);
        log.debug("new AsignaturaProfesor with asignaturaProfesorId from REST : {}", asignaturaProfesor);

        AsignaturaProfesor result = asignaturaProfesorRepository.save(asignaturaProfesor);
        log.debug("Get  getProfAsigpk from entity created : {}", result.getProfAsigpk());
       /*return ResponseEntity.created(new URI("/api/asignaturaprofesors/" + asignaturaProfesorId.id_profesor+"/"+asignaturaProfesorId.id_asignatura))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getProfAsigpk().toString()))
            .body(result);*/
        return Optional.ofNullable(result)
            .map(myresult -> new ResponseEntity<>(
                myresult,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        //return null;
    }

    /////////////////////////////////////////////////////////////
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

        AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId();
        asignaturaProfesorId.setId_asignatura(id_asignatura);
        asignaturaProfesorId.setId_profesor(id_profesor);
        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor(asignaturaProfesorId);

        asignaturaProfesorRepository.delete(asignaturaProfesor);
       // log.debug("REST request to delete AsignaturaProfesors idasig : {}", id_asignatura );
        // asignaturaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, asignaturaProfesor.getProfAsigpk().toString())).build();
        //return null;
    }

    /////////////////////////////////////////////////////////////no ok


 /*   *//**
     * GET  /asignaturasProfesor : get all the asignaturas-profesors.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and the list of asignaturasprofesors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     *//*
    @GetMapping("/asignaturaprofesors")
    @Timed
    //public ResponseEntity<List<AsignaturaProfesor>> getAllAsignaturasProfesors(@ApiParam Pageable pageable) {
    public ResponseEntity<List<AsignaturaProfesor>> getAllAsignaturasProfesors() {
        log.debug("REST request to get a page of AsignaturasProfesors");
        //Page<AsignaturaProfesor> page = asignaturaProfesorRepository.findAll(pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/asignaturaprofesors");
        List<AsignaturaProfesor> asignaturasProfesor =asignaturaProfesorRepository.findAll();
        log.debug("asignaturaProfesorRepository.findAll"+asignaturaProfesorRepository.findAll());
        // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        *//*Page<AsignaturaProfesor> page = asignaturaProfesorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/asignaturaprofesors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*//*
        return Optional.ofNullable(asignaturasProfesor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }*/

    /**
     * POST  /asignaturas : Create a new asignatura.
     *
     * @param asignatura the asignatura to create
     * @return the ResponseEntity with status 201 (Created) and with body the new asignatura, or with status 400 (Bad Request) if the asignatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PostMapping("/asignaturas")
    @Timed
    public ResponseEntity<Asignatura> createAsignatura(@Valid @RequestBody Asignatura asignatura) throws URISyntaxException {
        log.debug("REST request to save Asignatura : {}", asignatura);
        if (asignatura.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new asignatura cannot already have an ID")).body(null);
        }
        Asignatura result = asignaturaRepository.save(asignatura);
        return ResponseEntity.created(new URI("/api/asignaturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }*/














/*

    */
/**
     * PUT  /asignaturaprofesors : Updates an existing asignatura.
     *
     * @param asignaturaProfesorId the asignatura to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asignatura-profesor,
     * or with status 400 (Bad Request) if the asignatura-profesor is not valid,
     * or with status 500 (Internal Server Error) if the asignatura-profesor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*

    @PutMapping("/asignaturaprofesors")
    @Timed
    public ResponseEntity<Asignatura> updateAsignaturaProfesor(@Valid @RequestBody AsignaturaProfesorId asignaturaProfesorId) throws URISyntaxException {
        log.debug("REST request to update AsignaturaProfesor : {}", asignaturaProfesorId);
       */
/* if (asignatura.getId() == null) {
            return createAsignatura(asignatura);
        }
        Asignatura result = asignaturaRepository.save(asignatura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asignatura.getId().toString()))
            .body(result);*//*

       return null;
    }
*/


/*

    */
/**
     * GET  /asignaturaprofesors/:id : get the "id asig" asignaturaProfesors.
     *
     * @param id the id of the asignatura to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the asignaturaProfesors, or with status 404 (Not Found)
     *//*

    @GetMapping("/asignaturaprofesors/{id}")
    @Timed
    public ResponseEntity<AsignaturaProfesor> getAsignaturaProfesors(@PathVariable Long id) {
        log.debug("REST request to get Asignatura : {}", id);
        //Asignatura asignatura = asignaturaProfesorRepository.findOneWithEagerRelationships(id);
        return null;// ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignatura));
    }
*/

  /*  *//**
     * DELETE  /asignaturaprofesors/ delete the  asignaturaProfesors.
     *
     *
     * @return the ResponseEntity with status 200 (OK)
     *//*
    @DeleteMapping("/asignaturaprofesors")
    @Timed
    public ResponseEntity<Void> deleteAsignaturaProfesors(@Valid @RequestBody AsignaturaProfesorId asignaturaProfesorId) {
        log.debug("REST request to delete AsignaturaProfesors : {}", asignaturaProfesorId);
        // asignaturaRepository.delete(id);
        //  return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        return null;
    }
*/

}
