package com.example.findjobsrdv0.Adaptadores_Empleador;

public class AplicarCurriculo {

    private String sIdAplicarCurriculo, sIdCurriculoAplico, sIdEmpresaAplico, sFechadeAplicacion;

    public AplicarCurriculo() {
    }

    public AplicarCurriculo(String sIdAplicarCurriculo, String sIdCurriculoAplico, String sIdEmpresaAplico, String sFechadeAplicacion) {
        this.sIdAplicarCurriculo = sIdAplicarCurriculo;
        this.sIdCurriculoAplico = sIdCurriculoAplico;
        this.sIdEmpresaAplico = sIdEmpresaAplico;
        this.sFechadeAplicacion = sFechadeAplicacion;
    }

    public String getsIdAplicarCurriculo() {
        return sIdAplicarCurriculo;
    }

    public void setsIdAplicarCurriculo(String sIdAplicarCurriculo) {
        this.sIdAplicarCurriculo = sIdAplicarCurriculo;
    }

    public String getsIdCurriculoAplico() {
        return sIdCurriculoAplico;
    }

    public void setsIdCurriculoAplico(String sIdCurriculoAplico) {
        this.sIdCurriculoAplico = sIdCurriculoAplico;
    }

    public String getsIdEmpresaAplico() {
        return sIdEmpresaAplico;
    }

    public void setsIdEmpresaAplico(String sIdEmpresaAplico) {
        this.sIdEmpresaAplico = sIdEmpresaAplico;
    }

    public String getsFechadeAplicacion() {
        return sFechadeAplicacion;
    }

    public void setsFechadeAplicacion(String sFechadeAplicacion) {
        this.sFechadeAplicacion = sFechadeAplicacion;
    }
}
