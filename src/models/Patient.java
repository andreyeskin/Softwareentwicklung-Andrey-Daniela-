// Clase Patient.java
package models;

import java.time.LocalDate;

/**
 * Die Klasse Patient repräsentiert einen Patienten mit grundlegenden Attributen.
 */
public class Patient {
    private int id;
    private String vorname;
    private String nachname;
    private String geschlecht;
    private String adresse;
    private int alter;

    // Konstruktor
    public Patient(int id, String vorname, String nachname, String geschlecht, String adresse, int alter) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geschlecht = geschlecht;
        this.adresse = adresse;
        this.alter = alter;
    }

    public Patient(int id, String anrede, String vorname, String nachname, LocalDate geburtsdatum, String svnr, String versicherung, String telefonnummer, String strasse, String plz, String ort, String bundesland) {
    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        if (alter <= 0) {
            throw new IllegalArgumentException("Alter muss größer als 0 sein.");
        }
        this.alter = alter;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", geschlecht='" + geschlecht + '\'' +
                ", adresse='" + adresse + '\'' +
                ", alter=" + alter +
                '}';
    }

    public Object getAnrede() {
    }
}
