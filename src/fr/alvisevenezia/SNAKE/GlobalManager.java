package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.MainGUI;

import javax.swing.*;

public class GlobalManager {

    private SnakeManager snakeManager;

    public static MainGUI mainGUI;
    public static JFrame currentFrame;

    public GlobalManager(){

        mainGUI = new MainGUI();
        currentFrame = mainGUI;
        mainGUI.openFrame();

    }

    public SnakeManager getSnakeManager() {
        return snakeManager;
    }

    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }
}
