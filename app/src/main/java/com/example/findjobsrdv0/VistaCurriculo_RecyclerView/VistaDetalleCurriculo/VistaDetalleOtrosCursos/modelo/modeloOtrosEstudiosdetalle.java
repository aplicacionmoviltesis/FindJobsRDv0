package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleOtrosCursos.modelo;

public class modeloOtrosEstudiosdetalle {

   String ocAnoC, ocAreaoTemaC, ocInstitucionC, ocTipoEstudio;

    public modeloOtrosEstudiosdetalle() {
    }

    public modeloOtrosEstudiosdetalle(String ocAnoC, String ocAreaoTemaC, String ocInstitucionC, String ocTipoEstudio) {
        this.ocAnoC = ocAnoC;
        this.ocAreaoTemaC = ocAreaoTemaC;
        this.ocInstitucionC = ocInstitucionC;
        this.ocTipoEstudio = ocTipoEstudio;
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

    public String getOcInstitucionC() {
        return ocInstitucionC;
    }

    public void setOcInstitucionC(String ocInstitucionC) {
        this.ocInstitucionC = ocInstitucionC;
    }

    public String getOcTipoEstudio() {
        return ocTipoEstudio;
    }

    public void setOcTipoEstudio(String ocTipoEstudio) {
        this.ocTipoEstudio = ocTipoEstudio;
    }
}
