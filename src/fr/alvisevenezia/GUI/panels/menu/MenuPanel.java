package fr.alvisevenezia.GUI.panels.menu;

import fr.alvisevenezia.GUI.listeners.MenuButtonListener;
import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private GlobalManager globalManager;
    private MenuButtonListener menuButtonListener;

    public MenuPanel(GlobalManager globalManager) {

        this.globalManager = globalManager;

    }

    public void setup(){

        this.setBackground(Color.LIGHT_GRAY);

        menuButtonListener = new MenuButtonListener(globalManager);

        JButton gann = new JButton("Genetic Algoritm and Neuronal Network");
        gann.addActionListener(menuButtonListener);
        this.add(gann);

    }


}
