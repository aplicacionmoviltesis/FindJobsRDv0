package com.example.findjobsrdv0;

public class AplicarEmpleo {

    private String sIdAplicarEmpleo, sIdCurriculoAplico,sIdEmpleoAplico,sIdPersonaAplico,sFechadeAplicacion;

    public AplicarEmpleo() {
    }

    public AplicarEmpleo(String sIdAplicarEmpleo, String sIdCurriculoAplico, String sIdEmpleoAplico, String sIdPersonaAplico, String sFechadeAplicacion) {
        this.sIdAplicarEmpleo = sIdAplicarEmpleo;
        this.sIdCurriculoAplico = sIdCurriculoAplico;
        this.sIdEmpleoAplico = sIdEmpleoAplico;
        this.sIdPersonaAplico = sIdPersonaAplico;
        this.sFechadeAplicacion = sFechadeAplicacion;
    }

    public String getsIdAplicarEmpleo() {
        return sIdAplicarEmpleo;
    }

    public String getsIdCurriculoAplico() {
        return sIdCurriculoAplico;
    }

    public String getsIdEmpleoAplico() {
        return sIdEmpleoAplico;
    }

    public String getsIdPersonaAplico() {
        return sIdPersonaAplico;
    }

    public String getsFechadeAplicacion() {
        return sFechadeAplicacion;
    }
}
