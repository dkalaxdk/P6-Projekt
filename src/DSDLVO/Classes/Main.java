package DSDLVO.Classes;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        String[] selectedValues = fileInputPrompt();
        if (selectedValues.length == 0) {
            return;
        }
        CASystem caSystem = new CASystem(selectedValues[0], Integer.parseInt(selectedValues[1]));
        caSystem.Start();
    }

    private static String[] fileInputPrompt() {
        // Prompt design
        JPanel myPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        myPanel.setPreferredSize(new Dimension(400, 600));
        JTextField inputMMSI = new JTextField(20);
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setControlButtonsAreShown(false);
        JPanel fileChooserPanel = new JPanel();
        fileChooser.setPreferredSize(new Dimension(400, 400));
        fileChooserPanel.add(fileChooser);
        JPanel MMSIPanel = new JPanel(new GridLayout(2, 1));
        MMSIPanel.add(new JLabel("Own ship MMSI (Integer):"));
        MMSIPanel.add(inputMMSI);
        myPanel.add(fileChooserPanel);
        myPanel.add(MMSIPanel);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Input File Selector", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        String filePath = "";
        String MMSI;
        if (result == JOptionPane.OK_OPTION) {
            if (!fileChooser.getSelectedFile().getAbsolutePath().isEmpty()) {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }

            if (inputMMSI.getText().isEmpty()) {
                MMSI = "1";
            } else MMSI = inputMMSI.getText();

        } else{
            return new String[]{};
        }
        return new String[]{filePath, MMSI};
    }
}
