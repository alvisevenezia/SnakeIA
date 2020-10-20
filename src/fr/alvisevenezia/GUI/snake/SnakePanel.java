package fr.alvisevenezia.GUI.snake;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {

    private GlobalManager globalManager;

    public SnakePanel(GlobalManager globalManager){

        this.globalManager = globalManager;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0;i<50;i++){

            for(int i2 = 0;i2<50;i2++){

                if(globalManager.isStarted()) {

                    g.setColor(Color.BLACK);
                    g.drawRect(i * 10, i2 * 10, 10, 10);
                    if (globalManager.isStarted()) {
                        switch (globalManager.getSnakeManager().getSnake(i, i2)) {

                            case 1:
                                g.setColor(Color.RED);
                                g.fillRect(i * 10, i2 * 10, 10, 10);

                                break;

                            case 0:
                                g.setColor(Color.BLACK);
                                g.drawRect(i * 10, i2 * 10, 10, 10);

                                break;

                            default:

                                g.setColor(Color.BLACK);
                                g.fillRect(i * 10, i2 * 10, 10, 10);

                                break;
                        }
                    }

                    g.setColor(Color.BLACK);
                    g.drawRect(i * 10, i2 * 10, 10, 10);
                }
            }

        }

    }
}
