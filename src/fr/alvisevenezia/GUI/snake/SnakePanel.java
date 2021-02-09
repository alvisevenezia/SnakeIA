package fr.alvisevenezia.GUI.snake;

import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SnakePanel extends JPanel {

    private GlobalManager globalManager;

    public SnakePanel(GlobalManager globalManager){

        this.globalManager = globalManager;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<SnakeManager> snakeManagers = globalManager.getBestSnakesAlives(10);

        for(int x = 0;x<50;x++){

            for(int y = 0;y<50;y++){

                for(int snakeID = 0; snakeID<snakeManagers.size();snakeID++) {

                    g.setColor(Color.BLACK);
                    g.drawRect(x * 10, y * 10, 10, 10);
                    if (globalManager.isStarted()) {
                        switch (snakeManagers.get(snakeID).getSnake(x, y)) {

                            case 1:
                                g.setColor(Color.RED);
                                g.fillRect(x * 10, y * 10, 10, 10);

                                break;

                            case 0:

                                if (snakeManagers.get(snakeID).isApple(x, y)) {

                                    g.setColor(Color.GREEN);
                                    g.fillRect(x * 10, y * 10, 10, 10);
                                    break;
                                }
                                g.setColor(Color.BLACK);
                                g.drawRect(x * 10, y * 10, 10, 10);

                                break;

                            default:

                                g.setColor(Color.BLACK);
                                g.fillRect(x * 10, y * 10, 10, 10);

                                break;
                        }
                    }

                    g.setColor(Color.BLACK);

                    g.drawRect(x * 10, y * 10, 10, 10);

                }

            }

        }

    }
}
