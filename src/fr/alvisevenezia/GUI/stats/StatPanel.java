package fr.alvisevenezia.GUI.stats;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {

    private GlobalManager globalManager;
    JLabel q = new JLabel();
    JLabel bs = new JLabel();
    JLabel sa = new JLabel();

    public StatPanel(GlobalManager globalManager,int nbr,int b,int a) {

        this.globalManager = globalManager;

        q = new JLabel("Nombre de Serpent: "+nbr);
        bs = new JLabel("Best Score: "+b);
        sa = new JLabel("Nombre de Serpent vivant: "+a);
        this.add(q);
        this.add(bs);
        this.add(sa);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
