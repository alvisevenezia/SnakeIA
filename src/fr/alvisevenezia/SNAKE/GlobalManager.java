package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.MainGUI;
import fr.alvisevenezia.IA.IAIteration;
import jdk.dynalink.StandardNamespace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.ServerNotActiveException;
import java.util.*;
import java.util.Timer;

public class GlobalManager implements ActionListener {

    private SnakeManager snakeManager;

    public MainGUI mainGUI;
    public JFrame currentFrame;
    private boolean isStarted = false;
    private Timer timer;
    private HashMap<IAIteration,SnakeManager> managers;
    private int iterationcount = 0;
    private int snakeQuantity;
    private int snakeAliveQuantity;
    private Timer runnable;
    private ArrayList<SnakeManager> winner;

    public GlobalManager(){

        mainGUI = new MainGUI(this);
        managers = new HashMap<>();
        currentFrame = mainGUI;
        mainGUI.openFrame();

    }

    public HashMap<IAIteration, SnakeManager> getManagers() {
        return managers;
    }

    public void startGlobalRunnale(){

        runnable = new Timer();
        runnable.schedule(new GlobalRunnable(this),1000,1000);

    }

    public void stopGlobalRunnale(){

        runnable.cancel();

    }

    public void removeManager(SnakeManager snakeManager){

        managers.remove(snakeManager);

    }

    public ArrayList<IAIteration> getIaIterations(ArrayList<SnakeManager> snakeManagers){

        Collection<IAIteration> manager = managers.keySet();
        ArrayList<IAIteration> ia = new ArrayList<>();

        System.out.println("GI: "+snakeManagers.size());
        System.out.println("GI: "+manager.size());

        for(int i = 0;i<snakeManagers.size();i++) {

            System.out.println("GI: "+i);

            for (IAIteration iaIteration : manager) {

                if (iaIteration.getSnakeManager() == snakeManagers.get(i)) {

                    ia.add(iaIteration);
                    System.out.println("GI: Trouvé");

                }

            }
        }

        System.out.println("GI2: "+ia.size());
        return ia;

    }

    public ArrayList<SnakeManager> getWinner(){

        return winner;

    }

    public void setWinner(ArrayList<SnakeManager> winner) {
        this.winner = winner;
    }

    public int getSnakeAliveQuantity() {
        return snakeAliveQuantity;
    }

    public void setSnakeAliveQuantity(int snakeAliveQuantity) {
        this.snakeAliveQuantity = snakeAliveQuantity;
    }

    public IAIteration getIaIteration(SnakeManager snakeManager){

        for (IAIteration iaIteration : managers.keySet()) {

            if (iaIteration.getSnakeManager() == snakeManager) {

                return iaIteration;

            }

        }

        return null;

    }

    public ArrayList<SnakeManager> getBestSnakes(){

        ArrayList<SnakeManager> bests = new ArrayList<>();
        ArrayList<SnakeManager> snakemanagers =  new ArrayList<>(managers.values());
        Collection<SnakeManager>azdecd = managers.values();

        System.out.println("BS: "+snakemanagers.size());


        for(int i = 0;i < 10;i++){

            System.out.println("BS: "+i);

            SnakeManager best = null;

            for(SnakeManager m : snakemanagers){

                int max = -1;

                if(m.getScore() > max){

                    max = m.getScore();
                    best = m;

                }

            }

            bests.add(best);
            snakemanagers.remove(best);
            System.out.println("BS2: "+managers.size());
            System.out.println("BS3: "+snakemanagers.size());

        }

        System.out.println("BS: "+bests.size());
        System.out.println("BS: "+managers.size());
        return bests;
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
                createFirstGeneration(200);
                mainGUI.getSnake().repaint();
                startGlobalRunnale();


            }else if(button.getText().equalsIgnoreCase("Arreter IA")){

                button.setText("Lancer Snake");
                stopRunnables();
                stopGlobalRunnale();

            }
        }

    }

    public void stopRunnables() {

        for(SnakeManager snakeManager : managers.values()){

            snakeManager.stopRunnable();

        }
    }

    public int getIterationcount() {
        return iterationcount;
    }

    public void setIterationcount(int iterationcount) {
        this.iterationcount = iterationcount;
    }

    public void startIA(int quantite){

        for(SnakeManager m : managers.values()){

            m.stopRunnable();

        }

        HashMap<IAIteration, SnakeManager>list = new HashMap<>();

        setSnakeQuantity(quantite);
        setSnakeAliveQuantity(quantite);

        while (quantite != 0){

            IAIteration iaIteration = new IAIteration(this,iterationcount);
            SnakeManager snakeManager = new SnakeManager(this,iaIteration);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.startRunnable();
            iaIteration.setSnakeManager(snakeManager);

            iaIteration.createIA(false);

            list.put(iaIteration,snakeManager);

            iterationcount++;

            quantite--;

        }

        managers = list;

    }



    public void createFirstGeneration(int i){

        setSnakeQuantity(i);
        setSnakeAliveQuantity(i);

        while (i != 0){

            IAIteration iaIteration = new IAIteration(this,iterationcount);
            iaIteration.createIA(true);

            SnakeManager snakeManager = new SnakeManager(this,iaIteration);
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

    public int getSnakeQuantity() {
        return snakeQuantity;
    }

    public void setSnakeQuantity(int snakeQuantity) {
        this.snakeQuantity = snakeQuantity;
    }

    public void removeOneSnake(){

        setSnakeAliveQuantity(getSnakeAliveQuantity()-1);

    }
}
