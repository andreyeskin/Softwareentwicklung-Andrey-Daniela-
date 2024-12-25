package models;

public class Rezept {
    private int rezeptId; // ID des Rezepts
    private int patientId; // ID des Patienten
    private int medikamentId; // ID des Medikaments
    private String patientName; // Name des Patienten (für Anzeigezwecke)
    private String medikamentName; // Name des Medikaments (für Anzeigezwecke)
    private String dosierung; // Dosierung des Medikaments
    private String startdatum; // Startdatum des Rezepts
    private String enddatum; // Enddatum des Rezepts
    private String bemerkung; // Zusätzliche Bemerkung

    // Konstruktor für Datenbankoperationen
    public Rezept(int rezeptId, int patientId, int medikamentId, String dosierung, String startdatum, String enddatum, String bemerkung) {
        this.rezeptId = rezeptId;
        this.patientId = patientId;
        this.medikamentId = medikamentId;
        this.dosierung = dosierung;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.bemerkung = bemerkung;
    }

    // Konstruktor für Anzeigezwecke
    public Rezept(int rezeptId, String patientName, String medikamentName, String dosierung, String startdatum, String enddatum, String bemerkung) {
        this.rezeptId = rezeptId;
        this.patientName = patientName;
        this.medikamentName = medikamentName;
        this.dosierung = dosierung;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.bemerkung = bemerkung;
    }

    // Getter und Setter
    public int getRezeptId() {
        return rezeptId;
    }

    public void setRezeptId(int rezeptId) {
        this.rezeptId = rezeptId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedikamentId() {
        return medikamentId;
    }

    public void setMedikamentId(int medikamentId) {
        this.medikamentId = medikamentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedikamentName() {
        return medikamentName;
    }

    public void setMedikamentName(String medikamentName) {
        this.medikamentName = medikamentName;
    }

    public String getDosierung() {
        return dosierung;
    }

    public void setDosierung(String dosierung) {
        this.dosierung = dosierung;
    }

    public String getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(String startdatum) {
        this.startdatum = startdatum;
    }

    public String getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(String enddatum) {
        this.enddatum = enddatum;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    @Override
    public String toString() {
        return "RezeptID: " + rezeptId +
                ", Patient: " + patientName +
                ", Medikament: " + medikamentName +
                ", Dosierung: " + dosierung +
                ", Startdatum: " + startdatum +
                ", Enddatum: " + enddatum +
                ", Bemerkung: " + bemerkung;
    }
}