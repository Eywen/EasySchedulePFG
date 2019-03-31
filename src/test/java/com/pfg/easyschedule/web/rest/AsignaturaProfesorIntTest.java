package com.pfg.easyschedule.web.rest;

import com.pfg.easyschedule.EasyscheduleApp;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ProfesorResource REST controller.
 *
 * @see ProfesorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyscheduleApp.class)
public class AsignaturaProfesorIntTest {
    private static final Long DEFAULT_NUM_CREDITOS = Long.valueOf(3);
    private  static final Date fecha_seleccion = new Date();
    private static final long id_asig = 1;
    private static final long id_profesor = 2;
    private static final Long num_creditos = Long.valueOf(6);



    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AsignaturaProfesorRepository asignaturaProfesorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAsignaturaProfesorMockMvc;

    private AsignaturaProfesor asignaturaProfesor;
    private AsignaturaProfesorId asignaturaProfesorId;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        asignaturaProfesorId = new AsignaturaProfesorId(id_profesor, id_asig, fecha_seleccion);
        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor(asignaturaProfesorId, num_creditos);
        this.restAsignaturaProfesorMockMvc = MockMvcBuilders.standaloneSetup(asignaturaProfesor)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AsignaturaProfesor createEntity(EntityManager em) {
        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor()
            .profasigpk(new AsignaturaProfesorId(id_profesor, id_asig, fecha_seleccion))
            .num_creditos(num_creditos)
            ;
        return asignaturaProfesor;
    }

    @Before
    public void initTest() {
        asignaturaProfesor = createEntity(em);

    }

   /* @Test
    @Transactional
    public void createProfesor() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // Create the Profesor
        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isCreated());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate + 1);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getNombre()).isEqualTo(DEFAULT_NUM_CREDITOS);
        assertThat(testProfesor.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testProfesor.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testProfesor.getCodProfesor()).isEqualTo(DEFAULT_COD_PROFESOR);
        assertThat(testProfesor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfesor.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProfesor.getNumCreditosImpartir()).isEqualTo(DEFAULT_NUM_CREDITOS_IMPARTIR);
        assertThat(testProfesor.getPrioridad()).isEqualTo(DEFAULT_PRIORIDAD);
        assertThat(testProfesor.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testProfesor.getUsuAlta()).isEqualTo(DEFAULT_USU_ALTA);
    }*/

    /*@Test
    @Transactional
    public void createProfesorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profesorRepository.findAll().size();

        // Create the Profesor with an existing ID
        asignaturaProfesor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeCreate);
    }*/

    /*@Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setNombre(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setPrimerApellido(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkSegundoApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setSegundoApellido(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkCodProfesorIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setCodProfesor(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

   /* @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setEmail(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

   /* @Test
    @Transactional
    public void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setCategoria(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkPrioridadIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setPrioridad(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

   /* @Test
    @Transactional
    public void checkLoginRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setLogin(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkUsuAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = profesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setUsuAlta(null);

        // Create the Profesor, which fails.

        restProfesorMockMvc.perform(post("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());

        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void getAllProfesors() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(asignaturaProfesor);

        // Get all the profesorList
        restProfesorMockMvc.perform(get("/api/profesors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asignaturaProfesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NUM_CREDITOS.toString())))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].codProfesor").value(hasItem(DEFAULT_COD_PROFESOR)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].numCreditosImpartir").value(hasItem(DEFAULT_NUM_CREDITOS_IMPARTIR)))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD)))
            .andExpect(jsonPath("$.[*].usuAlta").value(hasItem(DEFAULT_USU_ALTA.toString())))
        ;
    }*/

   /* @Test
    @Transactional
    public void getProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(asignaturaProfesor);

        // Get the asignaturaProfesor
        restProfesorMockMvc.perform(get("/api/profesors/{id}", asignaturaProfesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asignaturaProfesor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NUM_CREDITOS.toString()))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO.toString()))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO.toString()))
            .andExpect(jsonPath("$.codProfesor").value(DEFAULT_COD_PROFESOR))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.numCreditosImpartir").value(DEFAULT_NUM_CREDITOS_IMPARTIR))
            .andExpect(jsonPath("$.prioridad").value(DEFAULT_PRIORIDAD))
            .andExpect(jsonPath("$.usuAlta").value(DEFAULT_USU_ALTA.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));
    }*/

    /*@Test
    @Transactional
    public void getNonExistingProfesor() throws Exception {
        // Get the asignaturaProfesor
        restProfesorMockMvc.perform(get("/api/profesors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }*/

    /*@Test
    @Transactional
    public void updateProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(asignaturaProfesor);
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Update the asignaturaProfesor
        Profesor updatedProfesor = profesorRepository.findOne(asignaturaProfesor.getId());
        updatedProfesor
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .codProfesor(UPDATED_COD_PROFESOR)
            .email(UPDATED_EMAIL)
            .categoria(UPDATED_CATEGORIA)
            .numCreditosImpartir(UPDATED_NUM_CREDITOS_IMPARTIR)
            .prioridad(UPDATED_PRIORIDAD)
            .usuAlta(UPDATED_USU_ALTA)
            .login(UPDATED_LOGIN);

        restProfesorMockMvc.perform(put("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfesor)))
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate);
        Profesor testProfesor = profesorList.get(profesorList.size() - 1);
        assertThat(testProfesor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProfesor.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testProfesor.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testProfesor.getCodProfesor()).isEqualTo(UPDATED_COD_PROFESOR);
        assertThat(testProfesor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfesor.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProfesor.getNumCreditosImpartir()).isEqualTo(UPDATED_NUM_CREDITOS_IMPARTIR);
        assertThat(testProfesor.getPrioridad()).isEqualTo(UPDATED_PRIORIDAD);
        assertThat(testProfesor.getUsuAlta()).isEqualTo(UPDATED_USU_ALTA);
    }*/

    /*@Test
    @Transactional
    public void updateNonExistingProfesor() throws Exception {
        int databaseSizeBeforeUpdate = profesorRepository.findAll().size();

        // Create the Profesor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfesorMockMvc.perform(put("/api/profesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isCreated());

        // Validate the Profesor in the database
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeUpdate + 1);
    }*/

   /* @Test
    @Transactional
    public void deleteProfesor() throws Exception {
        // Initialize the database
        profesorRepository.saveAndFlush(asignaturaProfesor);
        int databaseSizeBeforeDelete = profesorRepository.findAll().size();

        // Get the asignaturaProfesor
        restProfesorMockMvc.perform(delete("/api/profesors/{id}", asignaturaProfesor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profesor> profesorList = profesorRepository.findAll();
        assertThat(profesorList).hasSize(databaseSizeBeforeDelete - 1);
    }*/

    /*@Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesor.class);
    }*/

}



