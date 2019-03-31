package com.pfg.easyschedule.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * A Profesor.
 */
@Entity
@Table(name = "profesor")
public class Profesor implements Serializable ,  Comparable<Profesor>{//Comparator<Profesor>

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @NotNull
    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    @NotNull
    //@Max(value = 3)
    @Column(name = "cod_profesor", nullable = false)
    private Integer codProfesor;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "num_creditos_impartir")
    private Integer numCreditosImpartir;

    @NotNull
    //@Max(value = 2)
    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;

    @NotNull
    @Column(name = "usu_alta", nullable = false)
    private String usuAlta;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @OneToMany(
        mappedBy = "profesor",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
        //orphanRemoval = true
    )
    @JsonIgnore
    private List<AsignaturaProfesor> asignaturaProfesors;


    public Profesor() {
    }

    public String getLogin() {
        return login;
    }

    public Profesor login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Profesor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public Profesor primerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public Profesor segundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Integer getCodProfesor() {
        return codProfesor;
    }

    public Profesor codProfesor(Integer codProfesor) {
        this.codProfesor = codProfesor;
        return this;
    }

    public void setCodProfesor(Integer codProfesor) {
        this.codProfesor = codProfesor;
    }

    public String getEmail() {
        return email;
    }

    public Profesor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public Profesor categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getNumCreditosImpartir() {
        return numCreditosImpartir;
    }

    public Profesor numCreditosImpartir(Integer numCreditosImpartir) {
        this.numCreditosImpartir = numCreditosImpartir;
        return this;
    }

    public void setNumCreditosImpartir(Integer numCreditosImpartir) {
        this.numCreditosImpartir = numCreditosImpartir;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public Profesor prioridad(Integer prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getUsuAlta() {
        return usuAlta;
    }

    public Profesor usuAlta(String usuAlta) {
        this.usuAlta = usuAlta;
        return this;
    }

    public void setUsuAlta(String usuAlta) {
        this.usuAlta = usuAlta;
    }


    public List<AsignaturaProfesor> getAsignaturaProfesors() {
        return asignaturaProfesors;
    }



    public void setAsignaturaProfesors(List<AsignaturaProfesor> asignaturaProfesors) {
        this.asignaturaProfesors = asignaturaProfesors;
    }

    /*public List<Asignatura> getAsignaturaProfesors() {
        return asignaturaProfesors;
    }

    public Profesor asignaturaProfesors(List<Asignatura> asignaturaProfesors) {
        this.asignaturaProfesors = asignaturaProfesors;
        return this;
    }

    public Profesor addAsignatura(Asignatura asignatura) {
        this.asignaturaProfesors.add(asignatura);
        asignatura.getProfesors().add(this);
        return this;
    }

    public Profesor removeAsignatura(Asignatura asignatura) {
        this.asignaturaProfesors.remove(asignatura);
        asignatura.getProfesors().remove(this);
        return this;
    }*/

    /*public void setAsignaturaProfesors(List<Asignatura> asignaturaProfesors) {
        this.asignaturaProfesors = asignaturaProfesors;
    }*/

    public void addAsignatura(Asignatura asignatura, long num_creditos_seleccion ) {
        AsignaturaProfesor asignaturaProfesor = new AsignaturaProfesor(this, asignatura, num_creditos_seleccion);
        asignaturaProfesors.add(asignaturaProfesor);
        asignatura.getProfesors().add(asignaturaProfesor);
    }

    public void removeAsignatura(Asignatura asignatura) {
        for (Iterator<AsignaturaProfesor> iterator = asignaturaProfesors.iterator();
             iterator.hasNext(); ) {
            AsignaturaProfesor asignaturaProfesor = iterator.next();

            if (asignaturaProfesor.getProfesor().equals(this) &&
                asignaturaProfesor.getAsignatura().equals(asignatura)) {
                iterator.remove();
                asignaturaProfesor.getAsignatura().getProfesors().remove(asignaturaProfesor);
                asignaturaProfesor.setProfesor(null);
                asignaturaProfesor.setAsignatura(null);
            }
        }
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profesor profesor = (Profesor) o;
        if (profesor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profesor.id);
    }*/





    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int compareTo(Profesor o) {
        if (prioridad < o.prioridad) {
            return -1;
        }
        if (prioridad > o.prioridad) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Profesor{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", primerApellido='" + primerApellido + "'" +
            ", segundoApellido='" + segundoApellido + "'" +
            ", codProfesor='" + codProfesor + "'" +
            ", email='" + email + "'" +
            ", categoria='" + categoria + "'" +
            ", numCreditosImpartir='" + numCreditosImpartir + "'" +
            ", prioridad='" + prioridad + "'" +
            ", login='" + login + "'" +
            ", usuAlta='" + usuAlta + "'" +
            '}';
    }

    /*public enum Order {PRIORIDAD, NOMBRE, ID}

    private Order sortingBy = PRIORIDAD;
    @Override
    public int compare(Profesor p1, Profesor p2) {


            switch(sortingBy) {
                case PRIORIDAD: return p1.getPrioridad().compareTo(p2.getPrioridad());
                case NOMBRE: return p1.getNombre().compareTo(p2.getNombre());
                case ID: return p1.getId().compareTo(p2.getId());
            }
            throw new RuntimeException("Practically unreachable code, can't be thrown");


       // return 0;
    }

    public void setSortingBy(Order sortBy) {
        this.sortingBy = sortingBy;
    }*/
}
