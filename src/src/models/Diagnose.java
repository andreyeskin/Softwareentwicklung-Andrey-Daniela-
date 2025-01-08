package models;

/**
 * Die Klasse Diagnose repräsentiert eine medizinische Diagnose.
 * Sie enthält Informationen wie die Diagnose-ID, den ICD-10-Code, den Namen und die Beschreibung der Diagnose.
 */
public class Diagnose {
    private int diagnoseId;
    private String icd10Code;
    private String name;
    private String beschreibung;

    /**
     * Standardkonstruktor für die Klasse Diagnose.
     */
    public Diagnose() {
    }

    /**
     * Konstruktor mit Parametern für die Klasse Diagnose.
     *
     * @param diagnoseId   Die eindeutige ID der Diagnose.
     * @param icd10Code    Der ICD-10-Code der Diagnose.
     * @param name         Der Name der Diagnose.
     * @param beschreibung Die Beschreibung der Diagnose.
     */
    public Diagnose(int diagnoseId, String icd10Code, String name, String beschreibung) {
        this.diagnoseId = diagnoseId;
        this.icd10Code = icd10Code;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt die ID der Diagnose zurück.
     *
     * @return die Diagnose-ID.
     */
    public int getDiagnoseId() {
        return diagnoseId;
    }

    /**
     * Setzt die ID der Diagnose.
     *
     * @param diagnoseId die neue Diagnose-ID.
     */
    public void setDiagnoseId(int diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    /**
     * Gibt den ICD-10-Code der Diagnose zurück.
     *
     * @return der ICD-10-Code.
     */
    public String getIcd10Code() {
        return icd10Code;
    }

    /**
     * Setzt den ICD-10-Code der Diagnose.
     *
     * @param icd10Code der neue ICD-10-Code.
     */
    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    /**
     * Gibt den Namen der Diagnose zurück.
     *
     * @return der Name der Diagnose.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen der Diagnose.
     *
     * @param name der neue Name der Diagnose.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung der Diagnose zurück.
     *
     * @return die Beschreibung der Diagnose.
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setzt die Beschreibung der Diagnose.
     *
     * @param beschreibung die neue Beschreibung der Diagnose.
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
