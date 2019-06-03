package com.pfg.easyschedule.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.service.MailService;
import com.pfg.easyschedule.web.rest.util.AsignaturaFrontDto;
import com.pfg.easyschedule.web.rest.util.AsignaturaProfesorFrontDto;
import com.pfg.easyschedule.web.rest.util.HeaderUtil;
import com.pfg.easyschedule.web.rest.util.ProfesorFrontDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    MailService mailService;

    public AsignaturaProfesorResource(AsignaturaRepository asignaturaRepository, ProfesorRepository profesorRepository, AsignaturaProfesorRepository asignaturaProfesorRepository) {
        this.profesorRepository = profesorRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.asignaturaProfesorRepository = asignaturaProfesorRepository;
    }

    /**
     * POST  /asignaturaprofesors : to assign one subject to teacher.
     *
     * @param ,  the id_prof,id_asig and subject´s points to assign a subject to teacher
     * @return the ResponseEntity with status 201 (Created) and with body the new asignatura, or with status 400 (Bad Request) if the asignatura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asignaturaprofesors")
    public ResponseEntity<AsignaturaProfesor> createAsignaturaProfesor(@RequestBody Map<String, String> datos) throws URISyntaxException {
        log.debug("REST request to save AsignaturaProfesor asignatura : {}", datos.get("asignaturaId"));
        log.debug("REST request to save AsignaturaProfesor  profesor : {}", datos.get("profesorid"));
        log.debug("REST request to save AsignaturaProfesor  profesor : {}", datos.get("num_creditos"));
        String idProfesor = datos.get("profesorid");
        String idAsignatura = datos.get("asignaturaId");
        String numCreditos = datos.get("num_creditos");
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asig = Long.parseLong(idAsignatura, 10);
        Long  num_creditos = Long.parseLong(numCreditos, 10);

        AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(id_prof, id_asig, new Date());
        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor(asignaturaProfesorId,num_creditos);

        if (asignaturaProfesorRepository.exists(asignaturaProfesorId)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new asignatura profesor cannot already have an ID")).body(null);
        }else{
            AsignaturaProfesor result = asignaturaProfesorRepository.save(asignaturaProfesor);

            //AsignaturaProfesorFrontDto asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto(asignaturaProfesor);
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>ASIGNATURAS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
            return Optional.ofNullable(result)
                .map(myresult -> new ResponseEntity<>(
                    myresult,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }
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
        log.debug("REST POST deleteAsignaturaProfesors: {}", asignaturaborrar);
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
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
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
    //////////05-01-19
    /**
     * GET  /asignaturas : get all the asignaturas.
     *
     * @param
     * @return the ResponseEntity with status 200 (OK) and with body the asignaturaProfesors, or with status 404 (Not Found)
     */
    @GetMapping("/asignaturaprofesors")
    @Timed
    public ResponseEntity<List<AsignaturaProfesor>> getAllAsignaturasProfesor() {
        log.debug("REST request to get  getAllAsignaturasProfesor");
        List <AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findAll();

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaProfesorList));
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
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
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

        //log.debug("STRINGS DEL MAP PARAMETER EN GETSUBJECT: idProfesor:{} idAsignatura:{} fechaseleccion:{}",idProfesor , idAsignatura,fechaseleccion);
        //Timestamp timeStampDate = null;
        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asignatura = Long.parseLong(idAsignatura, 10);
        //Long numCreditos= Long.parseLong(num_creditos, 10);
       // AsignaturaProfesorFrontDto asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto(id_prof, id_asignatura,fechaseleccion);
        //log.debug("AsignaturaProfesorFrontDto: {}",asignaturaProfesorFrontDto);
        ///
        java.sql.Timestamp timeStampDate = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
            //Date date = formatter.parse(fechaseleccion);
            Date date = formatter.parse(fechaseleccion);
            log.debug("date fecha de seleccion: {}", date);
            timeStampDate = new Timestamp(date.getTime());
            log.debug("timeStampDate fecha de seleccion: {}", timeStampDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //

       /* try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
          /*  Date date = formatter.parse(fechaseleccion);
            timeStampDate = new Timestamp(date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        if (id_prof != null && id_asignatura != null && timeStampDate!= null) {
            AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(id_prof, id_asignatura, timeStampDate);
            log.debug("asignaturaprofesor id  en getsubjct:  {}",asignaturaProfesorId);
            AsignaturaProfesor asignaturaProfesor = asignaturaProfesorRepository.findOne(asignaturaProfesorId);
            log.debug("asignaturaprofesor  a buscar en getsubjct:  {}, EXIST: {}",asignaturaProfesor , asignaturaProfesorRepository.exists(asignaturaProfesorId));
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
     * @return 1 (true) o 0(false) si el profesor ya tiene asignada la asignatura
     */
    @GetMapping ("/asignaturaprofesors/checkAsignaturainProfesor/{asignaturaId}/{profesorId}")
    @Timed
    public ResponseEntity <Integer> checkAsignaturainProfesor (@PathVariable Long asignaturaId,@PathVariable Long profesorId){
        log.debug("REST request to get checkAsignaturainProfesor from asignaturaId: {} profesorId: {}", asignaturaId,profesorId);

        log.debug("PROFESOR: {}",profesorId);
        log.debug("ASIGNATURA: {}",asignaturaId);
        Integer exist = 0;
        List <AsignaturaProfesor> asignaturaProfesorList = null;
        asignaturaProfesorList = asignaturaProfesorRepository.findByprofyasig(profesorId,asignaturaId);
        if (asignaturaProfesorList !=null && !asignaturaProfesorList.isEmpty()){
            exist = 1;
        }
        log.debug("ASIGNATURA CHECK: {}",asignaturaProfesorList);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exist));
    }
    //////////////////////////////////////modificacion a n:m  26-11-208- OK
    /**
     *
     * @param asignaturaId asignatura que se desea buscar
     * @param profesorId profesor del que se quiere saber cuantas veces tiene asignada dicha asignatura
     * @return AsignaturaProfesorFrontDto. Contiene el atributo numVecesAsigSeleccionada.
     * Éste es el número de veces que tiene asignado un profesor una asignatura
     */
   @GetMapping ("/asignaturaprofesors/countsubject/{asignaturaId}/{profesorId}")
   @Timed
    public ResponseEntity <AsignaturaProfesorFrontDto> countSubject (@PathVariable Long asignaturaId, @PathVariable Long profesorId){
       log.debug("REST request count subject to a teacher from asignaturaId: {} profesorId: {}", asignaturaId, profesorId);
       List <AsignaturaProfesor> asignaturaProfesorList = null;
       asignaturaProfesorList = asignaturaProfesorRepository.findByprofyasig(profesorId,asignaturaId);
       AsignaturaProfesorFrontDto asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto();
       asignaturaProfesorFrontDto.setNumVecesAsigSeleccionada((long) asignaturaProfesorList.size());
       return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaProfesorFrontDto));
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
        //ordeno los profesores por prioridad antes de devolverlos
        profesoresList.sort(Comparator.comparing(Profesor::getPrioridad));
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
        log.debug("asignaturaProfesoreList  : {}", asignaturaProfesoreList);
        for (AsignaturaProfesor ap: asignaturaProfesoreList
            ) {
            profesoresList.add(profesorRepository.findOne(ap.getProfAsigpk().getId_profesor()));
        }
        log.debug("profesoresList in HIGHESTPRIORITY : {}", profesoresList);

        highestPriorityProfesor = highestPriorityTeachers(profesoresList,prof);
        log.debug("returngethighestpriority : {}", highestPriorityProfesor);
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
            log.debug("profesor.getPrioridad {}",profesor.getPrioridad());
            log.debug("prof.getPrioridad() {}",prof.getPrioridad());
            log.debug("profesor.getPrioridad() < prof.getPrioridad() && profesor.getId() != prof.getId() {}",profesor.getPrioridad() < prof.getPrioridad() && profesor.getId() != prof.getId());
            if (profesor.getPrioridad() < prof.getPrioridad() && profesor.getId() != prof.getId()){
                highestPriorityProfesor.add(profesor);
            }
        }
        //ordeno los profesores por prioridad antes de devolverlos
        //profesoresList.sort(Comparator.comparing(Profesor::getPrioridad));
        highestPriorityProfesor.sort(Comparator.comparing(Profesor::getPrioridad));
        log.debug("RETURN PROFESORESlIST highestPriorityProfesor {}",highestPriorityProfesor);
        return highestPriorityProfesor;
    }
