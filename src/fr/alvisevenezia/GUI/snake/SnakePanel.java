package fr.alvisevenezia.GUI.snake;

import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {

    public SnakePanel(){


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        for(int i = 0;i<50;i++){

            for(int i2 = 0;i2<50;i2++){

                g.drawRect(i*10,i2*10,10,10);

            }

        }

    }
}
