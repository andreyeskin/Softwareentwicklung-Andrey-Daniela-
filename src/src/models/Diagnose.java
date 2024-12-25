package models;

public class Diagnose {
    private int diagnoseId;
    private String icd10Code;
    private String name;
    private String beschreibung;

    // Standardkonstruktor
    public Diagnose() {
    }

    // Konstruktor mit Parametern
    public Diagnose(int diagnoseId, String icd10Code, String name, String beschreibung) {
        this.diagnoseId = diagnoseId;
        this.icd10Code = icd10Code;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    // Getter und Setter
    public int getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(int diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}