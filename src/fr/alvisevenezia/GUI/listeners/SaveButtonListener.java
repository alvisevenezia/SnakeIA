package fr.alvisevenezia.GUI.listeners;

import fr.alvisevenezia.IA.CSV.CSVBuilder;
import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class SaveButtonListener implements ActionListener {

    GlobalManager globalManager;

    public SaveButtonListener(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JFrame parent = new JFrame();

            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Select output directory");

            int selection = jFileChooser.showSaveDialog(parent);

            if(selection == JFileChooser.APPROVE_OPTION){

                File file = jFileChooser.getSelectedFile();

                if(!file.exists()){

                    file.mkdir();

                }

                CSVBuilder csvBuilder = new CSVBuilder(file.getAbsolutePath(),globalManager);
                csvBuilder.buildFile();

            }

        }

    }
}
