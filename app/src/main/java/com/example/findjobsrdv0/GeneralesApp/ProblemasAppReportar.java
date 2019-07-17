package com.example.findjobsrdv0.GeneralesApp;

public class ProblemasAppReportar {

    public ProblemasAppReportar() {
    }

    private String sIdProblemAppReport;
    private String sTituloProblem;
    private String sDecripcionProblem;
    private String sFechaProblem;
    private String sImagenProblem;
    private String sIdUserReportProblem;
    private String sEstadoReporte;







    public ProblemasAppReportar(String sIdProblemAppReport, String sTituloProblem, String sDecripcionProblem, String sFechaProblem, String sImagenProblem, String sIdUserReportProblem, String sEstadoReporte) {
        this.sIdProblemAppReport = sIdProblemAppReport;
        this.sTituloProblem = sTituloProblem;
        this.sDecripcionProblem = sDecripcionProblem;
        this.sFechaProblem = sFechaProblem;
        this.sImagenProblem = sImagenProblem;
        this.sIdUserReportProblem = sIdUserReportProblem;
        this.sEstadoReporte = sEstadoReporte;
    }

    public String getsIdProblemAppReport() {
        return sIdProblemAppReport;
    }

    public void setsIdProblemAppReport(String sIdProblemAppReport) {
        this.sIdProblemAppReport = sIdProblemAppReport;
    }

    public String getsTituloProblem() {
        return sTituloProblem;
    }

    public void setsTituloProblem(String sTituloProblem) {
        this.sTituloProblem = sTituloProblem;
    }

    public String getsDecripcionProblem() {
        return sDecripcionProblem;
    }

    public void setsDecripcionProblem(String sDecripcionProblem) {
        this.sDecripcionProblem = sDecripcionProblem;
    }

    public String getsFechaProblem() {
        return sFechaProblem;
    }

    public void setsFechaProblem(String sFechaProblem) {
        this.sFechaProblem = sFechaProblem;
    }

    public String getsImagenProblem() {
        return sImagenProblem;
    }

    public void setsImagenProblem(String sImagenProblem) {
        this.sImagenProblem = sImagenProblem;
    }

    public String getsIdUserReportProblem() {
        return sIdUserReportProblem;
    }

    public void setsIdUserReportProblem(String sIdUserReportProblem) {
        this.sIdUserReportProblem = sIdUserReportProblem;
    }
    public String getsEstadoReporte() {
        return sEstadoReporte;
    }

    public void setsEstadoReporte(String sEstadoReporte) {
        this.sEstadoReporte = sEstadoReporte;
    }


}
