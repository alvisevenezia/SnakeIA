package fr.alvisevenezia.GUI.panels.snake;

import fr.alvisevenezia.GUI.listeners.LoadButtonListener;
import fr.alvisevenezia.GUI.listeners.SaveButtonListener;
import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class SnakeButtonPanel extends JPanel {

    private GlobalManager globalManager;
    private JLabel score;
    private JLabel max;

    public SnakeButtonPanel(GlobalManager globalManager){

        this.globalManager = globalManager;

        this.setBounds(100,150,300,(int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-300));
        this.setBackground(Color.YELLOW);

        JButton startButton = new JButton();
        startButton.setText("Start IA");
        startButton.addActionListener(globalManager);
        this.add(startButton);

        JButton saveButton = new JButton();
        saveButton.setText("Save IA");
        saveButton.addActionListener(new SaveButtonListener(globalManager));
        this.add(saveButton);

        JButton directionButton = new JButton();
        directionButton.setText("Show directions");
        directionButton.addActionListener(globalManager);
        this.add(directionButton);

        JButton pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.addActionListener(globalManager);
        this.add(pauseButton);

        JButton fileChooserButton = new JButton();
        fileChooserButton.setText("Load Generation");
        fileChooserButton.addActionListener(new LoadButtonListener(globalManager));
        this.add(fileChooserButton);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



    }
}
