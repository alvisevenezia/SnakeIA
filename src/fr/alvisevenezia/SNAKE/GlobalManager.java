package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.MainGUI;
import fr.alvisevenezia.IA.IAIteration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class GlobalManager implements ActionListener {

    private SnakeManager snakeManager;

    public MainGUI mainGUI;
    public JFrame currentFrame;
    private boolean isStarted = false;
    private HashMap<IAIteration,SnakeManager> managers;
    private int iterationcount = 0;
    private int snakeQuantity;
    private int snakeAliveQuantity;
    private int generationCOunt = 0;
    private Timer runnable;
    private ArrayList<SnakeManager> winner;
    private Random random;

    public GlobalManager(){

        mainGUI = new MainGUI(this);
        managers = new HashMap<>();
        currentFrame = mainGUI;
        mainGUI.openFrame();
        random = new Random();
    }

    public Random getRandom(){
        return random;
    }

    public int getGenerationCOunt() {
        return generationCOunt;
    }

    public void setGenerationCOunt(int generationCOunt) {
        this.generationCOunt = generationCOunt;
    }

    public HashMap<IAIteration, SnakeManager> getManagers() {
        return managers;
    }

    public void startGlobalRunnale(){

        runnable = new Timer();
        runnable.schedule(new GlobalRunnable(this),1,1);

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

        for(int i = 0;i<snakeManagers.size();i++) {

            for (IAIteration iaIteration : manager) {

                if (iaIteration.getSnakeManager() == snakeManagers.get(i)) {

                    ia.add(iaIteration);

                }

            }
        }

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

        for(int i = 0;i < 2;i++){

            SnakeManager best = null;
            int score = 0;

            int max = -1;

            for(SnakeManager m : snakemanagers){

                if(m.getScore() > max){

                    max = m.getScore();
                    best = m;

                }

            }

            System.out.println(best.getScore());

            bests.add(best);
            snakemanagers.remove(best);

        }

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
                createFirstGeneration(2000);
                mainGUI.getSnake().repaint();


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

        generationCOunt++;

        setStarted(false);

        ArrayList<IAIteration>ias = getIaIterations(getWinner());
        HashMap<IAIteration, SnakeManager>list = new HashMap<>();

        setSnakeQuantity(quantite);
        setSnakeAliveQuantity(quantite);

        while (quantite != 2){

         /*   if(ias.get(0).getSnakeManager().getScore() <= 25 || ias.get(1).getSnakeManager().getScore() <= 25){

                IAIteration iaIteration = new IAIteration(this, iterationcount);
                iaIteration.createIA(true, null);
                SnakeManager snakeManager = new SnakeManager(this, iaIteration);
                snakeManager.createSnake();
                snakeManager.initilizeApple();
                snakeManager.setAlive(true);
                iaIteration.setSnakeManager(snakeManager);
                list.put(iaIteration,snakeManager);
                iterationcount++;
                quantite--;

            }else {*/

                IAIteration iaIteration = new IAIteration(this, iterationcount);
                iaIteration.createIA(false, ias);
                SnakeManager snakeManager = new SnakeManager(this, iaIteration);
                snakeManager.createSnake();
                snakeManager.initilizeApple();
                snakeManager.setAlive(true);
                iaIteration.setSnakeManager(snakeManager);
                list.put(iaIteration, snakeManager);
                iterationcount++;
                quantite--;

           // }
        }

        System.out.println("GM: "+winner.get(0).getScore());
        System.out.println("GM: "+winner.get(1).getScore());

        SnakeManager snakeManager = new SnakeManager(this,getIaIteration(winner.get(0)));
        snakeManager.createSnake();
        snakeManager.initilizeApple();
        snakeManager.setAlive(true);
        IAIteration iaIteration = getIaIteration(winner.get(0));
        iaIteration.setSnakeManager(snakeManager);
        list.put(iaIteration,snakeManager);

        snakeManager = new SnakeManager(this,getIaIteration(winner.get(1)));
        snakeManager.createSnake();
        snakeManager.initilizeApple();
        snakeManager.setAlive(true);
        iaIteration = getIaIteration(winner.get(1));
        iaIteration.setSnakeManager(snakeManager);
        list.put(iaIteration,snakeManager);


        managers = list;

        setStarted(true);

    }


    public void createFirstGeneration(int i){

        generationCOunt++;
        setSnakeQuantity(i);
        setSnakeAliveQuantity(i);

        while (i != 0){

            IAIteration iaIteration = new IAIteration(this,iterationcount);
            iaIteration.createIA(true,null);

            SnakeManager snakeManager = new SnakeManager(this,iaIteration);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.setAlive(true);
            //snakeManager.startRunnable();
            iaIteration.setSnakeManager(snakeManager);

            managers.put(iaIteration,snakeManager);

            iterationcount++;

            i--;

        }

        setStarted(true);
        startGlobalRunnale();

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
