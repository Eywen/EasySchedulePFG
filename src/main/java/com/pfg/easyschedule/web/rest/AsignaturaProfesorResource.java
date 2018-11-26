package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.util.AsignaturaFrontDto;
import com.pfg.easyschedule.web.rest.util.AsignaturaProfesorFrontDto;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final AsignaturaProfesorRepository asignaturaProfesorRepository;
    @Autowired
    EntityManager entityManager;

    public AsignaturaProfesorResource(AsignaturaRepository asignaturaRepository, ProfesorRepository profesorRepository, AsignaturaProfesorRepository asignaturaProfesorRepository) {
        this.profesorRepository = profesorRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.asignaturaProfesorRepository = asignaturaProfesorRepository;
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

        /////////
        Timestamp timeStampDate = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date date = formatter.parse(new Timestamp(System.currentTimeMillis()).toString());
            timeStampDate = new Timestamp(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.debug("FECHA: {}",timeStampDate);
        AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(profesorId, asignaturaId, timeStampDate);
        AsignaturaProfesor asignaturaProfesor = asignaturaProfesorRepository.findOne(asignaturaProfesorId);
        log.debug("asignaturaProfesor  A CHECKEAR : {}",asignaturaProfesor);
        /////////

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
    }*/
///////////////////////////// desarrollo 16-11-18. con n:m.  OK
    /**
    *DELETE  /asignaturaprofesors/delete the  asignaturaProfesors.
     * @return the ResponseEntity with status 200 (OK)
     *
    */
    @PostMapping (value = "/asignaturaprofesors/deleteselection")
    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<AsignaturaFrontDto> deleteAsignaturaProfesors(@RequestBody Map<String, String> asignaturaborrar) {
        log.debug("REST POST getProfesorSubjects: {}", asignaturaborrar);
        Asignatura asignatura = new Asignatura();
        AsignaturaFrontDto asignaturaFrontDto = new AsignaturaFrontDto();
        String idProfesor = asignaturaborrar.get("id_profesor");
        String idAsignatura = asignaturaborrar.get("id_asignatura");
        String fechaseleccion = asignaturaborrar.get("fecha_seleccion");
        log.debug("REST request to delete AsignaturaProfesors fecha : {}", fechaseleccion );
        java.sql.Timestamp timeStampDate = null;
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asignatura = Long.parseLong(idAsignatura, 10);
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
            Date date = formatter.parse(fechaseleccion);
            timeStampDate = new Timestamp(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        AsignaturaProfesorId asignaturaProfesorIdBorrar = new AsignaturaProfesorId(id_prof,id_asignatura,timeStampDate);
        if (asignaturaProfesorRepository.exists(asignaturaProfesorIdBorrar)){
            log.debug("asignaturaProfesorIdBorrar (asignaturaProfesorId) EXISTIS {}",asignaturaProfesorRepository.findOne(asignaturaProfesorIdBorrar));
            asignaturaProfesorRepository.delete(asignaturaProfesorIdBorrar);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, asignaturaProfesorIdBorrar.toString())).build();
    }

    ///////////////////////actualizacion automatica sin verificacion 11-11-18. OK
    /**
     * PUT  /profesors : Updates an existing profesor.
     *
     * @param asignaturaActualizar the asignatura profesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asignatura_profesor,
     * or with status 400 (Bad Request) if the id profesor, id new subject, id old subject are not valid,
     * or with status 500 (Internal Server Error) if the asgnatura_profesor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asignaturaprofesors")
    @Timed
    public ResponseEntity<AsignaturaProfesor> updateAsignaturaProfesor(@RequestBody Map<String, String> asignaturaActualizar) throws URISyntaxException {
        log.debug("REST request to UPDATE asignaturaprofesor : {}", asignaturaActualizar);
        String idProfesor = asignaturaActualizar.get("id_profesor");
        String idAsignaturanueva = asignaturaActualizar.get("id_asignatura_nueva");
        String idAsignaturavieja = asignaturaActualizar.get("id_asignatura_antigua");
        String fechaseleccion = asignaturaActualizar.get("fecha_seleccion");
        String num_creditos = asignaturaActualizar.get("num_creditos");

        java.sql.Timestamp timeStampDate = null;
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asignatura_nueva = Long.parseLong(idAsignaturanueva, 10);
        Long  id_asignatura_antigua = Long.parseLong(idAsignaturavieja, 10);
        Long numCreditos= Long.parseLong(num_creditos, 10);
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
            Date date = formatter.parse(fechaseleccion);
            timeStampDate = new Timestamp(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        AsignaturaProfesor asigProf = new AsignaturaProfesor();
        AsignaturaProfesorId asignaturaProfesorIdAgregar = new AsignaturaProfesorId(id_prof,id_asignatura_nueva,new Date());
        AsignaturaProfesor asignaturaProfesornueva = new AsignaturaProfesor(asignaturaProfesorIdAgregar,numCreditos);

        if (id_prof != null && id_asignatura_nueva != null && id_asignatura_antigua != null
            && timeStampDate!= null) {
            AsignaturaProfesorId asignaturaProfesorIdBorrar = new AsignaturaProfesorId(id_prof,id_asignatura_antigua,timeStampDate);
            log.debug("AsignaturaProfesorId: {}", asignaturaProfesorIdBorrar);
            log.debug("asignaturaProfesorRepository.findOne(asignaturaProfesorId): {}", asignaturaProfesorRepository.findOne(asignaturaProfesorIdBorrar));
            if (asignaturaProfesorRepository.exists(asignaturaProfesorIdBorrar)){
                log.debug("asignaturaProfesorIdBorrar (asignaturaProfesorId) EXISTIS");
                asignaturaProfesorRepository.delete(asignaturaProfesorIdBorrar);
                asigProf = asignaturaProfesorRepository.save(asignaturaProfesornueva);
            }
        }

        //Profesor result = profesorRepository.save(profesor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asignaturaProfesornueva.getProfAsigpk().toString() .toString()))
            .body(asigProf);

    }
    /**
     * POST  /asignaturaprofesors/getsubject : get the "asignatura" of asignaturaProfesor in a taacher.
     *
     * @param json
     * @return the ResponseEntity with status 200 (OK) and with body the subjects of a teacher, or with status 404 (Not Found)
     */
    @PostMapping (value = "/asignaturaprofesors/getsubject")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<AsignaturaFrontDto> getProfesorAsignaturas(@RequestBody Map<String, String> json) {
        log.debug("REST POST getProfesorSubjects: {}", json);
        Asignatura asignatura = new Asignatura();
        AsignaturaFrontDto asignaturaFrontDto = new AsignaturaFrontDto();

        String idProfesor = json.get("id_profesor");
        String idAsignatura = json.get("id_asignatura");
        // String idAsignaturavieja = json.get("id_asignatura_antigua");
        String fechaseleccion = json.get("fecha_seleccion");
        //String num_creditos = json.get("num_creditos");

        Timestamp timeStampDate = null;
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asignatura = Long.parseLong(idAsignatura, 10);
        //Long numCreditos= Long.parseLong(num_creditos, 10);

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
            Date date = formatter.parse(fechaseleccion);
            timeStampDate = new Timestamp(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (id_prof != null && id_asignatura != null && timeStampDate!= null) {
            AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(id_prof, id_asignatura, timeStampDate);
            AsignaturaProfesor asignaturaProfesor = asignaturaProfesorRepository.findOne(asignaturaProfesorId);
            if (asignaturaProfesorRepository.exists(asignaturaProfesorId)) {
                log.debug("asignaturaProfesorId (asignaturaProfesorId) EXISTIS");
                asignatura = asignaturaProfesor.getAsignatura();
                log.debug("asignaturaProfesor.getAsignatura() {}", asignatura);
                AsignaturaFrontDto asigFrontDto= new AsignaturaFrontDto(asignatura.getId(), asignatura.getNombre(), asignatura.getPlan(),
                    asignatura.getTitulacion(), asignatura.getCreditos(),asignatura.getNum_grupos(),
                    asignatura.getCreditos_teoricos(), asignatura.getCreditos_practicas(),asignatura.getNum_grupos_teoricos(),
                    asignatura.getNum_grupos_practicas(), asignatura.getUsu_alta(), timeStampDate.toString());
                asignaturaFrontDto = asigFrontDto;
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaFrontDto));
    }


 /////////////////////////////////////modificacion a n:m  25-11-208- OK

    /**
     *
     * @param asignaturaId id de la asignatura que se quiere buscar
     * @param profesorId id del profesor del que se quiere saber si tiene la asignatura
     * @return true o false si el profesor ya tiene asignada la asignatura
     */
    @GetMapping ("/asignaturaprofesors/checkAsignaturainProfesor/{asignaturaId}/{profesorId}")
    @Timed
    public ResponseEntity <Boolean> checkAsignaturainProfesor (@PathVariable Long asignaturaId,@PathVariable Long profesorId){
        log.debug("REST request to get checkAsignaturainProfesor from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);

        log.debug("PROFESOR: {}",profesorId);
        log.debug("ASIGNATURA: {}",asignaturaId);
        boolean exist = false;
        List <AsignaturaProfesor> asignaturaProfesorList = null;
        asignaturaProfesorList = asignaturaProfesorRepository.findByprofyasig(profesorId,asignaturaId);
        if (asignaturaProfesorList !=null && !asignaturaProfesorList.isEmpty()){
            exist = true;
        }
        log.debug("ASIGNATURA CHECK: {}",asignaturaProfesorList);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exist));
    }


    //////////////////////////////////////modificacion a n:m  26-11-208- OK
    /**
     *
     * @param asignaturaId asignatura que se desea buscar
     * @param profesorId profesor del que se quiere saber cuantas veces tiene asignada dicha asignatura
     * @return un Integer que contiene el número de veces que tiene asignado un profesor una asignatura
     */
   @GetMapping ("/asignaturaprofesors/countsubject/{asignaturaId}/{profesorId}")
   @Timed
    public ResponseEntity <Integer> countSubject (@PathVariable Long asignaturaId, @PathVariable Long profesorId){
       log.debug("REST request count subject to a teacher from asignaturaId: {} profesorId: {}", asignaturaId, profesorId);
       List <AsignaturaProfesor> asignaturaProfesorList = null;
       asignaturaProfesorList = asignaturaProfesorRepository.findByprofyasig(profesorId,asignaturaId);

       return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaProfesorList.size()));
   }

