package fr.alvisevenezia.GUI;

import javax.swing.*;
import java.awt.*;

public class IAGUI extends JPanel {

    private final double x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    private final double y = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    private JPanel panel = new JPanel();

    public IAGUI(){

        this.setSize((int)x,(int)y);
        this.setVisible(true);

        panel.setLayout(new GridLayout(10,10));



    }




}
