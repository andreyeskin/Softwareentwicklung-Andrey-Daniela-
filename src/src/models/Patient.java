package models;

import java.time.LocalDate;

/**
 * Die Klasse Patient repräsentiert einen Patienten mit grundlegenden Attributen.
 */
public class Patient {
    private int id;
    private String anrede;
    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;
    private String svnr;
    private String versicherung;
    private String telefonnummer;
    private String strasse;
    private String plz;
    private String ort;
    private String bundesland;

    // Konstruktor
    public Patient(int id, String anrede, String vorname, String nachname, LocalDate geburtsdatum, String svnr,
                   String versicherung, String telefonnummer, String strasse, String plz, String ort, String bundesland) {
        this.id = id;
        this.anrede = anrede;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.svnr = svnr;
        this.versicherung = versicherung;
        this.telefonnummer = telefonnummer;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.bundesland = bundesland;
    }

    // Getter und Setter
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

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getSvnr() {
        return svnr;
    }

    public void setSvnr(String svnr) {
        this.svnr = svnr;
    }

    public String getVersicherung() {
        return versicherung;
    }

    public void setVersicherung(String versicherung) {
        this.versicherung = versicherung;
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

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", anrede='" + anrede + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                ", svnr='" + svnr + '\'' +
                ", versicherung='" + versicherung + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", strasse='" + strasse + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", bundesland='" + bundesland + '\'' +
                '}';
    }
}