//////////////////////////////modificacion a n:m  26-11-208- OK

    /**
     * Servicio GET
     * @param asignaturaId id de la asignatura a buscar
     * @param profesorId  id del profesor que quiere hacer la selección
     * @return una lista de profesores que tienen la prioridad más baja que el profesor indicado
     */
   @GetMapping ("/asignaturaprofesors/getlowerpriority/{asignaturaId}/{profesorId}")
   @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
   @Timed
   public ResponseEntity<List<Profesor>> getLowerPriority(@PathVariable Long asignaturaId, @PathVariable Long profesorId) {
       log.debug("REST request to get lower priority from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);
       List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
       List <AsignaturaProfesor> asignaturaProfesoreList = new ArrayList<>();
       Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
       Profesor prof = profesorRepository.findOne(profesorId);
       //profesores = profesorRepository.findAll();
       List <Profesor> lowerPriorityProfesor = new ArrayList<>();
       //profesoresList = getProfesoresList(asignatura);
       asignaturaProfesoreList = asignatura.getProfesors();
       for (AsignaturaProfesor ap: asignaturaProfesoreList
            ) {
           profesoresList.add(profesorRepository.findOne(ap.getProfAsigpk().getId_profesor()));
       }
       log.debug("profesoresList in LOWERPRIPRITY : {}", profesoresList);

       lowerPriorityProfesor = lowerPriorityTeachers(profesoresList,prof);
       return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lowerPriorityProfesor));
   }

