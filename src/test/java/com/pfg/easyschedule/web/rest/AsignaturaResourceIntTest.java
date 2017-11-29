package com.pfg.easyschedule.web.rest;

import com.pfg.easyschedule.EasyscheduleApp;

import com.pfg.easyschedule.domain.Asignatura;
import com.pfg.easyschedule.repository.AsignaturaRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AsignaturaResource REST controller.
 *
 * @see AsignaturaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyscheduleApp.class)
public class AsignaturaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_PLAN = "BBBBBBBBBB";

    private static final String DEFAULT_TITULACION = "AAAAAAAAAA";
    private static final String UPDATED_TITULACION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITOS = 1;
    private static final Integer UPDATED_CREDITOS = 2;

    private static final Integer DEFAULT_NUM_GRUPOS = 1;
    private static final Integer UPDATED_NUM_GRUPOS = 2;

    private static final Integer DEFAULT_CREDITOS_TEORICOS = 1;
    private static final Integer UPDATED_CREDITOS_TEORICOS = 2;

    private static final Integer DEFAULT_CREDITOS_PRACTICAS = 1;
    private static final Integer UPDATED_CREDITOS_PRACTICAS = 2;

    private static final Integer DEFAULT_NUM_GRUPOS_TEORICOS = 1;
    private static final Integer UPDATED_NUM_GRUPOS_TEORICOS = 2;

    private static final Integer DEFAULT_NUM_GRUPOS_PRACTICAS = 1;
    private static final Integer UPDATED_NUM_GRUPOS_PRACTICAS = 2;

    private static final String DEFAULT_USU_ALTA = "AAAAAAAAAA";
    private static final String UPDATED_USU_ALTA = "BBBBBBBBBB";

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAsignaturaMockMvc;

    private Asignatura asignatura;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AsignaturaResource asignaturaResource = new AsignaturaResource(asignaturaRepository);
        this.restAsignaturaMockMvc = MockMvcBuilders.standaloneSetup(asignaturaResource)
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
    public static Asignatura createEntity(EntityManager em) {
        Asignatura asignatura = new Asignatura()
            .nombre(DEFAULT_NOMBRE)
            .plan(DEFAULT_PLAN)
            .titulacion(DEFAULT_TITULACION)
            .creditos(DEFAULT_CREDITOS)
            .num_grupos(DEFAULT_NUM_GRUPOS)
            .creditos_teoricos(DEFAULT_CREDITOS_TEORICOS)
            .creditos_practicas(DEFAULT_CREDITOS_PRACTICAS)
            .num_grupos_teoricos(DEFAULT_NUM_GRUPOS_TEORICOS)
            .num_grupos_practicas(DEFAULT_NUM_GRUPOS_PRACTICAS)
            .usu_alta(DEFAULT_USU_ALTA);
        return asignatura;
    }

    @Before
    public void initTest() {
        asignatura = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsignatura() throws Exception {
        int databaseSizeBeforeCreate = asignaturaRepository.findAll().size();

        // Create the Asignatura
        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isCreated());

        // Validate the Asignatura in the database
        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeCreate + 1);
        Asignatura testAsignatura = asignaturaList.get(asignaturaList.size() - 1);
        assertThat(testAsignatura.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAsignatura.getPlan()).isEqualTo(DEFAULT_PLAN);
        assertThat(testAsignatura.getTitulacion()).isEqualTo(DEFAULT_TITULACION);
        assertThat(testAsignatura.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
        assertThat(testAsignatura.getNum_grupos()).isEqualTo(DEFAULT_NUM_GRUPOS);
        assertThat(testAsignatura.getCreditos_teoricos()).isEqualTo(DEFAULT_CREDITOS_TEORICOS);
        assertThat(testAsignatura.getCreditos_practicas()).isEqualTo(DEFAULT_CREDITOS_PRACTICAS);
        assertThat(testAsignatura.getNum_grupos_teoricos()).isEqualTo(DEFAULT_NUM_GRUPOS_TEORICOS);
        assertThat(testAsignatura.getNum_grupos_practicas()).isEqualTo(DEFAULT_NUM_GRUPOS_PRACTICAS);
        assertThat(testAsignatura.getUsu_alta()).isEqualTo(DEFAULT_USU_ALTA);
    }

    @Test
    @Transactional
    public void createAsignaturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = asignaturaRepository.findAll().size();

        // Create the Asignatura with an existing ID
        asignatura.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setNombre(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setPlan(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitulacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setTitulacion(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditosIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setCreditos(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditos_teoricosIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setCreditos_teoricos(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditos_practicasIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setCreditos_practicas(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsu_altaIsRequired() throws Exception {
        int databaseSizeBeforeTest = asignaturaRepository.findAll().size();
        // set the field null
        asignatura.setUsu_alta(null);

        // Create the Asignatura, which fails.

        restAsignaturaMockMvc.perform(post("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isBadRequest());

        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAsignaturas() throws Exception {
        // Initialize the database
        asignaturaRepository.saveAndFlush(asignatura);

        // Get all the asignaturaList
        restAsignaturaMockMvc.perform(get("/api/asignaturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asignatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].plan").value(hasItem(DEFAULT_PLAN.toString())))
            .andExpect(jsonPath("$.[*].titulacion").value(hasItem(DEFAULT_TITULACION.toString())))
            .andExpect(jsonPath("$.[*].creditos").value(hasItem(DEFAULT_CREDITOS)))
            .andExpect(jsonPath("$.[*].num_grupos").value(hasItem(DEFAULT_NUM_GRUPOS)))
            .andExpect(jsonPath("$.[*].creditos_teoricos").value(hasItem(DEFAULT_CREDITOS_TEORICOS)))
            .andExpect(jsonPath("$.[*].creditos_practicas").value(hasItem(DEFAULT_CREDITOS_PRACTICAS)))
            .andExpect(jsonPath("$.[*].num_grupos_teoricos").value(hasItem(DEFAULT_NUM_GRUPOS_TEORICOS)))
            .andExpect(jsonPath("$.[*].num_grupos_practicas").value(hasItem(DEFAULT_NUM_GRUPOS_PRACTICAS)))
            .andExpect(jsonPath("$.[*].usu_alta").value(hasItem(DEFAULT_USU_ALTA.toString())));
    }

    @Test
    @Transactional
    public void getAsignatura() throws Exception {
        // Initialize the database
        asignaturaRepository.saveAndFlush(asignatura);

        // Get the asignatura
        restAsignaturaMockMvc.perform(get("/api/asignaturas/{id}", asignatura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asignatura.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.plan").value(DEFAULT_PLAN.toString()))
            .andExpect(jsonPath("$.titulacion").value(DEFAULT_TITULACION.toString()))
            .andExpect(jsonPath("$.creditos").value(DEFAULT_CREDITOS))
            .andExpect(jsonPath("$.num_grupos").value(DEFAULT_NUM_GRUPOS))
            .andExpect(jsonPath("$.creditos_teoricos").value(DEFAULT_CREDITOS_TEORICOS))
            .andExpect(jsonPath("$.creditos_practicas").value(DEFAULT_CREDITOS_PRACTICAS))
            .andExpect(jsonPath("$.num_grupos_teoricos").value(DEFAULT_NUM_GRUPOS_TEORICOS))
            .andExpect(jsonPath("$.num_grupos_practicas").value(DEFAULT_NUM_GRUPOS_PRACTICAS))
            .andExpect(jsonPath("$.usu_alta").value(DEFAULT_USU_ALTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAsignatura() throws Exception {
        // Get the asignatura
        restAsignaturaMockMvc.perform(get("/api/asignaturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsignatura() throws Exception {
        // Initialize the database
        asignaturaRepository.saveAndFlush(asignatura);
        int databaseSizeBeforeUpdate = asignaturaRepository.findAll().size();

        // Update the asignatura
        Asignatura updatedAsignatura = asignaturaRepository.findOne(asignatura.getId());
        updatedAsignatura
            .nombre(UPDATED_NOMBRE)
            .plan(UPDATED_PLAN)
            .titulacion(UPDATED_TITULACION)
            .creditos(UPDATED_CREDITOS)
            .num_grupos(UPDATED_NUM_GRUPOS)
            .creditos_teoricos(UPDATED_CREDITOS_TEORICOS)
            .creditos_practicas(UPDATED_CREDITOS_PRACTICAS)
            .num_grupos_teoricos(UPDATED_NUM_GRUPOS_TEORICOS)
            .num_grupos_practicas(UPDATED_NUM_GRUPOS_PRACTICAS)
            .usu_alta(UPDATED_USU_ALTA);

        restAsignaturaMockMvc.perform(put("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsignatura)))
            .andExpect(status().isOk());

        // Validate the Asignatura in the database
        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeUpdate);
        Asignatura testAsignatura = asignaturaList.get(asignaturaList.size() - 1);
        assertThat(testAsignatura.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAsignatura.getPlan()).isEqualTo(UPDATED_PLAN);
        assertThat(testAsignatura.getTitulacion()).isEqualTo(UPDATED_TITULACION);
        assertThat(testAsignatura.getCreditos()).isEqualTo(UPDATED_CREDITOS);
        assertThat(testAsignatura.getNum_grupos()).isEqualTo(UPDATED_NUM_GRUPOS);
        assertThat(testAsignatura.getCreditos_teoricos()).isEqualTo(UPDATED_CREDITOS_TEORICOS);
        assertThat(testAsignatura.getCreditos_practicas()).isEqualTo(UPDATED_CREDITOS_PRACTICAS);
        assertThat(testAsignatura.getNum_grupos_teoricos()).isEqualTo(UPDATED_NUM_GRUPOS_TEORICOS);
        assertThat(testAsignatura.getNum_grupos_practicas()).isEqualTo(UPDATED_NUM_GRUPOS_PRACTICAS);
        assertThat(testAsignatura.getUsu_alta()).isEqualTo(UPDATED_USU_ALTA);
    }

    @Test
    @Transactional
    public void updateNonExistingAsignatura() throws Exception {
        int databaseSizeBeforeUpdate = asignaturaRepository.findAll().size();

        // Create the Asignatura

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAsignaturaMockMvc.perform(put("/api/asignaturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asignatura)))
            .andExpect(status().isCreated());

        // Validate the Asignatura in the database
        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAsignatura() throws Exception {
        // Initialize the database
        asignaturaRepository.saveAndFlush(asignatura);
        int databaseSizeBeforeDelete = asignaturaRepository.findAll().size();

        // Get the asignatura
        restAsignaturaMockMvc.perform(delete("/api/asignaturas/{id}", asignatura.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asignatura> asignaturaList = asignaturaRepository.findAll();
        assertThat(asignaturaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asignatura.class);
    }
}
