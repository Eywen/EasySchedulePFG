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
   /* @PostMapping("/asignaturaprofesors")
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
        profesor.getAsignaturaProfesors().add(asignatura);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>ASIGNATURAS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< : {}", profesor.getAsignaturaProfesors());
        log.debug("------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>---------------------------");
        Profesor result = profesorRepository.save(profesor);
        log.debug("***********************************************************");
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>ASIGs---------------------------------------------------- : {}", profesor.getAsignaturaProfesors());
        return Optional.ofNullable(result)
            .map(myresult -> new ResponseEntity<>(
                myresult,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
/////////////////////////////
    /**
    *DELETE  /asignaturaprofesors/delete the  asignaturaProfesors.
     * @return the ResponseEntity with status 200 (OK)
     *
    */
    /*@DeleteMapping("/asignaturaprofesors/deleteselection/{id_profesor}/{id_asignatura}")
    //@DeleteMapping("/asignaturaprofesors/deleteselection")
    @Timed
    public ResponseEntity<Void> deleteAsignaturaProfesors(@PathVariable Long id_profesor, @PathVariable Long id_asignatura) {
        log.debug("REST request to delete AsignaturaProfesors id_profesor : {}", id_profesor );
        log.debug("REST request to delete AsignaturaProfesors id_asignatura : {}", id_asignatura );
        Profesor profesor = profesorRepository.findOne(id_profesor);
        Asignatura asignatura =asignaturaRepository.findOne(id_asignatura);
        log.debug("REST request to delete AsignaturaProfesor asignatura  : {} prof:{}", profesor , asignatura);
        profesor.getAsignaturaProfesors().remove(asignatura);
        Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, asignatura.getId().toString())).build();
    }*/

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
        //profesores = profesorRepository.findAll();
        /*for (Profesor profesor: profesores) {
            asignaturas = profesor.getAsignaturaProfesors();
            for (Asignatura asignaturaList: asignaturas) {
                if (asignaturaList.getId() == asignatura.getId()){
                    profesoresList.add(profesor);
                }
            }
        }*/
        //ordeno los profesores por prioridad antes de devolverlos
        Collections.sort(profesoresList);
        //getLowerPriority(profesoresList)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesoresList));
    }