////////////////////////////////////modificacion a n:m  26-11-208- OK
    /**
     *
     * @param profesoresList lista de profesores que tienen asignada una asignatura
     * @param prof profesor que quiere seleccionar dicha asignatura para impartir
     * @return Array de profesores que tienen la prioridad más baja que la del profesor que quiere hacer la elección
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Profesor> lowerPriorityTeachers (List<Profesor> profesoresList, Profesor prof ){
        List <Profesor> lowerPriorityProfesor = new ArrayList<>();
        //TODO revisar si el 1 es la mayor prioridad o la menor
        /** agrega a un array todos los profesores que tienen menor prioridad
         *que el profesore al que se quiere asignar la asignatura. prioridad grande es
         * menor, por ejemplo el q tenga prioridad 1 tiene mas prioridad que los demás
         **/
        for (Profesor profesor: profesoresList
            ) {
            if (profesor.getPrioridad() > prof.getPrioridad() && profesor.getId() != prof.getId()){
                lowerPriorityProfesor.add(profesor);
            }
        }
       log.debug("(LOWER PRIORITY): {} ",lowerPriorityProfesor);
       return lowerPriorityProfesor;
    }

    ////////////////////////////////modificacion a n:m  26-11-208- OK

    /**
     * Servicio GET
     * @param asignaturaId id de la asignatura a buscar
     * @param profesorId  id del profesor que quiere hacer la selección
     * @return una lista de profesores que tienen la prioridad más alta que el profesor indicado
     */
    @GetMapping ("/asignaturaprofesors/gethighestpriority/{asignaturaId}/{profesorId}")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Profesor>> getHighestPriority(@PathVariable Long asignaturaId, @PathVariable Long profesorId) {
        log.debug("REST request to get highest priority from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);
        List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
        Asignatura asignatura = asignaturaRepository.findOne(asignaturaId);
        Profesor prof = profesorRepository.findOne(profesorId);
        List <Profesor> highestPriorityProfesor = new ArrayList<>();
        List <AsignaturaProfesor> asignaturaProfesoreList = new ArrayList<>();
        asignaturaProfesoreList = asignatura.getProfesors();
        for (AsignaturaProfesor ap: asignaturaProfesoreList
            ) {
            profesoresList.add(profesorRepository.findOne(ap.getProfAsigpk().getId_profesor()));
        }
        log.debug("profesoresList in HIGHESTPRIORITY : {}", profesoresList);
        highestPriorityProfesor = highestPriorityTeachers(profesoresList,prof);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(highestPriorityProfesor));
    }
