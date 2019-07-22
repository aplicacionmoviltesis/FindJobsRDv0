package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

import java.util.HashMap;
import java.util.Map;

public class OtrosCursos {

   private String sIdOtroCurso, sIdCurriculosOtrosCursos, sInstitucionOtrosCursos, sAnoOtrosCursos, sAreaoTemaOtrosCursos, sTipoEstudioOtrosCursos;

    public OtrosCursos() {
    }

    public OtrosCursos(String sIdOtroCurso, String sIdCurriculosOtrosCursos, String sInstitucionOtrosCursos, String sAnoOtrosCursos, String sAreaoTemaOtrosCursos, String sTipoEstudioOtrosCursos) {
        this.sIdOtroCurso = sIdOtroCurso;
        this.sIdCurriculosOtrosCursos = sIdCurriculosOtrosCursos;
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
}
