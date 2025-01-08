package models;

/**
 * Die Klasse Medikament repräsentiert ein Medikament.
 * Sie enthält Informationen wie die ID, den Namen und die Beschreibung des Medikaments.
 */
public class Medikament {
    private int medikamentId; // ID des Medikaments
    private String name; // Name des Medikaments
    private String beschreibung; // Beschreibung des Medikaments

    /**
     * Konstruktor zur Initialisierung eines Medikaments mit allen Eigenschaften.
     *
     * @param medikamentId Die eindeutige ID des Medikaments.
     * @param name         Der Name des Medikaments.
     * @param beschreibung Die Beschreibung des Medikaments.
     */
    public Medikament(int medikamentId, String name, String beschreibung) {
        this.medikamentId = medikamentId;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt die ID des Medikaments zurück.
     *
     * @return die Medikament-ID.
     */
    public int getMedikamentId() {
        return medikamentId;
    }

    /**
     * Setzt die ID des Medikaments.
     *
     * @param medikamentId die neue Medikament-ID.
     */
    public void setMedikamentId(int medikamentId) {
        this.medikamentId = medikamentId;
    }

    /**
     * Gibt den Namen des Medikaments zurück.
     *
     * @return der Name des Medikaments.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Medikaments.
     *
     * @param name der neue Name des Medikaments.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Beschreibung des Medikaments zurück.
     *
     * @return die Beschreibung des Medikaments.
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Setzt die Beschreibung des Medikaments.
     *
     * @param beschreibung die neue Beschreibung des Medikaments.
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Überschreibt die Methode {@link Object#toString()}, um den Namen des Medikaments zurückzugeben.
     * Dies ist nützlich, wenn ein Medikament in einer visuellen Komponente wie einer ComboBox angezeigt wird.
     *
     * @return der Name des Medikaments.
     */
    @Override
    public String toString() {
        return name;
    }
}
