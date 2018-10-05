package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
    @Autowired
    EntityManager entityManager;

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
        log.debug("REST request to save AsignaturaProfesor asignatura : {}", json.get("id_asignatura"));
        log.debug("REST request to save AsignaturaProfesor  profesor : {}", json.get("id_profesor"));
        String idProfesor = json.get("id_profesor");
        String idAsignatura = json.get("id_asignatura");
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asig = Long.parseLong(idAsignatura, 10);;

        Profesor profesor = profesorRepository.findOne(id_prof);
        Asignatura asignatura =asignaturaRepository.findOne(id_asig);
        log.debug("REST request to save AsignaturaProfesor asignatura  : {} prof:{}", asignatura , profesor);
        profesor.getAsignaturas().add(asignatura);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>ASIGNATURAS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< : {}", profesor.getAsignaturas());
        log.debug("------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>---------------------------");
        Profesor result = profesorRepository.save(profesor);
        log.debug("***********************************************************");
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>ASIGs---------------------------------------------------- : {}", profesor.getAsignaturas());
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

    ////////////////////////////
    /**
     * @param asignatura  to find
     * @return the ResponseEntity with status 200 (OK) and with body the profesors, or with status 404 (Not Found)
     */
    @PostMapping ("/asignaturaprofesors/asignaturainprof")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Profesor>> getSubjectInProfesores(@RequestBody Asignatura asignatura) {
        log.debug("REST request to get asignatura in Profesors: {}", asignatura);
        List <Profesor> profesores = new ArrayList<>(); // todos los profesores
        List <Asignatura> asignaturas = new ArrayList<>(); //asignaturas que tiene asignadas un profesor
        List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
        /*try{
            String query = "select p.id from Profesor p INNER JOIN asignatura_profesor nmt  where nmt.id_asignatura = " + asignatura.getId() +" and nmt.id_profesor = p.id";
            log.debug("QUERY: "+ query);
            lista = entityManager.createQuery(query);
        }catch (Exception e){
            log.debug("NO SE PUDO CONSTRUIR LA QUERY ", e.getCause());
            return (ResponseEntity<List<Profesor>>) ResponseEntity.badRequest();
        }*/
        profesores = profesorRepository.findAll();
        log.debug("profesores: ",profesores);
        for (Profesor profesor: profesores) {
            asignaturas = profesor.getAsignaturas();
            log.debug("profesor antes del if: ",profesor);
            for (Asignatura asignaturaList: asignaturas) {
                if (asignaturaList.getId() == asignatura.getId()){
                    log.debug("profesor : ",profesor);
                    profesoresList.add(profesor);
                }
            }
        }
        //ordeno los profesores por prioridad antes de devolverlos
        Collections.sort(profesoresList);
        log.debug("profesoresList : ",profesoresList.toArray());

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesoresList));
    }
}