//////////////////////////////////////////////////probada el 02-12-18 OK. estado 7
    /**
     * @param asignatura  to find
     * @return the ResponseEntity with status 200 (OK) and with body the profesors, or with status 404 (Not Found)
       Profesores que tienen asignada una asignatura
     */
    @PostMapping ("/asignaturaprofesors/asignaturainprof")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<List<Profesor>> getSubjectInProfesores(@RequestBody Asignatura asignatura) {
        log.debug("REST request to get asignatura in Profesors: {}", asignatura);
        List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
        List <AsignaturaProfesor> asignaturaProfesorList =
            asignaturaProfesorRepository.findByAsignatura(asignatura.getId());

        for (AsignaturaProfesor asigProf: asignaturaProfesorList
             ) {
            profesoresList.add(asigProf.getProfesor());
        }
        log.debug(" PROFESORES CON LA ASIGNATURA profesoresList: {}", profesoresList);
        //ordeno los profesores por prioridad antes de devolverlos
        profesoresList.sort(Comparator.comparing(Profesor::getPrioridad));
       // Collections.sort(profesoresList);
        log.debug(" PROFESORES  profesoresList ORDENADA: {}", profesoresList);
        //getLowerPriority(profesoresList)
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesoresList));
    }
    //////////
    /*public void sortProfesorBy(Profesor.Order sortingBy) {
        List<Profesor> profesorList = this.profesoresList;  // useless line, just for clarification
        FlexiblePersonComparator comparator = new FlexiblePersonComparator();
        comparator.setSortingBy(sortingBy);
        Collections.sort(persons, comparator); // now we have a sorted list
    }*/
