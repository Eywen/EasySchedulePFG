package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;

import com.pfg.easyschedule.repository.AsignaturaRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Asignatura.
 */
@RestController
@RequestMapping("/api")
public class AsignaturaResource {

    private final Logger log = LoggerFactory.getLogger(AsignaturaResource.class);

    private static final String ENTITY_NAME = "asignatura";

    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaResource(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     * POST  /asignaturas : Create a new asignatura.
     *
     * @param asignatura the asignatura to create
     * @return the ResponseEntity with status 201 (Created) and with body the new asignatura, or with status 400 (Bad Request) if the asignatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asignaturas")
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
    }

    /**
     * PUT  /asignaturas : Updates an existing asignatura.
     *
     * @param asignatura the asignatura to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asignatura,
     * or with status 400 (Bad Request) if the asignatura is not valid,
     * or with status 500 (Internal Server Error) if the asignatura couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asignaturas")
    @Timed
    public ResponseEntity<Asignatura> updateAsignatura(@Valid @RequestBody Asignatura asignatura) throws URISyntaxException {
        log.debug("REST request to update Asignatura : {}", asignatura);
        if (asignatura.getId() == null) {
            return createAsignatura(asignatura);
        }
        Asignatura result = asignaturaRepository.save(asignatura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asignatura.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asignaturas : get all the asignaturas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of asignaturas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/asignaturas")
    @Timed
    public ResponseEntity<List<Asignatura>> getAllAsignaturas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Asignaturas");
        Page<Asignatura> page = asignaturaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/asignaturas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /asignaturas/:id : get the "id" asignatura.
     *
     * @param id the id of the asignatura to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the asignatura, or with status 404 (Not Found)
     */
    @GetMapping("/asignaturas/{id}")
    @Timed
    public ResponseEntity<Asignatura> getAsignatura(@PathVariable Long id) {
        log.debug("REST request to get Asignatura : {}", id);
       // Asignatura asignatura = asignaturaRepository.findOneWithEagerRelationships(id);
        Asignatura asignatura = asignaturaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignatura));
    }

    /**
     * DELETE  /asignaturas/:id : delete the "id" asignatura.
     *
     * @param id the id of the asignatura to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asignaturas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Long id) {
        log.debug("REST request to delete Asignatura : {}", id);
        asignaturaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
