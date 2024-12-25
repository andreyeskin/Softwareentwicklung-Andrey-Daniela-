package models;

public class PatientDiagnose {
    private int patientDiagnoseId;
    private int patientId;
    private int diagnoseId;
    private String bemerkung;
    private String datum;
    private String patientName;

    // Zusatzinformationen für Verknüpfungen (optional, falls benötigt)
    private String diagnoseName;
    private String diagnoseBeschreibung;

    // Standardkonstruktor
    public PatientDiagnose() {
    }

    // Konstruktor mit allen Feldern
    public PatientDiagnose(int patientDiagnoseId, int patientId, int diagnoseId, String bemerkung, String datum) {
        this.patientDiagnoseId = patientDiagnoseId;
        this.patientId = patientId;
        this.diagnoseId = diagnoseId;
        this.bemerkung = bemerkung;
        this.datum = datum;
    }

    // Getter und Setter
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientDiagnoseId() {
        return patientDiagnoseId;
    }

    public void setPatientDiagnoseId(int patientDiagnoseId) {
        this.patientDiagnoseId = patientDiagnoseId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(int diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDiagnoseName() {
        return diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    public String getDiagnoseBeschreibung() {
        return diagnoseBeschreibung;
    }

    public void setDiagnoseBeschreibung(String diagnoseBeschreibung) {
        this.diagnoseBeschreibung = diagnoseBeschreibung;
    }

    // toString-Methode (nützlich für Debugging)
    @Override
    public String toString() {
        return "PatientDiagnose{" +
                "patientDiagnoseId=" + patientDiagnoseId +
                ", patientId=" + patientId +
                ", diagnoseId=" + diagnoseId +
                ", bemerkung='" + bemerkung + '\'' +
                ", datum='" + datum + '\'' +
                ", diagnoseName='" + diagnoseName + '\'' +
                ", diagnoseBeschreibung='" + diagnoseBeschreibung + '\'' +
                '}';
    }

}
