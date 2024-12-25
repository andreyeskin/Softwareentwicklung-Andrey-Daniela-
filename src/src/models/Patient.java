package models;

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

    // Konstruktoren
    public Patient() {
        // Standard-Konstruktor
    }

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

    public Patient(int id, String vorname, String nachname) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;


    }

    public Patient(int patientID, String name) {
    }


    // Getter- und Setter-Methoden
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

    // toString-Methode

    @Override
    public String toString() {
        return vorname + " " + nachname + " (ID: " + id + ")";
    }


    public int getPatientId() {
        return id;
    }
}