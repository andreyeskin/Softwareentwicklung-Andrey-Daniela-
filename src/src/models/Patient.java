package models;

/**
 * Die Klasse Patient repräsentiert einen Patienten mit allen relevanten Daten.
 * Sie enthält Attribute wie Name, Geburtsdatum, Adresse, Geschlecht und weitere Informationen.
 */
public class Patient {
    private int id; // Primärschlüssel
    private String anrede; // Herr, Frau, etc.
    private String vorname;
    private String nachname;
    private String geburtstag; // Datum im Format YYYY-MM-DD
    private String svnr; // Sozialversicherungsnummer
    private String insuranceId; // Versicherungs-ID
    private String telefonnummer;
    private String strasse; // Adresse
    private String plz; // Postleitzahl
    private String ort; // Ort
    private int genderId; // Verweis auf Tabelle 'gender'
    private int bundeslandId; // Verweis auf Tabelle 'bundesland'

    /**
     * Standard-Konstruktor.
     */
    public Patient() {
        // Standard-Konstruktor
    }

    /**
     * Vollständiger Konstruktor für ein Patient-Objekt.
     * @param id Die ID des Patienten.
     * @param anrede Die Anrede des Patienten.
     * @param vorname Der Vorname des Patienten.
     * @param nachname Der Nachname des Patienten.
     * @param geburtstag Das Geburtsdatum des Patienten.
     * @param svnr Die Sozialversicherungsnummer des Patienten.
     * @param insuranceId Die Versicherungs-ID des Patienten.
     * @param telefonnummer Die Telefonnummer des Patienten.
     * @param strasse Die Adresse des Patienten.
     * @param plz Die Postleitzahl des Patienten.
     * @param ort Der Wohnort des Patienten.
     * @param genderId Die Geschlechts-ID des Patienten.
     * @param bundeslandId Die Bundesland-ID des Patienten.
     */
    public Patient(int id, String anrede, String vorname, String nachname, String geburtstag, String svnr,
                   String insuranceId, String telefonnummer, String strasse, String plz, String ort, int genderId, int bundeslandId) {
        this.id = id;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtstag = geburtstag;
        this.svnr = svnr;
        this.insuranceId = insuranceId;
        this.telefonnummer = telefonnummer;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.genderId = genderId;
        this.bundeslandId = bundeslandId;
    }

    /**
     * Konstruktor mit minimalen Daten (ID, Vorname, Nachname).
     * @param id Die ID des Patienten.
     * @param vorname Der Vorname des Patienten.
     * @param nachname Der Nachname des Patienten.
     */
    public Patient(int id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    /**
     * Konstruktor mit PatientID und Name.
     * @param patientID Die Patienten-ID.
     * @param name Der Name des Patienten.
     */
    public Patient(int patientID, String name) {
        // Konstruktor-Logik hier hinzufügen, falls erforderlich
    }

    /**
     * Getter- und Setter-Methoden für die Patientenattribute.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public int getBundeslandId() {
        return bundeslandId;
    }

    public void setBundeslandId(int bundeslandId) {
        this.bundeslandId = bundeslandId;
    }

    /**
     * Gibt eine String-Darstellung des Patienten zurück.
     * @return Der Vorname, Nachname und die ID des Patienten.
     */
    @Override
    public String toString() {
        return vorname + " " + nachname + " (ID: " + id + ")";
    }

    /**
     * Getter für die Patienten-ID (Alias für getId).
     * @return Die ID des Patienten.
     */
    public int getPatientId() {
        return id;
    }
}