//////////////////////////////////////modificacion a n:m  26-11-208- OK
    /**
     *
     * @param profesoresList lista de los profesores que tienen una asignatura asignada
     * @param prof profesor que quiere seleccionar una asignatura para impartir
     * @return lista de los profesores con la prioridad más alta que el profesor que quiere hacerl a selección
    */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public List<Profesor> highestPriorityTeachers (List<Profesor> profesoresList, Profesor prof){
       List <Profesor> highestPriorityProfesor = new ArrayList<>();
       //TODO revisar si el 1 es la mayor prioridad o la menor
       /** agrego a un array todos los profesores que tienen MAYOR prioridad
        *que el profesore al que se quiere asignar la asignatura. prioridad grande es
        * menor, por ejemplo el q tenga prioridad 1 tiene mas prioridad que los demás
        **/
       log.debug("PROFESORESlIST IN HIGHEST PRIORITY {}",profesoresList);
        for (Profesor profesor: profesoresList
             ) {
            if (profesor.getPrioridad() < prof.getPrioridad() && profesor.getId() != prof.getId()){
                highestPriorityProfesor.add(profesor);
            }
        }
        log.debug("RETURN PROFESORESlIST highestPriorityProfesor {}",highestPriorityProfesor);
        return highestPriorityProfesor;
    }
//////////////////////////////////////////////////
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


}
