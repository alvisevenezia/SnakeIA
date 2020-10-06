package fr.alvisevenezia.GUI.listener;

import fr.alvisevenezia.GUI.IAGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewIAListener implements ActionListener {

    JFrame frame;

    public NewIAListener(JFrame frame){

        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        frame.setContentPane(new IAGUI());
        frame.setTitle("Generation IA");

    }
}
