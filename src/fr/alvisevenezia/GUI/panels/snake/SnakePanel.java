package fr.alvisevenezia.GUI.panels.snake;

import fr.alvisevenezia.SNAKE.Direction;
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
    protected void paintComponent(Graphics g) throws NullPointerException{
        super.paintComponent(g);

        ArrayList<SnakeManager> snakeManagers = globalManager.getBestSnakesAlives(10);

        for (int snakeID = 0; snakeID < snakeManagers.size(); snakeID++) {

            for (int x = 0; x < globalManager.getSize(); x++) {

                for (int y = 0; y < globalManager.getSize(); y++) {

                    if (globalManager.isStarted()) {
                        switch (snakeManagers.get(snakeID).getSnake(x, y)) {

                            case 1:
                                g.setColor(Color.RED);
                                g.fillRect(x * globalManager.getMainGUI().getScale(), y * globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale());

                                break;

                            case 0:

                                if (snakeManagers.get(snakeID).isApple(x, y)) {

                                    g.setColor(Color.GREEN);
                                    g.fillRect(x * globalManager.getMainGUI().getScale(), y * globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale());
                                    break;
                                }
                                g.setColor(Color.BLACK);
                                g.drawRect(x * globalManager.getMainGUI().getScale(), y * globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale());

                                break;

                            default:

                                g.setColor(Color.BLUE);
                                g.fillRect(x * globalManager.getMainGUI().getScale(), y * globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale());

                                break;
                        }
                    }

                    g.setColor(Color.BLACK);
                    g.drawRect(x * globalManager.getMainGUI().getScale(), y * globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale(), globalManager.getMainGUI().getScale());


                }

            }

            if(globalManager.showLines()) {

                for (Direction direction : Direction.values()) {

                    if (snakeManagers.get(snakeID).getApple()[direction.getId()] == 1) {

                        g.setColor(Color.GREEN);

                    } else {

                        g.setColor(Color.RED);

                    }

                    g.drawLine(snakeManagers.get(snakeID).getHeadPos()[0] * globalManager.getMainGUI().getScale(), snakeManagers.get(snakeID).getHeadPos()[1] * globalManager.getMainGUI().getScale(), direction.getCoordsToDisplay(snakeManagers.get(snakeID).getHeadPos()[0], snakeManagers.get(snakeID).getHeadPos()[1], globalManager)[0] * globalManager.getMainGUI().getScale(), direction.getCoordsToDisplay(snakeManagers.get(snakeID).getHeadPos()[0], snakeManagers.get(snakeID).getHeadPos()[1], globalManager)[1] * globalManager.getMainGUI().getScale());

                }

            }

        }

    }

}
