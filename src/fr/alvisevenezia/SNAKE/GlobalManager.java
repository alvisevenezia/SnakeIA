package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.MainGUI;
import fr.alvisevenezia.IA.IAIteration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class GlobalManager implements ActionListener {

    private SnakeManager snakeManager;

    public MainGUI mainGUI;
    public JFrame currentFrame;
    private boolean isStarted = false;
    private Timer timer;
    private HashMap<IAIteration,SnakeManager> managers;

    public GlobalManager(){

        mainGUI = new MainGUI(this);
        currentFrame = mainGUI;
        mainGUI.openFrame();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JButton button = (JButton) e.getSource();

            if(button.getText().equalsIgnoreCase("Lancer Snake")){

                button.setText("Arreter IA");
                createFirstGeneration();
                mainGUI.getSnake().repaint();


            }else if(button.getText().equalsIgnoreCase("Arreter IA")){

                button.setText("Lancer Snake");
                stopRunnable();

            }
        }

    }

    public void startRunnable(){

        timer = new Timer();
        timer.schedule(new SnakeRunnable(this),1000,1000);

    }

    public void stopRunnable(){

        timer.cancel();

    }

    public void startIA(int quantite){



    }

    public void createFirstGeneration(){

        snakeManager = new SnakeManager(this);
        snakeManager.createSnake();
        startRunnable();
        setStarted(true);

    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public SnakeManager getSnakeManager() {
        return snakeManager;
    }

    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }


}
