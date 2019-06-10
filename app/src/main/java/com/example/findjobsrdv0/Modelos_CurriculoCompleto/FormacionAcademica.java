package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

import java.util.HashMap;
import java.util.Map;

public class FormacionAcademica {

   private String sIdFormacionAcademica, sIdCurriculoFormAcad, sIdBuscadorEmpleoFormAcad, sCarreraFormAcad, sNivelPrimarioFormAcad,
           sNivelSecundarioFormAcad, sNivelSuperiorFormAcad;

    public FormacionAcademica() {
    }

    public FormacionAcademica(String sIdFormacionAcademica, String sIdCurriculoFormAcad, String sIdBuscadorEmpleoFormAcad, String sCarreraFormAcad, String sNivelPrimarioFormAcad, String sNivelSecundarioFormAcad, String sNivelSuperiorFormAcad) {
        this.sIdFormacionAcademica = sIdFormacionAcademica;
        this.sIdCurriculoFormAcad = sIdCurriculoFormAcad;
        this.sIdBuscadorEmpleoFormAcad = sIdBuscadorEmpleoFormAcad;
        this.sCarreraFormAcad = sCarreraFormAcad;
        this.sNivelPrimarioFormAcad = sNivelPrimarioFormAcad;
        this.sNivelSecundarioFormAcad = sNivelSecundarioFormAcad;
        this.sNivelSuperiorFormAcad = sNivelSuperiorFormAcad;
    }

    public String getsIdFormacionAcademica() {
        return sIdFormacionAcademica;
    }

    public void setsIdFormacionAcademica(String sIdFormacionAcademica) {
        this.sIdFormacionAcademica = sIdFormacionAcademica;
    }

    public String getsIdCurriculoFormAcad() {
        return sIdCurriculoFormAcad;
    }

    public void setsIdCurriculoFormAcad(String sIdCurriculoFormAcad) {
        this.sIdCurriculoFormAcad = sIdCurriculoFormAcad;
    }

    public String getsIdBuscadorEmpleoFormAcad() {
        return sIdBuscadorEmpleoFormAcad;
    }

    public void setsIdBuscadorEmpleoFormAcad(String sIdBuscadorEmpleoFormAcad) {
        this.sIdBuscadorEmpleoFormAcad = sIdBuscadorEmpleoFormAcad;
    }

    public String getsCarreraFormAcad() {
        return sCarreraFormAcad;
    }

    public void setsCarreraFormAcad(String sCarreraFormAcad) {
        this.sCarreraFormAcad = sCarreraFormAcad;
    }

    public String getsNivelPrimarioFormAcad() {
        return sNivelPrimarioFormAcad;
    }

    public void setsNivelPrimarioFormAcad(String sNivelPrimarioFormAcad) {
        this.sNivelPrimarioFormAcad = sNivelPrimarioFormAcad;
    }

    public String getsNivelSecundarioFormAcad() {
        return sNivelSecundarioFormAcad;
    }

    public void setsNivelSecundarioFormAcad(String sNivelSecundarioFormAcad) {
        this.sNivelSecundarioFormAcad = sNivelSecundarioFormAcad;
    }

    public String getsNivelSuperiorFormAcad() {
        return sNivelSuperiorFormAcad;
    }

    public void setsNivelSuperiorFormAcad(String sNivelSuperiorFormAcad) {
        this.sNivelSuperiorFormAcad = sNivelSuperiorFormAcad;
    }

    public Map<String, Object> FormAcad(){

        HashMap<String,Object> ListFormAcad= new HashMap<>();
        ListFormAcad.put("sIdFormacionAcademica", sIdFormacionAcademica );
        ListFormAcad.put( "sIdCurriculoFormAcad", sIdCurriculoFormAcad );
        ListFormAcad.put( "sIdBuscadorEmpleoFormAcad", sIdBuscadorEmpleoFormAcad );
        ListFormAcad.put("sCarreraFormAcad", sCarreraFormAcad );
        ListFormAcad.put("sNivelPrimarioFormAcad", sNivelPrimarioFormAcad );
        ListFormAcad.put("sNivelSecundarioFormAcad", sNivelSecundarioFormAcad );
        ListFormAcad.put("sNivelSuperiorFormAcad", sNivelSuperiorFormAcad );

        return ListFormAcad;
    }
}
