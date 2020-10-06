package fr.alvisevenezia.GUI;

import fr.alvisevenezia.GUI.listener.NewIAListener;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private final double x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    private final double y = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    private JPanel panel = new JPanel();

    public MainGUI(){

        this.setSize((int)x,(int)y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SnakeIA");

        JButton button = new JButton();
        button.setText("Nouvelle IA");
        button.addActionListener(new NewIAListener(this));

        panel.add(button);

        this.setContentPane(panel);
    }

    public boolean openFrame(){

        this.setVisible(true);

        return true;

    }
}
