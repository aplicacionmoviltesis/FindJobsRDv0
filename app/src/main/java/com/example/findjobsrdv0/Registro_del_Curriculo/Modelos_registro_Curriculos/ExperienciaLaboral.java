package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class ExperienciaLaboral {
    String   elCodigoId, elBuscadorId ,elNombreEmpresa, elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida;

    public ExperienciaLaboral() {
    }

    public ExperienciaLaboral(String elCodigoId, String elBuscadorId, String elNombreEmpresa, String elCargoOcupado, String elTelefono, String elFechaEntrada, String elFechaSalida) {
        this.elCodigoId = elCodigoId;
        this.elBuscadorId = elBuscadorId;
        this.elNombreEmpresa = elNombreEmpresa;
        this.elCargoOcupado = elCargoOcupado;
        this.elTelefono = elTelefono;
        this.elFechaEntrada = elFechaEntrada;
        this.elFechaSalida = elFechaSalida;
    }

    public String getElCodigoId() {
        return elCodigoId;
    }

    public void setElCodigoId(String elCodigoId) {
        this.elCodigoId = elCodigoId;
    }

    public String getElBuscadorId() {
        return elBuscadorId;
    }

    public void setElBuscadorId(String elBuscadorId) {
        this.elBuscadorId = elBuscadorId;
    }

    public String getElNombreEmpresa() {
        return elNombreEmpresa;
    }

    public void setElNombreEmpresa(String elNombreEmpresa) {
        this.elNombreEmpresa = elNombreEmpresa;
    }

    public String getElCargoOcupado() {
        return elCargoOcupado;
    }

    public void setElCargoOcupado(String elCargoOcupado) {
        this.elCargoOcupado = elCargoOcupado;
    }

    public String getElTelefono() {
        return elTelefono;
    }

    public void setElTelefono(String elTelefono) {
        this.elTelefono = elTelefono;
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

    public Map<String, Object> ExpLab(){

        HashMap<String,Object> ListExpLab= new HashMap<>();
        ListExpLab.put("Nombre_Empresa",elNombreEmpresa);
        ListExpLab.put( "Cargo_Ocupado", elCargoOcupado );
        ListExpLab.put("Telefono",elTelefono);
        ListExpLab.put("Fecha_Entrada",elFechaEntrada);
        ListExpLab.put("Fecha_Salida",elFechaSalida);


        return ListExpLab;
    }
}
