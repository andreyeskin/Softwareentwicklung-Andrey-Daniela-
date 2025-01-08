package models;

/**
 * Die Klasse PatientDiagnose repräsentiert die Zuordnung einer Diagnose zu einem Patienten.
 * Sie enthält Informationen wie die ID der Zuordnung, die Patient-ID, die Diagnose-ID,
 * Bemerkungen und das Datum der Diagnose.
 * Zusätzlich können optionale Felder wie Diagnose-Name und Beschreibung enthalten sein.
 */
public class PatientDiagnose {
    private int patientDiagnoseId; // Primärschlüssel der Zuordnung
    private int patientId; // ID des Patienten
    private int diagnoseId; // ID der Diagnose
    private String bemerkung; // Zusätzliche Bemerkungen
    private String datum; // Datum der Diagnose
    private String patientName; // Name des Patienten (optional)

    // Zusatzinformationen für Verknüpfungen (optional, falls benötigt)
    private String diagnoseName; // Name der Diagnose
    private String diagnoseBeschreibung; // Beschreibung der Diagnose

    /**
     * Standardkonstruktor für die Klasse PatientDiagnose.
     */
    public PatientDiagnose() {
    }

    /**
     * Konstruktor zur Initialisierung einer PatientDiagnose mit allen notwendigen Feldern.
     *
     * @param patientDiagnoseId Die eindeutige ID der PatientDiagnose-Zuordnung.
     * @param patientId         Die ID des Patienten.
     * @param diagnoseId        Die ID der Diagnose.
     * @param bemerkung         Zusätzliche Bemerkungen zur Diagnose.
     * @param datum             Das Datum der Diagnose.
     */
    public PatientDiagnose(int patientDiagnoseId, int patientId, int diagnoseId, String bemerkung, String datum) {
        this.patientDiagnoseId = patientDiagnoseId;
        this.patientId = patientId;
        this.diagnoseId = diagnoseId;
        this.bemerkung = bemerkung;
        this.datum = datum;
    }

    // Getter- und Setter-Methoden mit Javadoc-Kommentaren

    /**
     * Gibt den Namen des Patienten zurück.
     *
     * @return der Name des Patienten.
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Setzt den Namen des Patienten.
     *
     * @param patientName der neue Name des Patienten.
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Gibt die ID der PatientDiagnose-Zuordnung zurück.
     *
     * @return die PatientDiagnose-ID.
     */
    public int getPatientDiagnoseId() {
        return patientDiagnoseId;
    }

    /**
     * Setzt die ID der PatientDiagnose-Zuordnung.
     *
     * @param patientDiagnoseId die neue PatientDiagnose-ID.
     */
    public void setPatientDiagnoseId(int patientDiagnoseId) {
        this.patientDiagnoseId = patientDiagnoseId;
    }

    /**
     * Gibt die ID des Patienten zurück.
     *
     * @return die Patienten-ID.
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Setzt die ID des Patienten.
     *
     * @param patientId die neue Patienten-ID.
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
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
     * Gibt die Bemerkung zur Diagnose zurück.
     *
     * @return die Bemerkung.
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * Setzt die Bemerkung zur Diagnose.
     *
     * @param bemerkung die neue Bemerkung.
     */
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    /**
     * Gibt das Datum der Diagnose zurück.
     *
     * @return das Datum der Diagnose.
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Setzt das Datum der Diagnose.
     *
     * @param datum das neue Datum der Diagnose.
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * Gibt den Namen der Diagnose zurück.
     *
     * @return der Name der Diagnose.
     */
    public String getDiagnoseName() {
        return diagnoseName;
    }

    /**
     * Setzt den Namen der Diagnose.
     *
     * @param diagnoseName der neue Name der Diagnose.
     */
    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    /**
     * Gibt die Beschreibung der Diagnose zurück.
     *
     * @return die Beschreibung der Diagnose.
     */
    public String getDiagnoseBeschreibung() {
        return diagnoseBeschreibung;
    }

    /**
     * Setzt die Beschreibung der Diagnose.
     *
     * @param diagnoseBeschreibung die neue Beschreibung der Diagnose.
     */
    public void setDiagnoseBeschreibung(String diagnoseBeschreibung) {
        this.diagnoseBeschreibung = diagnoseBeschreibung;
    }

    /**
     * Überschreibt die Methode {@link Object#toString()}.
     * Gibt die Informationen der PatientDiagnose als String zurück.
     *
     * @return die String-Darstellung der PatientDiagnose.
     */
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
