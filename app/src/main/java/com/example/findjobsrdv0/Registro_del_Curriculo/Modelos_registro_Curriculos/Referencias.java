package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class Referencias {

    String rCodigoId, rBuscadorId, idusuarioregistrado, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC;

    public Referencias() {
    }

    public Referencias(String rCodigoId, String rBuscadorId, String idusuarioregistrado, String rNombreC, String rCargoOcupadoC, String rInstitucionC, String rTelefonoC) {
        this.rCodigoId = rCodigoId;
        this.rBuscadorId = rBuscadorId;
        this.idusuarioregistrado = idusuarioregistrado;
        this.rNombreC = rNombreC;
        this.rCargoOcupadoC = rCargoOcupadoC;
        this.rInstitucionC = rInstitucionC;
        this.rTelefonoC = rTelefonoC;
    }

    public String getrCodigoId() {
        return rCodigoId;
    }

    public void setrCodigoId(String rCodigoId) {
        this.rCodigoId = rCodigoId;
    }

    public String getrBuscadorId() {
        return rBuscadorId;
    }

    public void setrBuscadorId(String rBuscadorId) {
        this.rBuscadorId = rBuscadorId;
    }

    public String getIdusuarioregistrado() {
        return idusuarioregistrado;
    }

    public void setIdusuarioregistrado(String idusuarioregistrado) {
        this.idusuarioregistrado = idusuarioregistrado;
    }

    public String getrNombreC() {
        return rNombreC;
    }

    public void setrNombreC(String rNombreC) {
        this.rNombreC = rNombreC;
    }

    public String getrCargoOcupadoC() {
        return rCargoOcupadoC;
    }

    public void setrCargoOcupadoC(String rCargoOcupadoC) {
        this.rCargoOcupadoC = rCargoOcupadoC;
    }

    public String getrInstitucionC() {
        return rInstitucionC;
    }

    public void setrInstitucionC(String rInstitucionC) {
        this.rInstitucionC = rInstitucionC;
    }

    public String getrTelefonoC() {
        return rTelefonoC;
    }

    public void setrTelefonoC(String rTelefonoC) {
        this.rTelefonoC = rTelefonoC;
    }

    public Map<String, Object> Referencia() {

        HashMap<String, Object> ListReferencia = new HashMap<>();
        ListReferencia.put( "idusuarioregistrado", idusuarioregistrado );
        ListReferencia.put( "Nombre", rNombreC );
        ListReferencia.put( "CargoOcupado", rCargoOcupadoC );
        ListReferencia.put( "Institucion", rInstitucionC );
        ListReferencia.put( "Telefono", rTelefonoC );

        return ListReferencia;
    }
}