package com.pfg.easyschedule.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Asignatura.
 */
@Entity
@Table(name = "asignatura")
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "plan", nullable = false)
    private String plan;

    @NotNull
    @Column(name = "titulacion", nullable = false)
    private String titulacion;

    @NotNull
    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Column(name = "num_grupos")
    private Integer num_grupos;

    @NotNull
    @Column(name = "creditos_teoricos", nullable = false)
    private Integer creditos_teoricos;

    @NotNull
    @Column(name = "creditos_practicas", nullable = false)
    private Integer creditos_practicas;

    @Column(name = "num_grupos_teoricos")
    private Integer num_grupos_teoricos;

    @Column(name = "num_grupos_practicas")
    private Integer num_grupos_practicas;

    @NotNull
    @Column(name = "usu_alta", nullable = false)
    private String usu_alta;

    @ManyToMany
    @JoinTable(name = "asignatura_profesor",
               joinColumns = @JoinColumn(name="asignaturas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="profesors_id", referencedColumnName="id"))
    private Set<Profesor> profesors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Asignatura nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlan() {
        return plan;
    }

    public Asignatura plan(String plan) {
        this.plan = plan;
        return this;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public Asignatura titulacion(String titulacion) {
        this.titulacion = titulacion;
        return this;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public Asignatura creditos(Integer creditos) {
        this.creditos = creditos;
        return this;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getNum_grupos() {
        return num_grupos;
    }

    public Asignatura num_grupos(Integer num_grupos) {
        this.num_grupos = num_grupos;
        return this;
    }

    public void setNum_grupos(Integer num_grupos) {
        this.num_grupos = num_grupos;
    }

    public Integer getCreditos_teoricos() {
        return creditos_teoricos;
    }

    public Asignatura creditos_teoricos(Integer creditos_teoricos) {
        this.creditos_teoricos = creditos_teoricos;
        return this;
    }

    public void setCreditos_teoricos(Integer creditos_teoricos) {
        this.creditos_teoricos = creditos_teoricos;
    }

    public Integer getCreditos_practicas() {
        return creditos_practicas;
    }

    public Asignatura creditos_practicas(Integer creditos_practicas) {
        this.creditos_practicas = creditos_practicas;
        return this;
    }

    public void setCreditos_practicas(Integer creditos_practicas) {
        this.creditos_practicas = creditos_practicas;
    }

    public Integer getNum_grupos_teoricos() {
        return num_grupos_teoricos;
    }

    public Asignatura num_grupos_teoricos(Integer num_grupos_teoricos) {
        this.num_grupos_teoricos = num_grupos_teoricos;
        return this;
    }

    public void setNum_grupos_teoricos(Integer num_grupos_teoricos) {
        this.num_grupos_teoricos = num_grupos_teoricos;
    }

    public Integer getNum_grupos_practicas() {
        return num_grupos_practicas;
    }

    public Asignatura num_grupos_practicas(Integer num_grupos_practicas) {
        this.num_grupos_practicas = num_grupos_practicas;
        return this;
    }

    public void setNum_grupos_practicas(Integer num_grupos_practicas) {
        this.num_grupos_practicas = num_grupos_practicas;
    }

    public String getUsu_alta() {
        return usu_alta;
    }

    public Asignatura usu_alta(String usu_alta) {
        this.usu_alta = usu_alta;
        return this;
    }

    public void setUsu_alta(String usu_alta) {
        this.usu_alta = usu_alta;
    }

    public Set<Profesor> getProfesors() {
        return profesors;
    }

    public Asignatura profesors(Set<Profesor> profesors) {
        this.profesors = profesors;
        return this;
    }

    public Asignatura addProfesor(Profesor profesor) {
        this.profesors.add(profesor);
        profesor.getAsignaturas().add(this);
        return this;
    }

    public Asignatura removeProfesor(Profesor profesor) {
        this.profesors.remove(profesor);
        profesor.getAsignaturas().remove(this);
        return this;
    }

    public void setProfesors(Set<Profesor> profesors) {
        this.profesors = profesors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asignatura asignatura = (Asignatura) o;
        if (asignatura.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, asignatura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Asignatura{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", plan='" + plan + "'" +
            ", titulacion='" + titulacion + "'" +
            ", creditos='" + creditos + "'" +
            ", num_grupos='" + num_grupos + "'" +
            ", creditos_teoricos='" + creditos_teoricos + "'" +
            ", creditos_practicas='" + creditos_practicas + "'" +
            ", num_grupos_teoricos='" + num_grupos_teoricos + "'" +
            ", num_grupos_practicas='" + num_grupos_practicas + "'" +
            ", usu_alta='" + usu_alta + "'" +
            '}';
    }
}
