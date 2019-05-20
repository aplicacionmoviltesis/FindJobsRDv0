package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleReferencias.modelo;

public class modelodetalleReferencia {

    String rBuscadorId, rCargoOcupadoC, rCodigoId,rInstitucionC, rNombreC, rTelefonoC;

    public modelodetalleReferencia() {
    }

    public modelodetalleReferencia(String rBuscadorId, String rCargoOcupadoC, String rCodigoId, String rInstitucionC, String rNombreC, String rTelefonoC) {
        this.rBuscadorId = rBuscadorId;
        this.rCargoOcupadoC = rCargoOcupadoC;
        this.rCodigoId = rCodigoId;
        this.rInstitucionC = rInstitucionC;
        this.rNombreC = rNombreC;
        this.rTelefonoC = rTelefonoC;
    }

    public String getrBuscadorId() {
        return rBuscadorId;
    }

    public void setrBuscadorId(String rBuscadorId) {
        this.rBuscadorId = rBuscadorId;
    }

    public String getrCargoOcupadoC() {
        return rCargoOcupadoC;
    }

    public void setrCargoOcupadoC(String rCargoOcupadoC) {
        this.rCargoOcupadoC = rCargoOcupadoC;
    }

    public String getrCodigoId() {
        return rCodigoId;
    }

    public void setrCodigoId(String rCodigoId) {
        this.rCodigoId = rCodigoId;
    }

    public String getrInstitucionC() {
        return rInstitucionC;
    }

    public void setrInstitucionC(String rInstitucionC) {
        this.rInstitucionC = rInstitucionC;
    }

    public String getrNombreC() {
        return rNombreC;
    }

    public void setrNombreC(String rNombreC) {
        this.rNombreC = rNombreC;
    }

    public String getrTelefonoC() {
        return rTelefonoC;
    }

    public void setrTelefonoC(String rTelefonoC) {
        this.rTelefonoC = rTelefonoC;
    }
}
