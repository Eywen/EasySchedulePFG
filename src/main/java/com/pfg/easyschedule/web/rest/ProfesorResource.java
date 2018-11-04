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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
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
    @Autowired
    EntityManager em;

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
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public ResponseEntity<List<Profesor>> getAllProfesors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Profesors");
        List<Profesor> listaprofesores = profesorRepository.findAll();
        log.debug("************FINDALL_PROFESORS******** {}", listaprofesores);
        //List <AsignaturaProfesor> asignaturasProfesor = new ArrayList<>();
        Page<Profesor> page = profesorRepository.findAll(pageable);

        log.debug("--------------------------------------------------------------------------{}",em);
        /*String query = "select \n" +
            "asignatura0_.profasigpk.id_profesor as id_profesor, \n" +
            "asignatura0_.profasigpk.id_asignatura as id_asignatura, \n" +
            "asignatura0_.fecha_seleccion as fecha_seleccion, \n" +
            "asignatura0_.num_creditos as num_creditos_seleccion \n" +
            "from \n" +
            "AsignaturaProfesor asignatura0_ \n" +
            "where asignatura0_.id_profesor=";*/
        for (Profesor profesor: listaprofesores) {

            /*Query consulta = em.createQuery(query+profesor.getId());*/
           /* List<Object> profesors = profesorRepository.findasignaturas(profesor.getId()) ;*/
            log.debug("++++++++++++++++++++LISTA ASIGNATURA PROFESOR {} ++++++++++++++++++",profesor.getAsignaturaProfesors());
        }
        /*List<Profesor> profesorList = page.getContent();
        for  (Profesor prof: profesorList
             ) {
            //TODO probar a llamar a este metodo con una instancia que tengas mas asignaturas de las qe devuelve el page, ya que en proffesor onetomany no tiene el @BatchSize(size = n)
            asignaturasProfesor = prof.getAsignaturaProfesors();
            log.debug("(((((((((((((((((((((((((asignaturasProfesor))))))))))))))))))))) {}", asignaturasProfesor);
            prof.setAsignaturaProfesors(asignaturasProfesor);
            log.debug("////////////////////////////////prof/////////////////////// {}", prof);
        }

        log.debug("+++++++++++++++++PROFESORS_LIST++++++++++ {}", profesorList);
*/
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
    @GetMapping("/profesors/subjects/{id}")
    /*@RequestMapping(value = "/profesors/subjects/{id}",
        method= RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)*/
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Asignatura>> getProfesorSubjects(@PathVariable Long id) {
        log.debug("REST request to get Profesor Subjects: {}", id);

        //List<Asignatura> subjects = asignaturaProfesorRepository.getProfesorSubjects(id);

        Profesor profesor = profesorRepository.findOne(id);
        log.debug("Recuperado profesor: {} ",profesor);
        log.debug("REST get Profesor Subjects: {}", profesor.getAsignaturaProfesors());
        List<AsignaturaProfesor> asignaturasProfesors = new ArrayList<>();
        asignaturasProfesors = profesor.getAsignaturaProfesors();
        List <Asignatura> asignaturas = new ArrayList<>();

        for (AsignaturaProfesor as: asignaturasProfesors) {
            asignaturas.add(as.getAsignatura());
            log.debug("Recuperado asignaturas de asignatura profesor: {} ",asignaturas);
        }



        return Optional.ofNullable(asignaturas)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asigProfesor.getAsignaturaProfesors()));
    }
    /**
     * POST  /profesors/getsubjects : get the "asignaturas" of a taacher.
     *
     * @param profesor
     * @return the ResponseEntity with status 200 (OK) and with body the subjects of a teacher, or with status 404 (Not Found)
     */
    //ESTE METODO HA SIDO REEMPLAZADA POR profesors/getsubjects/id DE TIPO GET.
    /*@PostMapping (value = "/profesors/getsubjects")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Asignatura>> getProfesorSubjects(@RequestBody Profesor profesor) {
        log.debug("REST POST getProfesorSubjects: {}",profesor);
        Profesor profeAux = profesorRepository.findOne(profesor.getId());
        List <Asignatura> subjectsList = new ArrayList<>();
        List <AsignaturaProfesor> asignaturaProfesorsList = profeAux.getAsignaturaProfesors();
        log.debug("**************asignaturaProfesorsList**********{}",asignaturaProfesorsList);
        /*for (AsignaturaProfesor asignaturaProfesor: asignaturaProfesorsList) {
           subjectsList.add(asignaturaProfesor.getAsignatura());
        }
        log.debug("++++++++++++++++++++++++++++subjectsList++++++++++++++++++++++",asignaturaProfesorsList);*/
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subjectsList));*/

    //}








}
