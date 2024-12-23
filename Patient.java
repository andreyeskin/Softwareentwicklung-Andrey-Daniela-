public class Patient {
    /**
     * Die Klasse Patient repräsentiert einen Patienten mit grundlegenden Attributen.
     */

        private int id;
        private String name;
        private int alter;
        private String adresse;

        // Konstruktor
        public Patient(int id, String name, int alter, String adresse) {
            this.id = id;
            this.name = name;
            this.alter = alter;
            this.adresse = adresse;
        }

        // Getter und Setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAlter() {
            return alter;
        }

        public void setAlter(int alter) {
            this.alter = alter;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        // Methode zur Ausgabe von Patientendaten
        @Override
        public String toString() {
            return "Patient{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", alter=" + alter +
                    ", adresse='" + adresse + '\'' +
                    '}';
        }
    }
