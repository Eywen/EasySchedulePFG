package com.pfg.easyschedule.web.rest.util;

import java.io.Serializable;

public class AsignaturaFrontDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String plan;
    private String titulacion;
    private Integer creditos;
    private Integer num_grupos;
    private Integer creditos_teoricos;
    private Integer creditos_practicas;
    private Integer num_grupos_teoricos;
    private Integer num_grupos_practicas;
    private String usu_alta;
    private String fecha_seleccion;

    public AsignaturaFrontDto (){

    }

    public AsignaturaFrontDto(Long id, String nombre, String plan, String titulacion,
                              Integer creditos, Integer num_grupos, Integer creditos_teoricos,
                              Integer creditos_practicas, Integer num_grupos_teoricos,
                              Integer num_grupos_practicas, String usu_alta, String fecha_seleccion) {
        this.id = id;
        this.nombre = nombre;
        this.plan = plan;
        this.titulacion = titulacion;
        this.creditos = creditos;
        this.num_grupos = num_grupos;
        this.creditos_teoricos = creditos_teoricos;
        this.creditos_practicas = creditos_practicas;
        this.num_grupos_teoricos = num_grupos_teoricos;
        this.num_grupos_practicas = num_grupos_practicas;
        this.usu_alta = usu_alta;
        this.fecha_seleccion = fecha_seleccion;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getNum_grupos() {
        return num_grupos;
    }

    public void setNum_grupos(Integer num_grupos) {
        this.num_grupos = num_grupos;
    }

    public Integer getCreditos_teoricos() {
        return creditos_teoricos;
    }

    public void setCreditos_teoricos(Integer creditos_teoricos) {
        this.creditos_teoricos = creditos_teoricos;
    }

    public Integer getCreditos_practicas() {
        return creditos_practicas;
    }

    public void setCreditos_practicas(Integer creditos_practicas) {
        this.creditos_practicas = creditos_practicas;
    }

    public Integer getNum_grupos_teoricos() {
        return num_grupos_teoricos;
    }

    public void setNum_grupos_teoricos(Integer num_grupos_teoricos) {
        this.num_grupos_teoricos = num_grupos_teoricos;
    }

    public Integer getNum_grupos_practicas() {
        return num_grupos_practicas;
    }

    public void setNum_grupos_practicas(Integer num_grupos_practicas) {
        this.num_grupos_practicas = num_grupos_practicas;
    }

    public String getUsu_alta() {
        return usu_alta;
    }

    public void setUsu_alta(String usu_alta) {
        this.usu_alta = usu_alta;
    }

    public String getFecha_seleccion() {
        return fecha_seleccion;
    }

    public void setFecha_seleccion(String fecha_seleccion) {
        this.fecha_seleccion = fecha_seleccion;
    }

    @Override
    public String toString() {
        return "AsignaturaFrontDto{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", plan='" + plan + '\'' +
            ", titulacion='" + titulacion + '\'' +
            ", creditos=" + creditos +
            ", num_grupos=" + num_grupos +
            ", creditos_teoricos=" + creditos_teoricos +
            ", creditos_practicas=" + creditos_practicas +
            ", num_grupos_teoricos=" + num_grupos_teoricos +
            ", num_grupos_practicas=" + num_grupos_practicas +
            ", usu_alta='" + usu_alta + '\'' +
            ", fecha_seleccion='" + fecha_seleccion + '\'' +
            '}';
    }
}