/*
    /**
     * @param datos  list of teachers to find their prioritys and priority actual  teacher
     * @return the ResponseEntity with status 200 (OK) and with body the profesors lower prioritys, or with status 404 (Not Found)
     */
   /* @PostMapping ("/asignaturaprofesors/getlowerpriority")
    @Timed
    public ResponseEntity<List<Profesor>> getLowerPriority(@RequestBody HashMap<String,Object> datos) {
        log.debug("REST request to get lower priority from array: {}", datos);
        ArrayList<Profesor> listaProfesores= null;
        List<Profesor> list = new ArrayList();
        Number profesorId = null;
        Profesor profesor = new Profesor();
        Iterator<Map.Entry<String,Object>> it = datos.entrySet().iterator();
        //Collection<Object> collection= datos.values();
        //log.debug("COLLECTIONS  ", collection );
       /* int cont = 0;
        while (it.hasNext()) {
            Map.Entry<String,Object> e = it.next();
            log.debug("HASHMAP KEY: {} VALUE: {} CONT {}", e.getKey(),e.getValue(), cont );
            //System.out.println(e.getKey() + " " + e.getValue());
            if (cont == 0){
                listaProfesores = (ArrayList<Profesor>) e.getValue();
                log.debug("LISTAPROFESORES {}", listaProfesores );
            }
            else if (cont ==1){
                profesorId = (Number) e.getValue();//(e.getClass().cast(Profesor));//
                log.debug("PROFESORID {} ", profesorId );

            }
            cont++;
            //list.add((Profesor) e.getValue());
        }

       /* long id = profesorId.longValue();
        Long pId = id;
        log.debug("LongID {} ", profesorId.longValue() );
        profesor = profesorRepository.findOne(pId);
        log.debug("(PROFESOR) PROFESOR: {}", profesor);

        List<Profesor> profores = new ArrayList<>();

        for (int i = 0; i < listaProfesores.size(); i++){
            Profesor idProfesor = listaProfesores.get(i);
            log.debug("(PROFESOR) for: {}", idProfesor);
        }*/


       /* int index = -1;
        log.debug("(PROFESORES) getClass: {}",  listaProfesores.getClass());
        for (Profesor p : listaProfesores){
            log.debug("(PROFESOR) index: {}",  p.getPrioridad());
        }*/
       /* for (int profInd = 0; profInd < listaProfesores.size(); profInd++) {
            if (listaProfesores.get(profInd).getPrioridad().equals(profesor.getPrioridad())) {
                index = profInd;
                log.debug("(PROFESOR) index: {}", index);
                break;
            }
        }*/
        /*return index;*/
       /* for (Profesor profesor : listaProfesores){

        }*/

       /* for (Map.Entry<Object, Profesor> profesor : profesores.entrySet()){
            Object clave = profesor.getKey();
            Profesor valor = profesor.getValue();
            System.out.println(clave+"  ------->  "+valor.toString());
        }*/
       /* return null;
    }*/
    /**
     * Servicio GET
     * @param asignaturaId id de la asignatura a buscar
     * @param profesorId  id del profesor que quiere hacer la selección
     * @return una lista de profesores que tienen la prioridad más baja que el profesor indicado
     */
  /* @GetMapping ("/asignaturaprofesors/getlowerpriority/{asignaturaId}/{profesorId}")
   @Timed
   public ResponseEntity<List<Profesor>> getLowerPriority(@PathVariable Long asignaturaId, @PathVariable Long profesorId) {
       log.debug("REST request to get lower priority from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);
       List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
       Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
       Profesor prof = profesorRepository.findOne(profesorId);
       //profesores = profesorRepository.findAll();
       List <Profesor> lowerPriorityProfesor = new ArrayList<>();
       profesoresList = getProfesoresList(asignatura);
      lowerPriorityProfesor = lowerPriorityTeachers(profesoresList,prof);
       return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lowerPriorityProfesor));
   }*/

    /**
     * Servicio GET
     * @param asignaturaId id de la asignatura a buscar
     * @param profesorId  id del profesor que quiere hacer la selección
     * @return una lista de profesores que tienen la prioridad más alta que el profesor indicado
     */
   /* @GetMapping ("/asignaturaprofesors/gethighestpriority/{asignaturaId}/{profesorId}")
    @Timed
    public ResponseEntity<List<Profesor>> getHighestPriority(@PathVariable Long asignaturaId, @PathVariable Long profesorId) {
        log.debug("REST request to get highest priority from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);
        List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
        Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
        Profesor prof = profesorRepository.findOne(profesorId);
        List <Profesor> highestPriorityProfesor = new ArrayList<>();

        profesoresList = getProfesoresList(asignatura);
        highestPriorityProfesor = highestPriorityTeachers(profesoresList,prof);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(highestPriorityProfesor));
    }*/

    /**
     *
     * @param asignatura asignatura que se quiere saber quienes la tienen asignada
     * @return Arraylist de los profesores que tienen asignada una asignatura
     */
  /* public List<Profesor> getProfesoresList (Asignatura asignatura){
       List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
       List <Profesor> profesores = new ArrayList<>(); // todos los profesores
       List <Asignatura> asignaturas = new ArrayList<>(); //asignaturas que tiene asignadas un profesor

       profesores = profesorRepository.findAll();
       for (Profesor profesor: profesores) {
           asignaturas = profesor.getAsignaturaProfesors();
           for (Asignatura asignaturaList: asignaturas) {
               if (asignaturaList.getId() == asignatura.getId()){
                   profesoresList.add(profesor);
               }
           }
       }
       //ordeno los profesores por prioridad antes de devolverlos
       Collections.sort(profesoresList);
       return profesoresList;
   }*/

    /**
     *
      * @param profesoresList lista de profesores que tienen asignada una asignatura
     * @param prof profesor que quiere seleccionar dicha asignatura para impartir
     * @return Array de profesores que tienen la prioridad más baja que la del profesor que quiere hacer la elección
     */
  /* public List<Profesor> lowerPriorityTeachers (List<Profesor> profesoresList, Profesor prof ){
       List <Profesor> lowerPriorityProfesor = new ArrayList<>();
       int cont = profesoresList.size()-1;
       //TODO revisar si el 1 es la mayor prioridad o la menor
       /** agrega a un array todos los profesores que tienen menor prioridad
        *que el profesore al que se quiere asignar la asignatura. prioridad grande es
        * menor, por ejemplo el q tenga prioridad 1 tiene mas prioridad que los demás
        **/
       /*while (profesoresList.get(cont).getPrioridad() > prof.getPrioridad() && cont >0) {
               lowerPriorityProfesor.add(profesoresList.get(cont));
           cont--;
       }
       log.debug("(LOWER PRIORITY): {} cont: {}",lowerPriorityProfesor, cont);
       return lowerPriorityProfesor;
   }*/

    /**
     *
     * @param profesoresList lista de los profesores que tienen una asignatura asignada
     * @param prof profesor que quiere seleccionar una asignatura para impartir
     * @return lista de los profesores con la prioridad más alta que el profesor que quiere hacerl a selección
     */
   /*public List<Profesor> highestPriorityTeachers (List<Profesor> profesoresList, Profesor prof){
       List <Profesor> highestPriorityProfesor = new ArrayList<>();
       int cont = 0;
       //TODO revisar si el 1 es la mayor prioridad o la menor
       /** agrego a un array todos los profesores que tienen menor prioridad
        *que el profesore al que se quiere asignar la asignatura. prioridad grande es
        * menor, por ejemplo el q tenga prioridad 1 tiene mas prioridad que los demás
        **/
       /*log.debug("PROFESORESlIST IN HIGHEST PRIORITY",profesoresList);
       while (profesoresList.get(cont).getPrioridad() < prof.getPrioridad() && cont  < profesoresList.size()) {
           highestPriorityProfesor.add(profesoresList.get(cont));
           cont++;
       }
       log.debug("(HIGHEST PRIORITY): {} cont: {}",highestPriorityProfesor, cont);
       return highestPriorityProfesor;
   }*/

    /**
     *
     * @param asignaturaId id de la asignatura que se quiere buscar
     * @param profesorId id del profesor del que se quiere saber si tiene la asignatura
     * @return true o false si el profesor ya tiene asignada la asignatura
     */
  /* @GetMapping ("/asignaturaprofesors/checkAsignaturainProfesor/{asignaturaId}/{profesorId}")
   @Timed
   public ResponseEntity <Boolean> checkAsignaturainProfesor (@PathVariable Long asignaturaId,@PathVariable Long profesorId){
       log.debug("REST request to get checkAsignaturainProfesor from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);
       Profesor profesor = profesorRepository.findOne(profesorId);
       List <Asignatura> asignaturas = profesor.getAsignaturaProfesors();
       Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
       log.debug("PROFESOR: {}",profesor);
       log.debug("ASIGNATURAS LIST: {}",asignaturas);
       log.debug("ASIGNATURA: {}", asignatura);
       Boolean ckeckAsig = asignaturas.contains(asignatura);

       return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ckeckAsig));
   }*/

    /**
     *
     * @param asignaturaId asignatura que se desea buscar
     * @param profesorId profesor del que se quiere saber cuantas veces tiene asignada dicha asignatura
     * @return un Integer que contiene el número de veces que tiene asignado un profesor una asignatura
     */
   /*@GetMapping ("/asignaturaprofesors/countsubject/{asignaturaId}/{profesorId}")
   @Timed
    public ResponseEntity <Integer> countSubject (@PathVariable Long asignaturaId, @PathVariable Long profesorId){
        log.debug("REST request count subject to a teacher from asignaturaId: {} profesorId: {}", asignaturaId, profesorId);
        Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
        List<Profesor> profesorList = getProfesoresList(asignatura);
        Integer cont = 0;
       for (Profesor profesor: profesorList) {
           if (profesor.getId() == profesorId){
                cont++;
           }
       }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cont));
   }*/
}
