package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class OtrosCursos {

    String ocidcodigo, idbuscador, idusuarioregistrado,ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio;

    public OtrosCursos() {
    }

    public OtrosCursos(String ocidcodigo, String idbuscador, String idusuarioregistrado, String ocInstitucionC, String ocAnoC, String ocAreaoTemaC, String ocTipoEstudio) {
        this.ocidcodigo = ocidcodigo;
        this.idbuscador = idbuscador;
        this.idusuarioregistrado = idusuarioregistrado;
        this.ocInstitucionC = ocInstitucionC;
        this.ocAnoC = ocAnoC;
        this.ocAreaoTemaC = ocAreaoTemaC;
        this.ocTipoEstudio = ocTipoEstudio;
    }

    public String getOcidcodigo() {
        return ocidcodigo;
    }

    public void setOcidcodigo(String ocidcodigo) {
        this.ocidcodigo = ocidcodigo;
    }

    public String getIdbuscador() {
        return idbuscador;
    }

    public void setIdbuscador(String idbuscador) {
        this.idbuscador = idbuscador;
    }

    public String getIdusuarioregistrado() {
        return idusuarioregistrado;
    }

    public void setIdusuarioregistrado(String idusuarioregistrado) {
        this.idusuarioregistrado = idusuarioregistrado;
    }

    public String getOcInstitucionC() {
        return ocInstitucionC;
    }

    public void setOcInstitucionC(String ocInstitucionC) {
        this.ocInstitucionC = ocInstitucionC;
    }

    public String getOcAnoC() {
        return ocAnoC;
    }

    public void setOcAnoC(String ocAnoC) {
        this.ocAnoC = ocAnoC;
    }

    public String getOcAreaoTemaC() {
        return ocAreaoTemaC;
    }

    public void setOcAreaoTemaC(String ocAreaoTemaC) {
        this.ocAreaoTemaC = ocAreaoTemaC;
    }

    public String getOcTipoEstudio() {
        return ocTipoEstudio;
    }

    public void setOcTipoEstudio(String ocTipoEstudio) {
        this.ocTipoEstudio = ocTipoEstudio;
    }

    public Map<String, Object> OtrosCursosMap(){

        HashMap<String,Object> ListOtrosCursos= new HashMap<>();
        ListOtrosCursos.put( "idcodigo",ocidcodigo );
        ListOtrosCursos.put( "idusuarioregistardo", idusuarioregistrado );
        ListOtrosCursos.put("institucion",ocInstitucionC);
        ListOtrosCursos.put("año",ocAnoC);
        ListOtrosCursos.put("areaotema",ocAreaoTemaC);
        ListOtrosCursos.put("tipodeestudio",ocTipoEstudio);


        return ListOtrosCursos;
    }

}
