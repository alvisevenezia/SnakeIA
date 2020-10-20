package fr.alvisevenezia.GUI;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private GlobalManager globalManager;

    public MainPanel(GlobalManager globalManager){

        this.globalManager = globalManager;

        this.setBounds(100,150,300,(int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-300));
        this.setBackground(Color.YELLOW);

        JButton button = new JButton();
        button.setText("Lancer Snake");
        button.addActionListener(globalManager);

        this.add(button);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



    }
}