///////////////////////////////////////////////////02-12-18 OK
    /**
     * Servicio GET
     * @param profesorId  id del profesor
     * @return ProfesorFrontDto. Contiene un atributo que indica el número de creditos restantes
     * para cubrir el total de creditos a impartir por el profesor
     */
    @GetMapping ("/asignaturaprofesors/creditosdisponibles/{profesorId}")
    @Timed
    public ResponseEntity<ProfesorFrontDto> getCreditosdisponibles(@PathVariable Long profesorId) {
        log.debug("REST request to get highest priority from  profesorId: {}", profesorId);

        Profesor profesor = profesorRepository.findOne(profesorId);
        Integer creditosTotales = profesor.getNumCreditosImpartir();
        long creditosSeleccionados = 0;
        List <AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findByProfesor(profesorId);
        for (AsignaturaProfesor asignaturaProfesor: asignaturaProfesorList
             ) {
            creditosSeleccionados = creditosSeleccionados + asignaturaProfesor.getNum_creditos();
        }

        long creditosDisponibles = creditosTotales - creditosSeleccionados;
        log.debug("creditosDisponibles: {}", creditosDisponibles);
        ProfesorFrontDto profesorFrontDto = new ProfesorFrontDto();
        profesorFrontDto.setCreditosLibres(creditosDisponibles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesorFrontDto));
    }
    ///////////////////////reasignacion/:profmenorprioridadId/:profesorid
    /**
     * Servicio POST
     * Borra al profesor de menor prioridad la asignatura que tiene asignada y se la asigna al
     * profesor que tiene mayor prioridad
     * @param datos  Map con los datos del id de los profesores de menor y mayor prioridad, el id de la asignatura y el número de creditos
     * para la reasignacion de una asignatura     * @return numero de creditos restantes para cubrir el total de creditos a impartir por el profesor
     */
    @PostMapping ("/asignaturaprofesors/reasignacion")
    //@Timed
    public ResponseEntity<Boolean> reasignacion(@RequestBody Map<String, String> datos) {
        log.debug("REST POST reasignacion datos: {} ",datos);
        String profMenorPrioridadId = datos.get("profmenorprioridadId");
        String profMayorPrioridadId = datos.get("profesorid");
        String asignaturaId = datos.get("asignaturaId");
        String num_creditos = datos.get("num_creditos");
        Long idProfMenorPrioridad= Long.parseLong(profMenorPrioridadId, 10);
        Long  idProfMayorPrioridad = Long.parseLong(profMayorPrioridadId, 10);
        Long  id_asignatura = Long.parseLong(asignaturaId, 10);
        Long  numCreditos = Long.parseLong(num_creditos, 10);

        Profesor profesorMenorPrioridad = profesorRepository.findOne(idProfMenorPrioridad);
        Profesor profesorMayorPrioridad = profesorRepository.findOne(idProfMayorPrioridad);
        //obtengo la lista de las asignaciones que tiene el profesor de menor prioridad para un asignatura
        List <AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findByProfesor(profesorMenorPrioridad.getId());
        log.debug("ASIGNATURA POR PROFESOR: {}", asignaturaProfesorList);
        AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(idProfMayorPrioridad,id_asignatura,new Date());
        AsignaturaProfesor nuevaAsignaturaProfesor = new AsignaturaProfesor(asignaturaProfesorId,numCreditos);
        int contador= asignaturaProfesorList.size() - 1;
        log.debug("asignaturaProfesorList.size() ",asignaturaProfesorList.size());
        log.debug("CONTADOR",contador);
        AsignaturaProfesor asignaturaProfesor = null;
        boolean agregado = false;

        if (contador == 0) { //cuando solo hay uno con menor prioridad que la del profesor que esta seleccionando la asignatura
            if (asignaturaProfesorList.get(contador).getNum_creditos() == numCreditos){
                asignaturaProfesor = asignaturaProfesorList.get(contador);
            }
        }

        while (contador > 0 && asignaturaProfesor  == null){
            log.debug("asignaturaProfesorList.get(contador).getNum_creditos(): {}",asignaturaProfesorList.get(contador).getNum_creditos());
            if (asignaturaProfesorList.get(contador).getNum_creditos() == numCreditos){
                asignaturaProfesor = asignaturaProfesorList.get(contador);
            }
            contador --;
        }

        log.debug("MENOR PRIORIDAD PROFESORLIST: {}", asignaturaProfesor);




        if (asignaturaProfesor != null){
            String mensaje = "Se le ha desasignado la asignatura: "
                +asignaturaProfesor.getAsignatura().getNombre()+
                " seleccionada el dia: " + asignaturaProfesor.getProfAsigpk().getFechaSeleccion()+
                " con el número de créditos: "+
                asignaturaProfesor.getNum_creditos()
                +" Debe entrar nuevamente a la aplicación y seleccionar una asignatura nueva.";
            //Borra la asignación de la asignatura que tenga el mismo número de créditos si existe un con el mismo num de creditos
            asignaturaProfesorRepository.delete(asignaturaProfesor.getProfAsigpk());
            log.debug("exist asig borrada: {} ",asignaturaProfesorRepository.exists(asignaturaProfesor.getProfAsigpk()));
            log.debug("eliminada asignacion menor prioridad: {} ",asignaturaProfesor.getProfAsigpk());
            log.debug("MAILSENDER  MAILSERVICE : {}", mailService);
            mailService.sendEmail(
                "blk20100@gmail.com",
                "Mensaje de prueba desde spring",
                "prueba de mail sender",
                true,
                true
            );
        }else{
            //si no hay una asignación que tenga el mismo de creditos, elimino la primera asignación de la lista.
            log.debug("NO SE HA ENCONTRADO LA ASIGNACION DEL PROFESOR DE MENOR PRIORIDAD: {}",asignaturaProfesor);
            asignaturaProfesorRepository.delete(asignaturaProfesorList.get(0).getProfAsigpk());
            log.debug("asignaturaProfesorList.get(0).getProfAsigpk(): {} ",asignaturaProfesorList.get(0).getProfAsigpk());
            //asignaturaProfesorRepository.delete(asignaturaProfesorList.get(0));
            //log.debug("eliminada asignacion menor prioridad: {} ",asignaturaProfesorList.get(0));
            log.debug("MAILSENDER  MAILSERVICE : {}", mailService);

            String mensaje = "Se le ha desasignado la asignatura: "
                +asignaturaProfesorList.get(0).getAsignatura().getNombre()+
                " seleccionada el dia: " + asignaturaProfesorList.get(0).getProfAsigpk().getFechaSeleccion()+
                " con el número de créditos: "+
                asignaturaProfesorList.get(0).getNum_creditos()
                +" Debe entrar nuevamente a la aplicación y seleccionar una asignatura nueva.";

            mailService.sendEmail(
                "blk20100@gmail.com",
                "Mensaje de prueba desde spring",
                "prueba de mail sender",
                true,
                true
            );
        }
        /*
        "Este messaje de prueba para avisar que se le ha quitado la asignatura: "+asignaturaProfesor.getAsignatura()+
                    " seleccionada el dia: " + asignaturaProfesor.getProfAsigpk().getFechaSeleccion()+" con el número e créditos: "+
                    asignaturaProfesor.getNum_creditos(),
         */
        asignaturaProfesorRepository.save(nuevaAsignaturaProfesor);
        agregado=true;

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agregado));
    }

    ////////creado 25-12-18
    /**
     * POST  /asignaturaprofesors/getasigprof : get the "asignaturaProfesor" con nombre de profesor,
     * nombre de asignatura y numero de creditos elegidos
     * of asignaturaProfesor para un profesor.
     *
     * @param json
     * @return the ResponseEntity  con AsignaturaProfesorFrontDto with status 200 (OK) and with body the subjects of a teacher, or with status 404 (Not Found)
     */
    @PostMapping (value = "/asignaturaprofesors/getasigprof")
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<AsignaturaProfesorFrontDto> getAsignaturaProfesorFrontDto(@RequestBody Map<String, String> json) {
        log.debug("REST POST getProfesorSubjects: {}", json);
        Asignatura asignatura = new Asignatura();
        AsignaturaProfesorFrontDto asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto();
        AsignaturaFrontDto asignaturaFrontDto = new AsignaturaFrontDto();

        String idProfesor = json.get("id_profesor");
        String idAsignatura = json.get("id_asignatura");
        String fechaseleccion = json.get("fecha_seleccion");

        Long id_prof= Long.parseLong(idProfesor, 10);
        Long  id_asignatura = Long.parseLong(idAsignatura, 10);
        java.sql.Timestamp timeStampDate = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");/*yyyy-MM-dd'T'hh:mm:ss.SSS*/
            //Date date = formatter.parse(fechaseleccion);
            Date date = formatter.parse(fechaseleccion);
            log.debug("date fecha de seleccion: {}", date);
            timeStampDate = new Timestamp(date.getTime());
            log.debug("timeStampDate fecha de seleccion: {}", timeStampDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (id_prof != null && id_asignatura != null && timeStampDate!= null) {
            AsignaturaProfesorId asignaturaProfesorId = new AsignaturaProfesorId(id_prof, id_asignatura, timeStampDate);
            log.debug("asignaturaprofesor id  en getsubjct:  {}",asignaturaProfesorId);
            Profesor profesor = profesorRepository.findOne(id_prof);
            AsignaturaProfesor asignaturaProfesor = asignaturaProfesorRepository.findOne(asignaturaProfesorId);
            log.debug("asignaturaprofesor  a buscar en getsubjct:  {}, EXIST: {}",asignaturaProfesor , asignaturaProfesorRepository.exists(asignaturaProfesorId));
            if (asignaturaProfesorRepository.exists(asignaturaProfesorId)) {
                log.debug("asignaturaProfesorId (asignaturaProfesorId) EXISTIS");
                asignatura = asignaturaProfesor.getAsignatura();
                log.debug("asignaturaProfesor.getAsignatura() {}", asignatura);
                log.debug("asignaturaProfesor.getProfesor() {}", profesor);
                asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto(asignaturaProfesor, profesor.getPrimerApellido()+" "+profesor.getSegundoApellido()+" "+
                    profesor.getNombre(),asignatura.getNombre());
                log.debug("asignaturaProfesorFrontDto  {}", asignaturaProfesorFrontDto);
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaProfesorFrontDto));
    }


    /**
     * @param asignatura  to find
     * @return the ResponseEntity with status 200 (OK) and with body the profesors, or with status 404 (Not Found)
     */
    @PostMapping (value  = "/asignaturaprofesors/numCreditosSeleccionadosAsignatura",
        produces = { MediaType.APPLICATION_JSON_VALUE })
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Timed
    public ResponseEntity<AsignaturaProfesorFrontDto> getNumCreditosSeleccionadosAsignatura(@RequestBody Asignatura asignatura) {
        log.debug("REST request to get getNumCreditosSeleccionadosAsignatura: {}", asignatura);
        List <Profesor> profesoresList = new ArrayList<>(); //profesores que tienen asignada la asignatura
        List <AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findByAsignatura(asignatura.getId());
        Long numCreditosSeleccionados = Long.valueOf(0);
        for (AsignaturaProfesor asigProf: asignaturaProfesorList ) {
            //profesoresList.add(asigProf.getProfesor());
            numCreditosSeleccionados= numCreditosSeleccionados + asigProf.getNum_creditos();
        }
        log.debug(" CREDITOS SELECCIONADOS DE ASIGNATURA : {} = ", asignatura, numCreditosSeleccionados);
        AsignaturaProfesorFrontDto asignaturaProfesorFrontDto = new AsignaturaProfesorFrontDto();
        asignaturaProfesorFrontDto.setNumCreditosSeleccionadosAsignatura(numCreditosSeleccionados);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asignaturaProfesorFrontDto));
    }
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
