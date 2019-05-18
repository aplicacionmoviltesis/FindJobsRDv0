package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class FormacionAcademica {
    String faCodigoC, faIdBuscadorC, afCarreraC, afNivelprimarioC, afNivelsecundarioC, afNivelsuperiorC;

    public FormacionAcademica() {
    }

    public FormacionAcademica(String faCodigoC, String faIdBuscadorC, String afCarreraC, String afNivelprimarioC, String afNivelsecundarioC, String afNivelsuperiorC) {
        this.faCodigoC = faCodigoC;
        this.faIdBuscadorC = faIdBuscadorC;
        this.afCarreraC = afCarreraC;
        this.afNivelprimarioC = afNivelprimarioC;
        this.afNivelsecundarioC = afNivelsecundarioC;
        this.afNivelsuperiorC = afNivelsuperiorC;
    }

    public String getFaCodigoC() {
        return faCodigoC;
    }

    public void setFaCodigoC(String faCodigoC) {
        this.faCodigoC = faCodigoC;
    }

    public String getFaIdBuscadorC() {
        return faIdBuscadorC;
    }

    public void setFaIdBuscadorC(String faIdBuscadorC) {
        this.faIdBuscadorC = faIdBuscadorC;
    }

    public String getAfCarreraC() {
        return afCarreraC;
    }

    public void setAfCarreraC(String afCarreraC) {
        this.afCarreraC = afCarreraC;
    }

    public String getAfNivelprimarioC() {
        return afNivelprimarioC;
    }

    public void setAfNivelprimarioC(String afNivelprimarioC) {
        this.afNivelprimarioC = afNivelprimarioC;
    }

    public String getAfNivelsecundarioC() {
        return afNivelsecundarioC;
    }

    public void setAfNivelsecundarioC(String afNivelsecundarioC) {
        this.afNivelsecundarioC = afNivelsecundarioC;
    }

    public String getAfNivelsuperiorC() {
        return afNivelsuperiorC;
    }

    public void setAfNivelsuperiorC(String afNivelsuperiorC) {
        this.afNivelsuperiorC = afNivelsuperiorC;
    }

    public Map<String, Object> FormAcad(){

        HashMap<String,Object> ListFormAcad= new HashMap<>();
        ListFormAcad.put("faCodigoC",faCodigoC);
        ListFormAcad.put( "faIdBuscadorC", faIdBuscadorC );
        ListFormAcad.put("afCarreraC",afCarreraC);
        ListFormAcad.put("afNivelprimarioC",afNivelprimarioC);
        ListFormAcad.put("afNivelsecundarioC",afNivelsecundarioC);
        ListFormAcad.put("afNivelsuperiorC",afNivelsuperiorC);

        return ListFormAcad;
    }
}
