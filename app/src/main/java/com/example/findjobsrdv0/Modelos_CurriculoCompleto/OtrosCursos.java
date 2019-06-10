package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

import java.util.HashMap;
import java.util.Map;

public class OtrosCursos {

   private String sIdOtroCurso, sIdCurriculosOtrosCursos, sIdBuscadorEmpleoOtrosCursos, sInstitucionOtrosCursos, sAnoOtrosCursos, sAreaoTemaOtrosCursos, sTipoEstudioOtrosCursos;

    public OtrosCursos() {
    }

    public OtrosCursos(String sIdOtroCurso, String sIdCurriculosOtrosCursos, String sIdBuscadorEmpleoOtrosCursos, String sInstitucionOtrosCursos, String sAnoOtrosCursos, String sAreaoTemaOtrosCursos, String sTipoEstudioOtrosCursos) {
        this.sIdOtroCurso = sIdOtroCurso;
        this.sIdCurriculosOtrosCursos = sIdCurriculosOtrosCursos;
        this.sIdBuscadorEmpleoOtrosCursos = sIdBuscadorEmpleoOtrosCursos;
        this.sInstitucionOtrosCursos = sInstitucionOtrosCursos;
        this.sAnoOtrosCursos = sAnoOtrosCursos;
        this.sAreaoTemaOtrosCursos = sAreaoTemaOtrosCursos;
        this.sTipoEstudioOtrosCursos = sTipoEstudioOtrosCursos;
    }

    public String getsIdOtroCurso() {
        return sIdOtroCurso;
    }

    public void setsIdOtroCurso(String sIdOtroCurso) {
        this.sIdOtroCurso = sIdOtroCurso;
    }

    public String getsIdCurriculosOtrosCursos() {
        return sIdCurriculosOtrosCursos;
    }

    public void setsIdCurriculosOtrosCursos(String sIdCurriculosOtrosCursos) {
        this.sIdCurriculosOtrosCursos = sIdCurriculosOtrosCursos;
    }

    public String getsIdBuscadorEmpleoOtrosCursos() {
        return sIdBuscadorEmpleoOtrosCursos;
    }

    public void setsIdBuscadorEmpleoOtrosCursos(String sIdBuscadorEmpleoOtrosCursos) {
        this.sIdBuscadorEmpleoOtrosCursos = sIdBuscadorEmpleoOtrosCursos;
    }

    public String getsInstitucionOtrosCursos() {
        return sInstitucionOtrosCursos;
    }

    public void setsInstitucionOtrosCursos(String sInstitucionOtrosCursos) {
        this.sInstitucionOtrosCursos = sInstitucionOtrosCursos;
    }

    public String getsAnoOtrosCursos() {
        return sAnoOtrosCursos;
    }

    public void setsAnoOtrosCursos(String sAnoOtrosCursos) {
        this.sAnoOtrosCursos = sAnoOtrosCursos;
    }

    public String getsAreaoTemaOtrosCursos() {
        return sAreaoTemaOtrosCursos;
    }

    public void setsAreaoTemaOtrosCursos(String sAreaoTemaOtrosCursos) {
        this.sAreaoTemaOtrosCursos = sAreaoTemaOtrosCursos;
    }

    public String getsTipoEstudioOtrosCursos() {
        return sTipoEstudioOtrosCursos;
    }

    public void setsTipoEstudioOtrosCursos(String sTipoEstudioOtrosCursos) {
        this.sTipoEstudioOtrosCursos = sTipoEstudioOtrosCursos;
    }

    public Map<String, Object> OtrosCursosMap(){

        HashMap<String,Object> ListOtrosCursos= new HashMap<>();
        ListOtrosCursos.put( "idcodigo", sIdOtroCurso );
        ListOtrosCursos.put( "idusuarioregistardo", sIdBuscadorEmpleoOtrosCursos );
        ListOtrosCursos.put("institucion", sInstitucionOtrosCursos );
        ListOtrosCursos.put("año", sAnoOtrosCursos );
        ListOtrosCursos.put("areaotema", sAreaoTemaOtrosCursos );
        ListOtrosCursos.put("tipodeestudio", sTipoEstudioOtrosCursos );


        return ListOtrosCursos;
    }

}
