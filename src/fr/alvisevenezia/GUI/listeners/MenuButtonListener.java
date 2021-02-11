package fr.alvisevenezia.GUI.listeners;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuButtonListener implements ActionListener {

    private GlobalManager globalManager;

    public MenuButtonListener(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JButton button = (JButton)e.getSource();

            switch (button.getText()){

                case "Genetic Algoritm and Neuronal Network":

                    globalManager.getMainGUI().displayMainPanel();
                    break;

                default:

                    break;

            }

        }

    }
}
