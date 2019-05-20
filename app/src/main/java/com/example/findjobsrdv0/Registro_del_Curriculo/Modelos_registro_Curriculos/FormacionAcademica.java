package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class FormacionAcademica {
    String codigoc, idbuscadorc, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;

    public FormacionAcademica() {
    }

    public FormacionAcademica(String codigoc, String idbuscadorc, String carrerac, String nivelprimarioc, String nivelsecundarioc, String nivelsuperiorc) {
        this.codigoc = codigoc;
        this.idbuscadorc = idbuscadorc;
        this.carrerac = carrerac;
        this.nivelprimarioc = nivelprimarioc;
        this.nivelsecundarioc = nivelsecundarioc;
        this.nivelsuperiorc = nivelsuperiorc;
    }

    public String getCodigoc() {
        return codigoc;
    }

    public void setCodigoc(String codigoc) {
        this.codigoc = codigoc;
    }

    public String getIdbuscadorc() {
        return idbuscadorc;
    }

    public void setIdbuscadorc(String idbuscadorc) {
        this.idbuscadorc = idbuscadorc;
    }

    public String getCarrerac() {
        return carrerac;
    }

    public void setCarrerac(String carrerac) {
        this.carrerac = carrerac;
    }

    public String getNivelprimarioc() {
        return nivelprimarioc;
    }

    public void setNivelprimarioc(String nivelprimarioc) {
        this.nivelprimarioc = nivelprimarioc;
    }

    public String getNivelsecundarioc() {
        return nivelsecundarioc;
    }

    public void setNivelsecundarioc(String nivelsecundarioc) {
        this.nivelsecundarioc = nivelsecundarioc;
    }

    public String getNivelsuperiorc() {
        return nivelsuperiorc;
    }

    public void setNivelsuperiorc(String nivelsuperiorc) {
        this.nivelsuperiorc = nivelsuperiorc;
    }

    public Map<String, Object> FormAcad(){

        HashMap<String,Object> ListFormAcad= new HashMap<>();
        ListFormAcad.put("codigoc",codigoc);
        ListFormAcad.put( "idbuscadorc", idbuscadorc );
        ListFormAcad.put("carrerac",carrerac);
        ListFormAcad.put("nivelprimarioc",nivelprimarioc);
        ListFormAcad.put("nivelsecundarioc",nivelsecundarioc);
        ListFormAcad.put("nivelsuperiorc",nivelsuperiorc);

        return ListFormAcad;
    }
}
