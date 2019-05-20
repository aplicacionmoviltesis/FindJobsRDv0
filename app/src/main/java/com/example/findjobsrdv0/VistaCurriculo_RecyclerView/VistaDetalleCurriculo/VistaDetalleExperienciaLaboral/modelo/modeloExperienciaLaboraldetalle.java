package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.modelo;

public class modeloExperienciaLaboraldetalle {

    String elBuscadorId, elCargoOcupado, elCodigoId, elFechaEntrada, elFechaSalida, elNombreEmpresa, elTelefono;

    public modeloExperienciaLaboraldetalle() {
    }

    public modeloExperienciaLaboraldetalle(String elBuscadorId, String elCargoOcupado, String elCodigoId, String elFechaEntrada, String elFechaSalida, String elNombreEmpresa, String elTelefono) {
        this.elBuscadorId = elBuscadorId;
        this.elCargoOcupado = elCargoOcupado;
        this.elCodigoId = elCodigoId;
        this.elFechaEntrada = elFechaEntrada;
        this.elFechaSalida = elFechaSalida;
        this.elNombreEmpresa = elNombreEmpresa;
        this.elTelefono = elTelefono;
    }

    public String getElBuscadorId() {
        return elBuscadorId;
    }

    public void setElBuscadorId(String elBuscadorId) {
        this.elBuscadorId = elBuscadorId;
    }

    public String getElCargoOcupado() {
        return elCargoOcupado;
    }

    public void setElCargoOcupado(String elCargoOcupado) {
        this.elCargoOcupado = elCargoOcupado;
    }

    public String getElCodigoId() {
        return elCodigoId;
    }

    public void setElCodigoId(String elCodigoId) {
        this.elCodigoId = elCodigoId;
    }

    public String getElFechaEntrada() {
        return elFechaEntrada;
    }

    public void setElFechaEntrada(String elFechaEntrada) {
        this.elFechaEntrada = elFechaEntrada;
    }

    public String getElFechaSalida() {
        return elFechaSalida;
    }

    public void setElFechaSalida(String elFechaSalida) {
        this.elFechaSalida = elFechaSalida;
    }

    public String getElNombreEmpresa() {
        return elNombreEmpresa;
    }

    public void setElNombreEmpresa(String elNombreEmpresa) {
        this.elNombreEmpresa = elNombreEmpresa;
    }

    public String getElTelefono() {
        return elTelefono;
    }

    public void setElTelefono(String elTelefono) {
        this.elTelefono = elTelefono;
    }
}
