package Panels;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import DAO.PatientDAO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import models.Patient;


public class PatientenPanel extends JPanel {
    private JTable patientenTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> anredeComboBox;
    private JTextField vornameField;
    private JTextField nachnameField;
    private JTextField geburtstagField;
    private JTextField svnrField;
    private JComboBox<String> insuranceComboBox;
    private JTextField telefonnummerField;
    private JTextField strasseField;
    private JTextField plzField;
    private JTextField ortField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> bundeslandComboBox;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    private PatientDAO patientDAO;
    private PatientDiagnosenPanel patientDiagnosenPanel; // Referenz für Dropdown-Aktualisierung

    private JTextField searchField;

    private RezeptePanel rezeptePanel;



    public PatientenPanel(PatientDiagnosenPanel patientDiagnosenPanel) {
        this.patientDiagnosenPanel = patientDiagnosenPanel;



        patientDAO = new PatientDAO();
        setLayout(new BorderLayout());



        // Tabelle
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Anrede", "Vorname", "Nachname", "Geburtstag", "SVNR",
                "Versicherung", "Telefon", "Straße", "PLZ", "Ort", "Geschlecht", "Bundesland"}, 0);
        patientenTable = new JTable(tableModel);
        patientenTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 250)); // Zebra-Farben
                } else {
                    c.setBackground(new Color(184, 207, 229)); // Hervorhebung der ausgewählten Zeile
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientenTable);
        add(scrollPane, BorderLayout.CENTER);

        // Tabelle anklickbar machen
        patientenTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPatient();
            }
        });


        // Im Konstruktor aufrufen
        //enableTableSorting();

        // In der PatientenPanel-Konstruktor
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Suche:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        add(searchPanel, BorderLayout.NORTH);

        // Event für die Suche hinzufügen
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });




        // Formular
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        formPanel.add(new JLabel("Anrede:"));
        anredeComboBox = new JComboBox<>(new String[]{"Herr", "Frau", "Dr."});
        formPanel.add(anredeComboBox);

        formPanel.add(new JLabel("Vorname:"));
        vornameField = new JTextField();
        formPanel.add(vornameField);

        formPanel.add(new JLabel("Nachname:"));
        nachnameField = new JTextField();
        formPanel.add(nachnameField);

        formPanel.add(new JLabel("Geburtstag (YYYY-MM-DD):"));
        geburtstagField = new JTextField();
        formPanel.add(geburtstagField);

        formPanel.add(new JLabel("SVNR:"));
        svnrField = new JTextField();
        formPanel.add(svnrField);

        formPanel.add(new JLabel("Versicherung:"));
        insuranceComboBox = new JComboBox<>();
        formPanel.add(insuranceComboBox);

        formPanel.add(new JLabel("Telefonnummer:"));
        telefonnummerField = new JTextField();
        formPanel.add(telefonnummerField);

        formPanel.add(new JLabel("Straße:"));
        strasseField = new JTextField();
        formPanel.add(strasseField);

        formPanel.add(new JLabel("PLZ:"));
        plzField = new JTextField();
        formPanel.add(plzField);

        formPanel.add(new JLabel("Ort:"));
        ortField = new JTextField();
        formPanel.add(ortField);

        formPanel.add(new JLabel("Geschlecht:"));
        genderComboBox = new JComboBox<>();
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Bundesland:"));
        bundeslandComboBox = new JComboBox<>();
        formPanel.add(bundeslandComboBox);

        addButton = new JButton("Hinzufügen");
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton = new JButton("Bearbeiten");
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton = new JButton("Löschen");
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton exportPdfButton = new JButton("PDF Export");
        exportPdfButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(addButton);
        formPanel.add(editButton);
        formPanel.add(deleteButton);
        formPanel.add(exportPdfButton); // Button zum Formular hinzufügen

        add(formPanel, BorderLayout.SOUTH);





        // Button-Events
        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> updatePatient());
        deleteButton.addActionListener(e -> deletePatient());
        exportPdfButton.addActionListener(e -> exportToPDF());

        // Daten in ComboBoxen laden
        loadComboBoxData();

        // Patienten laden
        loadPatientenData();
    }





    private void loadComboBoxData() {
        try {
            // Versicherungsliste laden
            List<String> insuranceList = patientDAO.getInsuranceList();
            insuranceComboBox.removeAllItems();
            for (String insurance : insuranceList) {
                insuranceComboBox.addItem(insurance); // Namen hinzufügen
            }

            // Geschlechterliste laden
            List<String> genderList = patientDAO.getGenderList();
            genderComboBox.removeAllItems();
            for (String gender : genderList) {
                genderComboBox.addItem(gender); // Namen hinzufügen
            }

            // Bundeslandliste laden
            List<String> bundeslandList = patientDAO.getBundeslandList();
            bundeslandComboBox.removeAllItems();
            for (String bundesland : bundeslandList) {
                bundeslandComboBox.addItem(bundesland); // Namen hinzufügen
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Listen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }



    private String formatDate(String date) {
        try {
            // Versucht, das Datum im Format "yyyy-MM-dd" zu parsen
            LocalDate parsedDate = LocalDate.parse(date);
            // Gibt das Datum im deutschen Format "dd.MM.yyyy" zurück
            return parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            // Gibt das Originaldatum zurück, falls ein Fehler auftritt
            return date;
        }
    }

    private void exportToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("PDF speichern unter...");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String pdfFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!pdfFilePath.endsWith(".pdf")) {
                pdfFilePath += ".pdf"; // .pdf hinzufügen, falls nicht vorhanden
            }

            try {
                PdfWriter writer = new PdfWriter(pdfFilePath);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                // Titel hinzufügen
                document.add(new Paragraph("Patientenbericht")
                        .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(20));

                // Kopfzeilen und Spaltenbreite definieren
                String[] headers = {"ID", "Anrede", "Vorname", "Nachname", "Geburtstag", "Versicherung"}; // Telefon entfernt
                float[] columnWidths = {30, 60, 80, 80, 100, 100}; // Angepasste Breiten
                Table table = new Table(columnWidths);
                table.setWidth(UnitValue.createPercentValue(100));

                // Tabellenkopf hinzufügen
                for (String header : headers) {
                    table.addHeaderCell(new Cell()
                            .add(new Paragraph(header))
                            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                            .setBold()
                            .setTextAlignment(TextAlignment.CENTER));
                }

                // Tabellendaten hinzufügen
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < headers.length; j++) { // Nur relevante Spalten
                        Object cellValue = tableModel.getValueAt(i, j);
                        String cellText = (cellValue != null) ? cellValue.toString() : "-";

                        if (j == 4) { // Spalte Geburtstag
                            cellText = formatDate(cellText); // Nur Datum formatieren
                        }

                        table.addCell(new Cell()
                                .add(new Paragraph(cellText))
                                .setTextAlignment(TextAlignment.LEFT)
                                .setPadding(5));
                    }
                }

                // Datum des Exports hinzufügen
                document.add(table);
                document.add(new Paragraph("Exportiert am: " + LocalDate.now())
                        .setTextAlignment(TextAlignment.RIGHT).setFontSize(10).setItalic());

                // Dokument schließen
                document.close();
                JOptionPane.showMessageDialog(this, "PDF erfolgreich erstellt: " + pdfFilePath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fehler beim Speichern: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




    private void loadPatientenData() {
        try {
            tableModel.setRowCount(0); // Tabelle zurücksetzen
            List<Patient> patienten = patientDAO.getAllPatients();
            for (Patient patient : patienten) {
                System.out.println("Geladene Daten: " + patient.getId() + ", " + patient.getInsuranceId() + ", " + patient.getGenderId() + ", " + patient.getBundeslandId());
                tableModel.addRow(new Object[]{
                        patient.getId(),
                        patient.getAnrede(),
                        patient.getVorname(),
                        patient.getNachname(),
                        patient.getGeburtstag(),
                        patient.getSvnr(),
                        patient.getInsuranceId(), // Versicherung
                        patient.getTelefonnummer(),
                        patient.getStrasse(),
                        patient.getPlz(),
                        patient.getOrt(),
                        patient.getGenderId(), // Geschlecht
                        patient.getBundeslandId() // Bundesland
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Patienten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadSelectedPatient() {



        int selectedRow = patientenTable.getSelectedRow();

        if (selectedRow != -1) {
            System.out.println("Geladene Zeile: " + selectedRow);
            System.out.println("Versicherung ID: " + tableModel.getValueAt(selectedRow, 6));
            System.out.println("Geschlecht ID: " + tableModel.getValueAt(selectedRow, 11));
            System.out.println("Bundesland ID: " + tableModel.getValueAt(selectedRow, 12));
            // Setze Textfelder
            anredeComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 1).toString());
            vornameField.setText((String) tableModel.getValueAt(selectedRow, 2));
            nachnameField.setText((String) tableModel.getValueAt(selectedRow, 3));
            geburtstagField.setText((String) tableModel.getValueAt(selectedRow, 4));
            svnrField.setText((String) tableModel.getValueAt(selectedRow, 5));
            telefonnummerField.setText((String) tableModel.getValueAt(selectedRow, 7));
            strasseField.setText((String) tableModel.getValueAt(selectedRow, 8));
            plzField.setText((String) tableModel.getValueAt(selectedRow, 9));
            ortField.setText((String) tableModel.getValueAt(selectedRow, 10));

            // Versicherung
            String insuranceId = tableModel.getValueAt(selectedRow, 6).toString();
            String insuranceName = getInsuranceNameById(insuranceId);
            System.out.println("Versicherung ID: " + insuranceId + ", Name: " + insuranceName); // Debugging
            insuranceComboBox.setSelectedItem(insuranceName);

            // Geschlecht
            String genderId = tableModel.getValueAt(selectedRow, 11).toString();
            String genderName = getGenderNameById(genderId);
            System.out.println("Geschlecht ID: " + genderId + ", Name: " + genderName); // Debugging
            genderComboBox.setSelectedItem(genderName);


            // Bundesland
            String bundeslandId = tableModel.getValueAt(selectedRow, 12).toString();
            String bundeslandName = getBundeslandNameById(bundeslandId);
            System.out.println("Bundesland ID: " + bundeslandId + ", Name: " + bundeslandName); // Debugging
            bundeslandComboBox.setSelectedItem(bundeslandName);

        }
    }
    private String getInsuranceNameById(String id) {
        switch (id) {
            case "1": return "STGKK";
            case "2": return "BVAEB";
            case "3": return "SVS";
            case "4": return "KFA";
            case "5": return "VAEB";
            case "6": return "Andere";
            default: return "";
        }
    }
    private String getGenderNameById(String id) {
        switch (id) {
            case "1": return "männlich";
            case "2": return "weiblich";
            case "3": return "divers";
            default: return "";
        }
    }
    private String getBundeslandNameById(String id) {
        switch (id) {
            case "1": return "Burgenland";
            case "2": return "Kärnten";
            case "3": return "Niederösterreich";
            case "4": return "Oberösterreich";
            case "5": return "Salzburg";
            case "6": return "Steiermark";
            case "7": return "Tirol";
            case "8": return "Vorarlberg";
            case "9": return "Wien";
            default: return "";
        }
    }


    // Methode zur Filterung der Tabelle
    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) patientenTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        patientenTable.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    // Sortierfunktion aktivieren
    // private void enableTableSorting() {
    //  DefaultTableModel model = (DefaultTableModel) patientenTable.getModel();
    //  TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    //   patientenTable.setRowSorter(sorter);
    // }


    private void addPatient() {
        String anrede = (String) anredeComboBox.getSelectedItem();
        String vorname = vornameField.getText();
        String nachname = nachnameField.getText();
        String geburtstag = geburtstagField.getText();
        String svnr = svnrField.getText();
        String insuranceName = (String) insuranceComboBox.getSelectedItem();
        String telefonnummer = telefonnummerField.getText();
        String strasse = strasseField.getText();
        String plz = plzField.getText();
        String ort = ortField.getText();
        String genderName = (String) genderComboBox.getSelectedItem();
        String bundeslandName = (String) bundeslandComboBox.getSelectedItem();

        if (vorname.isEmpty() || nachname.isEmpty() || geburtstag.isEmpty() || svnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alle Felder müssen ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int insuranceId = patientDAO.getInsuranceIdByName(insuranceName);
            int genderId = patientDAO.getGenderIdByName(genderName);
            int bundeslandId = patientDAO.getBundeslandIdByName(bundeslandName);

            Patient patient = new Patient(0, anrede, vorname, nachname, geburtstag, svnr, String.valueOf(insuranceId),
                    telefonnummer, strasse, plz, ort, genderId, bundeslandId);
            patientDAO.addPatient(patient);
            JOptionPane.showMessageDialog(this, "Patient hinzugefügt!");
            loadPatientenData();

            if (rezeptePanel != null) {
                try {
                    List<Patient> updatedPatients = patientDAO.getAllPatients();
                    rezeptePanel.refreshPatientenComboBox(updatedPatients);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren der Patientenliste: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }




            // Patienten-Dropdown aktualisieren
            if (patientDiagnosenPanel != null) {
                patientDiagnosenPanel.updatePatientenDropdown();
            }

            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        int selectedRow = patientenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Patienten aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int patientId = (int) tableModel.getValueAt(selectedRow, 0);
            String anrede = (String) anredeComboBox.getSelectedItem();
            String vorname = vornameField.getText();
            String nachname = nachnameField.getText();
            String geburtstag = geburtstagField.getText();
            String svnr = svnrField.getText();
            String insuranceName = (String) insuranceComboBox.getSelectedItem();
            String telefonnummer = telefonnummerField.getText();
            String strasse = strasseField.getText();
            String plz = plzField.getText();
            String ort = ortField.getText();
            String genderName = (String) genderComboBox.getSelectedItem();
            String bundeslandName = (String) bundeslandComboBox.getSelectedItem();

            int insuranceId = patientDAO.getInsuranceIdByName(insuranceName);
            int genderId = patientDAO.getGenderIdByName(genderName);
            int bundeslandId = patientDAO.getBundeslandIdByName(bundeslandName);

            Patient patient = new Patient(patientId, anrede, vorname, nachname, geburtstag, svnr, String.valueOf(insuranceId),
                    telefonnummer, strasse, plz, ort, genderId, bundeslandId);

            patientDAO.updatePatient(patient);
            JOptionPane.showMessageDialog(this, "Patient aktualisiert!");
            loadPatientenData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Bearbeiten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = patientenTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Patienten aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            patientDAO.deletePatient(patientId);
            JOptionPane.showMessageDialog(this, "Patient gelöscht!");
            loadPatientenData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Löschen: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        anredeComboBox.setSelectedIndex(0);
        vornameField.setText("");
        nachnameField.setText("");
        geburtstagField.setText("");
        svnrField.setText("");
        insuranceComboBox.setSelectedIndex(0);
        telefonnummerField.setText("");
        strasseField.setText("");
        plzField.setText("");
        ortField.setText("");
        genderComboBox.setSelectedIndex(0);
        bundeslandComboBox.setSelectedIndex(0);
    }
    // Methode zum Setzen der Referenz auf das RezeptePanel
    public void setRezeptePanel(RezeptePanel rezeptePanel) {
        this.rezeptePanel = rezeptePanel;
    }



}