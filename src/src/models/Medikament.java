package models;

public class Medikament {
    private int medikamentId; // ID
    private String name; // Name
    private String beschreibung; // Beschreibung

    // Konstruktor
    public Medikament(int medikamentId, String name, String beschreibung) {
        this.medikamentId = medikamentId;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    // Getter und Setter
    public int getMedikamentId() {
        return medikamentId;
    }

    public void setMedikamentId(int medikamentId) {
        this.medikamentId = medikamentId;
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

    // Überschreiben der Methode toString, um die Namen von Medikamenten bequem anzuzeigen (zum Beispiel in einer ComboBox).
    @Override
    public String toString() {
        return name;
    }
}