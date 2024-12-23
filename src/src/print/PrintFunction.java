package print;

import javax.swing.*;
import java.awt.print.PrinterException;

public class PrintFunction {

    public static void printTable(JTable table, JFrame parentFrame) {
        try {
            boolean complete = table.print(JTable.PrintMode.FIT_WIDTH,
                    new java.text.MessageFormat("Patientenliste"),
                    new java.text.MessageFormat("Seite - {0}"));

            if (complete) {
                JOptionPane.showMessageDialog(parentFrame, "Druck erfolgreich!", "Drucken", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Druck abgebrochen.", "Drucken", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(parentFrame, "Fehler beim Drucken: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
