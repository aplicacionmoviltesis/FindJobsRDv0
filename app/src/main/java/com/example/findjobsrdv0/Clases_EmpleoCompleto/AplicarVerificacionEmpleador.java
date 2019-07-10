package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.net.Uri;

public class AplicarVerificacionEmpleador {
    private String sIdVerifEmp, sIdEmpleadorVerifEmp, sNombreDocumVerifEmp, sDocumentoVerifEmp, sFechaVerifEmp;

    public AplicarVerificacionEmpleador() {
    }

    public AplicarVerificacionEmpleador(String sIdVerifEmp, String sIdEmpleadorVerifEmp, String sNombreDocumVerifEmp, String sDocumentoVerifEmp, String sFechaVerifEmp) {
        this.sIdVerifEmp = sIdVerifEmp;
        this.sIdEmpleadorVerifEmp = sIdEmpleadorVerifEmp;
        this.sNombreDocumVerifEmp = sNombreDocumVerifEmp;
        this.sDocumentoVerifEmp = sDocumentoVerifEmp;
        this.sFechaVerifEmp = sFechaVerifEmp;
    }

    public String getsIdVerifEmp() {
        return sIdVerifEmp;
    }

    public void setsIdVerifEmp(String sIdVerifEmp) {
        this.sIdVerifEmp = sIdVerifEmp;
    }

    public String getsIdEmpleadorVerifEmp() {
        return sIdEmpleadorVerifEmp;
    }

    public void setsIdEmpleadorVerifEmp(String sIdEmpleadorVerifEmp) {
        this.sIdEmpleadorVerifEmp = sIdEmpleadorVerifEmp;
    }

    public String getsNombreDocumVerifEmp() {
        return sNombreDocumVerifEmp;
    }

    public void setsNombreDocumVerifEmp(String sNombreDocumVerifEmp) {
        this.sNombreDocumVerifEmp = sNombreDocumVerifEmp;
    }

    public String getsDocumentoVerifEmp() {
        return sDocumentoVerifEmp;
    }

    public void setsDocumentoVerifEmp(String sDocumentoVerifEmp) {
        this.sDocumentoVerifEmp = sDocumentoVerifEmp;
    }

    public String getsFechaVerifEmp() {
        return sFechaVerifEmp;
    }

    public void setsFechaVerifEmp(String sFechaVerifEmp) {
        this.sFechaVerifEmp = sFechaVerifEmp;
    }
}
