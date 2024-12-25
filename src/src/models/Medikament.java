package models;

public class Medikament {
    private int medikamentId; // ID медикамента
    private String name; // Название медикамента
    private String beschreibung; // Описание медикамента

    // Конструктор
    public Medikament(int medikamentId, String name, String beschreibung) {
        this.medikamentId = medikamentId;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    // Геттеры и сеттеры
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

    // Переопределение toString для удобного отображения названий медикаментов (например, в ComboBox)
    @Override
    public String toString() {
        return name;
    }
}