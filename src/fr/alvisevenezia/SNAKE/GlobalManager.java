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
    private int iterationcount = 0;

    public GlobalManager(){

        mainGUI = new MainGUI(this);
        managers = new HashMap<>();
        currentFrame = mainGUI;
        mainGUI.openFrame();

    }

    public SnakeManager getBestSnake(){

        int max = -1;
        SnakeManager best = null;

        for(SnakeManager snakeManager : managers.values()){


            if(snakeManager.getScore() > max){

                max = snakeManager.getScore();
                best = snakeManager;

            }

        }

        return best;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JButton button = (JButton) e.getSource();

            if(button.getText().equalsIgnoreCase("Lancer Snake")){

                button.setText("Arreter IA");
                createFirstGeneration(1);
                mainGUI.getSnake().repaint();


            }else if(button.getText().equalsIgnoreCase("Arreter IA")){

                button.setText("Lancer Snake");
                stopRunnables();

            }
        }

    }

    private void stopRunnables() {

        for(SnakeManager snakeManager : managers.values()){

            snakeManager.stopRunnable();

        }
    }

    public void startIA(int quantite){



    }

    public void createFirstGeneration(int i){

        while (i != 0){

            IAIteration iaIteration = new IAIteration(this,iterationcount);
            SnakeManager snakeManager = new SnakeManager(this);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.startRunnable();
            iaIteration.setSnakeManager(snakeManager);
            managers.put(iaIteration,snakeManager);

            iterationcount++;

            i--;

        }

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

    public SnakeManager getSnakeManager(IAIteration iaIteration) {
        return managers.get(iaIteration);
    }

    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }


}
