package com.pfg.easyschedule.web.rest;

import com.pfg.easyschedule.EasyscheduleApp;
import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.domain.AsignaturaProfesor;
import com.pfg.easyschedule.domain.AsignaturaProfesorId;
import com.pfg.easyschedule.domain.Profesor;
import com.pfg.easyschedule.repository.AsignaturaProfesorRepository;
import com.pfg.easyschedule.repository.AsignaturaRepository;
import com.pfg.easyschedule.repository.ProfesorRepository;
import com.pfg.easyschedule.web.rest.errors.ExceptionTranslator;
import org.junit.Assume;
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
   /* private static final Long DEFAULT_NUM_CREDITOS = Long.valueOf(3);
    private  static final Date fecha_seleccion = new Date();
    private static final long id_asig = 1;
    private static final long id_profesor = 2;*/
    private static final Long num_creditos = Long.valueOf(6);
////
    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_COD_PROFESOR = 3;
    private static final Integer UPDATED_COD_PROFESOR = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_CREDITOS_IMPARTIR = 1;
    private static final Integer UPDATED_NUM_CREDITOS_IMPARTIR = 2;

    private static final Integer DEFAULT_PRIORIDAD = 2;
    private static final Integer UPDATED_PRIORIDAD = 1;

    private static final String DEFAULT_LOGIN = "CCCCC";
    private static final String UPDATED_LOGIN = "FFFFFF";

    private static final String DEFAULT_USU_ALTA = "AAAAAAAAAA";
    private static final String UPDATED_USU_ALTA = "BBBBBBBBBB";


    private  static final Date fecha_seleccion = new Date();
    private static final long id_asig = 1L;
    private static final long id_profesor = 2L;
    private static final Long DEFAULT_NUM_CREDITOS = Long.valueOf(6);



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
    private MockMvc restProfesorMockMvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Profesor profesor = new Profesor(1L, "aaa", "bbb", "ccc",
            1, "ddd", "A", 6,
            1, "eee", "fff");
        Asignatura asignatura = new Asignatura(1L, "aaa", "bbb", "ccc", 6,
            1, 1, 1, 1, 1,
            1, "ddd");
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
            .num_creditos(DEFAULT_NUM_CREDITOS)
            ;
        return asignaturaProfesor;
    }

    @Before
    public void before() throws Exception {
        Assume.assumeTrue("someValue".equals(System.getProperty("some.property")));
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

    @Test
    @Transactional
    public void checkNumCreditosIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaProfesorRepository.findAll().size();
        // set the field null
        asignaturaProfesor.setNum_creditos(null);

        // Create the Profesor, which fails.

        restAsignaturaProfesorMockMvc.perform(post("/api/asignaturaprofesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isBadRequest());
            //.andExpect(status().isOk());

        List<AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findAll();
        assertThat(asignaturaProfesorList).hasSize(databaseSizeBeforeTest);
    }

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

   /* @Test
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

    /*@Test
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

    /*@Test
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

    /*@Test
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
    public void getAllAsignaturasProfesor() throws Exception {
        // Initialize the database
        asignaturaProfesorRepository.saveAndFlush(asignaturaProfesor);

        // Get all the profesorList
        restProfesorMockMvc.perform(get("/api/asignaturaprofesors"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].idProfesor").value(hasItem(asignaturaProfesor.getProfAsigpk().getId_profesor())))
            .andExpect(jsonPath("$.[*].idAsignatura").value(hasItem(asignaturaProfesor.getProfAsigpk().getId_asignatura())))
            .andExpect(jsonPath("$.[*].fechaSeleccion").value(hasItem(asignaturaProfesor.getProfAsigpk().getFechaSeleccion())))
            .andExpect(jsonPath("$.[*].num_creditos").value(hasItem(num_creditos.toString())))

        ;
    }*/

    /*@Test
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
    public void updateAsignaturaProfesor() throws Exception {
        // Initialize the database
        asignaturaProfesorRepository.saveAndFlush(asignaturaProfesor);
        int databaseSizeBeforeUpdate = asignaturaProfesorRepository.findAll().size();

        // Update the asignaturaProfesor
        AsignaturaProfesor updatedAsignaturaProfesor = asignaturaProfesorRepository.findOne(asignaturaProfesor.getProfAsigpk());
        updatedAsignaturaProfesor
            .num_creditos(num_creditos)
           ;

        restAsignaturaProfesorMockMvc.perform(put("/api/asignaturaprofesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsignaturaProfesor)))
            .andExpect(status().isOk());

        // Validate the Profesor in the database
        List<AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findAll();
        assertThat(asignaturaProfesorList).hasSize(databaseSizeBeforeUpdate);
        AsignaturaProfesor testAsignaturaProfesor = asignaturaProfesorList.get(asignaturaProfesorList.size() - 1);
        assertThat(testAsignaturaProfesor.getNum_creditos()).isEqualTo(num_creditos);
    }*/

    @Test
    @Transactional
    public void updateNonExistingAsignaturaProfesor() throws Exception {
        int databaseSizeBeforeUpdate = asignaturaProfesorRepository.findAll().size();

        // Create the AsignaturaProfesor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAsignaturaProfesorMockMvc.perform(put("/api/asignaturaprofesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            .andExpect(status().isCreated());

        // Validate the AsignaturaProfesor in the database
        List<AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findAll();
        assertThat(asignaturaProfesorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    /*@Test
    @Transactional
    public void deleteAsignaturaProfesor() throws Exception {
        // Initialize the database
        asignaturaProfesorRepository.saveAndFlush(asignaturaProfesor);
        int databaseSizeBeforeDelete = asignaturaProfesorRepository.findAll().size();

        // Get the asignaturaProfesor
        /*restAsignaturaProfesorMockMvc.perform(post("/api/asignaturaprofesors/deleteselection", asignaturaProfesor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());*/

       /* restAsignaturaProfesorMockMvc.perform(post("/api/asignaturaprofesors/deleteselection")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignaturaProfesor)))
            //.andExpect(status().isBadRequest());
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AsignaturaProfesor> asignaturaProfesorList = asignaturaProfesorRepository.findAll();
        assertThat(asignaturaProfesorList).hasSize(databaseSizeBeforeDelete - 1);
    }*/

    /*@Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profesor.class);
    }*/

}



