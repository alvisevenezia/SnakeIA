package fr.alvisevenezia.GUI.panels.stats;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel {

    private GlobalManager globalManager;
    private int best1 = 0;
    private int best2 = 0;
    JLabel q = new JLabel("Nombre de Serpent: 0");
    JLabel bs = new JLabel("Best Score: 0");
    JLabel sa = new JLabel("Nombre de Serpent vivant: 0");
    JLabel nbGenLabel = new JLabel("Génération: 0");
    JLabel bestScore1 = new JLabel("Meilleur: 0");
    JLabel bestScore2 = new JLabel("Second: 0");
    JLabel moyenne = new JLabel("Moyenne: 0");
    public StatPanel(GlobalManager globalManager) {

        this.globalManager = globalManager;
        this.add(q);
        this.add(bs);
        this.add(sa);
        this.add(nbGenLabel);
     //   this.add(bestScore1);
     //   this.add(bestScore2);
     //   this.add(moyenne);
    }

    public void update(int nbr,String b,int a,int nbGen,int moy){

        q.setText("Nombre de Serpent: "+nbr);
        bs.setText("Best Score: "+b);
        sa.setText("Nombre de Serpent vivant: "+a);
        nbGenLabel.setText("Génération: "+nbGen);
        bestScore1.setText("Meilleur: "+best1);
        bestScore2.setText("Second: "+best2);
        moyenne.setText("Moyenne: "+moy);

    }

    public void updateBests(int bestScore1,int bestScore2){

        best1 = bestScore1;
        best2 = bestScore2;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
