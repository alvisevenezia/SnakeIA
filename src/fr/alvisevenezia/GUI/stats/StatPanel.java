package fr.alvisevenezia.GUI.stats;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {

    private GlobalManager globalManager;
    JLabel q = new JLabel();
    JLabel bs = new JLabel();
    JLabel sa = new JLabel();
    JLabel nbGenLabel = new JLabel();

    public StatPanel(GlobalManager globalManager,int nbr,int b,int a,int nbGen) {

        this.globalManager = globalManager;

        q = new JLabel("Nombre de Serpent: "+nbr);
        bs = new JLabel("Best Score: "+b);
        sa = new JLabel("Nombre de Serpent vivant: "+a);
        nbGenLabel = new JLabel("Génration: "+nbGen);
        this.add(q);
        this.add(bs);
        this.add(sa);
        this.add(nbGenLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
