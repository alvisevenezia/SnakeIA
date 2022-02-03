package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.GUIType;
import fr.alvisevenezia.GUI.MainGUI;
import fr.alvisevenezia.IA.GA.GeneticAlgoritmManager;
import fr.alvisevenezia.IA.IAType;
import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.*;

public class GlobalManager implements ActionListener {

    private SnakeManager snakeManager;

    public MainGUI mainGUI;
    public JFrame currentFrame;
    private boolean isStarted = false;
    private boolean showLines = false;
    private boolean isLoaded = false;
    private final int size = 31;
    private final String path = "C:\\Users\\wanto\\OneDrive\\Documents\\DEV\\IA\\SnakeIA";
    private int[] layersSize = new int[]{24, 24, 24, 4};
    private GeneticAlgoritmManager geneticAlgoritmManager;

    private IAType iaType;

    public GeneticAlgoritmManager getGeneticAlgoritmManager() {
        return geneticAlgoritmManager;
    }

    public void setGeneticAlgoritmManager(GeneticAlgoritmManager geneticAlgoritmManager) {
        this.geneticAlgoritmManager = geneticAlgoritmManager;
    }

    public GlobalManager(){

    }

    public void startWithHead(){

        mainGUI = new MainGUI(this);
        mainGUI.createSnakeGUI();
        mainGUI.createMenuGUI();
        currentFrame = mainGUI;
        mainGUI.openFrame(GUIType.Menu);

    }

    public void startHeadless(){

        setGeneticAlgoritmManager(new GeneticAlgoritmManager(this));
        setIaType(IAType.GANN);
        geneticAlgoritmManager.generateApple();
        geneticAlgoritmManager.createFirstGeneration();
        try {
            wait(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        geneticAlgoritmManager.startCalculationThread();
    }

    public void setShowLines(boolean showLines) {
        this.showLines = showLines;
    }

    public boolean getShowLines(){
        return showLines;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JButton button = (JButton) e.getSource();

            switch (button.getText()){

                case "Start IA":

                    button.setText("Stop IA");
                    mainGUI.getSnake().repaint();
                    switch (iaType){

                        case GANN:

                            geneticAlgoritmManager.generateApple();
                            geneticAlgoritmManager.createFirstGeneration();
                            try {
                                wait(100);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            geneticAlgoritmManager.startCalculationThread();
                            break;

                    }

                    break;

                case "Stop IA":

                    button.setText("Start IA");

                    switch (iaType){

                        case GANN:

                            geneticAlgoritmManager.stopCalculationThread();
                            break;

                    }
                    break;

                case "Show directions":

                    showLines = !showLines;

                    break;

                case "Pause":

                    isStarted = !isStarted;

                    break;


                default:

                    break;

            }

        }

    }

    public IAType getIaType() {
        return iaType;
    }

    public void setIaType(IAType iaType) {
        this.iaType = iaType;
    }

    public int[] getLayersSize() {
        return layersSize;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public boolean showLines() {
        return showLines;
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

    public void removeOneSnake() {

        switch (iaType){

            case GANN:

                geneticAlgoritmManager.removeOneSnake();
                break;

        }
    }
}