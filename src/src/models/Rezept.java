package models;

/**
 * Die Klasse Rezept repräsentiert ein medizinisches Rezept.
 * Sie enthält Informationen wie Rezept-ID, Patienten-ID, Medikamenten-ID, Dosierung, Start- und Enddatum sowie Bemerkungen.
 */
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

    /**
     * Konstruktor für Datenbankoperationen, um ein Rezept mit IDs zu initialisieren.
     *
     * @param rezeptId     Die eindeutige ID des Rezepts.
     * @param patientId    Die ID des Patienten.
     * @param medikamentId Die ID des Medikaments.
     * @param dosierung    Die Dosierung des Medikaments.
     * @param startdatum   Das Startdatum des Rezepts.
     * @param enddatum     Das Enddatum des Rezepts.
     * @param bemerkung    Zusätzliche Bemerkung zum Rezept.
     */
    public Rezept(int rezeptId, int patientId, int medikamentId, String dosierung, String startdatum, String enddatum, String bemerkung) {
        this.rezeptId = rezeptId;
        this.patientId = patientId;
        this.medikamentId = medikamentId;
        this.dosierung = dosierung;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.bemerkung = bemerkung;
    }

    /**
     * Konstruktor für Anzeigezwecke, um ein Rezept mit Patienten- und Medikamentennamen zu initialisieren.
     *
     * @param rezeptId       Die eindeutige ID des Rezepts.
     * @param patientName    Der Name des Patienten.
     * @param medikamentName Der Name des Medikaments.
     * @param dosierung      Die Dosierung des Medikaments.
     * @param startdatum     Das Startdatum des Rezepts.
     * @param enddatum       Das Enddatum des Rezepts.
     * @param bemerkung      Zusätzliche Bemerkung zum Rezept.
     */
    public Rezept(int rezeptId, String patientName, String medikamentName, String dosierung, String startdatum, String enddatum, String bemerkung) {
        this.rezeptId = rezeptId;
        this.patientName = patientName;
        this.medikamentName = medikamentName;
        this.dosierung = dosierung;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.bemerkung = bemerkung;
    }

    // Getter- und Setter-Methoden mit Javadoc-Kommentaren

    /**
     * Gibt die ID des Rezepts zurück.
     *
     * @return die Rezept-ID.
     */
    public int getRezeptId() {
        return rezeptId;
    }

    /**
     * Setzt die ID des Rezepts.
     *
     * @param rezeptId die neue Rezept-ID.
     */
    public void setRezeptId(int rezeptId) {
        this.rezeptId = rezeptId;
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
     * Gibt die ID des Medikaments zurück.
     *
     * @return die Medikamenten-ID.
     */
    public int getMedikamentId() {
        return medikamentId;
    }

    /**
     * Setzt die ID des Medikaments.
     *
     * @param medikamentId die neue Medikamenten-ID.
     */
    public void setMedikamentId(int medikamentId) {
        this.medikamentId = medikamentId;
    }

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
     * Gibt den Namen des Medikaments zurück.
     *
     * @return der Name des Medikaments.
     */
    public String getMedikamentName() {
        return medikamentName;
    }

    /**
     * Setzt den Namen des Medikaments.
     *
     * @param medikamentName der neue Name des Medikaments.
     */
    public void setMedikamentName(String medikamentName) {
        this.medikamentName = medikamentName;
    }

    /**
     * Gibt die Dosierung des Medikaments zurück.
     *
     * @return die Dosierung.
     */
    public String getDosierung() {
        return dosierung;
    }

    /**
     * Setzt die Dosierung des Medikaments.
     *
     * @param dosierung die neue Dosierung.
     */
    public void setDosierung(String dosierung) {
        this.dosierung = dosierung;
    }

    /**
     * Gibt das Startdatum des Rezepts zurück.
     *
     * @return das Startdatum.
     */
    public String getStartdatum() {
        return startdatum;
    }

    /**
     * Setzt das Startdatum des Rezepts.
     *
     * @param startdatum das neue Startdatum.
     */
    public void setStartdatum(String startdatum) {
        this.startdatum = startdatum;
    }

    /**
     * Gibt das Enddatum des Rezepts zurück.
     *
     * @return das Enddatum.
     */
    public String getEnddatum() {
        return enddatum;
    }

    /**
     * Setzt das Enddatum des Rezepts.
     *
     * @param enddatum das neue Enddatum.
     */
    public void setEnddatum(String enddatum) {
        this.enddatum = enddatum;
    }

    /**
     * Gibt die Bemerkung des Rezepts zurück.
     *
     * @return die Bemerkung.
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * Setzt die Bemerkung des Rezepts.
     *
     * @param bemerkung die neue Bemerkung.
     */
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    /**
     * Überschreibt die Methode {@link Object#toString()}, um eine lesbare Darstellung des Rezepts zu liefern.
     *
     * @return die String-Darstellung des Rezepts.
     */

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
