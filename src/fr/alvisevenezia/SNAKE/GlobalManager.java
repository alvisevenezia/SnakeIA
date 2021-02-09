package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.MainGUI;
import fr.alvisevenezia.IA.CSV.CSVBuilder;
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
    private HashMap<Integer,int[]>appleCoord;
    private int iterationcount = 0;
    private int snakeQuantity;
    private int snakeAliveQuantity;
    private int generationCOunt = 0;
    private int appledCount = 0;
    private final double mutationRate = 0.5;
    private final double crossOverRate = 95;
    private final int quantity = 2000;
    private final String path = "C:\\Users\\wanto\\OneDrive\\Documents\\DEV\\IA\\SnakeIA";
    private Timer runnable;
    private ArrayList<SnakeManager> winner;
    private Random random;

    public GlobalManager(){

        mainGUI = new MainGUI(this);
        managers = new HashMap<>();
        appleCoord = new HashMap<>();
        currentFrame = mainGUI;
        mainGUI.openFrame();
        random = new Random();
        generateApple();
    }

    public void setAppleCoord(HashMap<Integer, int[]> appleCoord) {
        this.appleCoord = appleCoord;
    }

    public HashMap<Integer, int[]> getAppleCoords() {
        return appleCoord;
    }

    public int[] getAppleCoord(int id){

        return appleCoord.get(id);

    }

    public void clearApple(){

        appleCoord.clear();

    }

    public void generateApple(){

        for(int i = 0;i <15000;i++){

            int[] apple = {random.nextInt(48)+1,random.nextInt(48)+1};

            appleCoord.put(i,apple);

        }

    }

    public boolean isApple(int ID,int x, int y){

        if(appleCoord.get(ID)[0] == x && appleCoord.get(ID)[1] == y){

            return true;

        }

        return false;

    }

    public int getAppledCount() {
        return appledCount;
    }

    public void setAppledCount(int appledCount) {
        this.appledCount = appledCount;
    }

    public void removeApple(int x,int y){

        for(IAIteration iaIteration : managers.keySet()){

            iaIteration.getSnakeManager().setApple(x,y,0);

        }

        appledCount --;

    }

    public int getQuantity() {
        return quantity;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossOverRate() {
        return crossOverRate;
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
        runnable.schedule(new GlobalRunnable(this),100,100);

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
    public ArrayList<SnakeManager> getBestSnakesAlives(int amount){

        ArrayList<SnakeManager> bests = new ArrayList<>();
        ArrayList<SnakeManager> snakemanagers =  new ArrayList<>(managers.values());

        for(int i = 0;i < Math.min(amount,snakeAliveQuantity);i++){

            SnakeManager best = null;
            int score = 0;

            int max = -1;

            for(SnakeManager m : snakemanagers){

                if(m.calculateFitness() > max && m.isAlive()){

                    max = m.calculateFitness();
                    best = m;

                }

            }

            bests.add(best);
            snakemanagers.remove(best);

        }

        return bests;
    }

    public ArrayList<SnakeManager> getBestSnakes(int amount){

        ArrayList<SnakeManager> bests = new ArrayList<>();
        ArrayList<SnakeManager> snakemanagers =  new ArrayList<>(managers.values());

        for(int i = 0;i < amount;i++){

            SnakeManager best = null;
            int score = 0;

            int max = -1;

            for(SnakeManager m : snakemanagers){

                if(m.calculateFitness() > max){

                    max = m.calculateFitness();
                    best = m;

                }

            }

            bests.add(best);
            snakemanagers.remove(best);

        }

        return bests;
    }

    public SnakeManager getBestSnake(){

        int max = -1;
        SnakeManager best = null;

        for(SnakeManager snakeManager : managers.values()){


            if(snakeManager.calculateFitness() > max){

                max = snakeManager.calculateFitness();
                best = snakeManager;

            }

        }

        return best;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            JButton button = (JButton) e.getSource();

            switch (button.getText()){

                case "Lancer Snake":

                    button.setText("Arreter IA");
                    createFirstGeneration(quantity);
                    mainGUI.getSnake().repaint();
                    break;

                case "Arreter IA":

                    button.setText("Lancer Snake");
                    stopRunnables();
                    stopGlobalRunnale();
                    break;

                case "Enregistrer Generation":

                    CSVBuilder csvBuilder = new CSVBuilder(null,path+"\\"+getBestSnake().calculateFitness(),new ArrayList<IAIteration>(managers.keySet()),this);
                    csvBuilder.buildFile();

                    break;


                default:

                    break;

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

        clearApple();
        generateApple();

        setStarted(false);

        ArrayList<IAIteration>ias = getIaIterations(getBestSnakes(10));
        HashMap<IAIteration, SnakeManager>list = new HashMap<>();

        setSnakeQuantity(quantite);
        setSnakeAliveQuantity(quantite);

        ArrayList<IAIteration>randomIAs = new ArrayList<>();

        for(int i = 0;i<quantite-12;i++){

            randomIAs.add(ias.get(random.nextInt(10)));
            randomIAs.add(ias.get(random.nextInt(10)));

            IAIteration iaIteration = new IAIteration(this, iterationcount);
            iaIteration.createIA(false, randomIAs);
            SnakeManager snakeManager = new SnakeManager(this, iaIteration);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.setAlive(true);
            snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
         //   snakeManager.randomGenerateApple();
            iaIteration.setSnakeManager(snakeManager);
            list.put(iaIteration, snakeManager);
            iterationcount++;

            randomIAs.clear();
        }

        for(int i = 0;i<10;i++){

            IAIteration iaIteration = new IAIteration(this,iterationcount);
            iaIteration.createIA(true,null);
            SnakeManager snakeManager = new SnakeManager(this,iaIteration);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.setAlive(true);
            snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
        //    snakeManager.randomGenerateApple();
            iaIteration.setSnakeManager(snakeManager);

            list.put(iaIteration,snakeManager);

            iterationcount++;

        }

        SnakeManager snakeManager = new SnakeManager(this,getIaIteration(winner.get(0)));
        snakeManager.createSnake();
        snakeManager.initilizeApple();
        snakeManager.setAlive(true);
      //  snakeManager.randomGenerateApple();
        snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
        IAIteration iaIteration = getIaIteration(winner.get(0));
        iaIteration.setSnakeManager(snakeManager);
        list.put(iaIteration,snakeManager);

        snakeManager = new SnakeManager(this,getIaIteration(winner.get(1)));
        snakeManager.createSnake();
        snakeManager.initilizeApple();
        snakeManager.setAlive(true);
      //  snakeManager.randomGenerateApple();
        snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
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
           // snakeManager.randomGenerateApple();
            snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
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

    public int getMoyenne() {

        int moy = 1;
        int count = 1;

        for(SnakeManager snakeManager : managers.values()){

            if(snakeManager.isAlive()){

                moy += snakeManager.getScore();
                count ++;

            }

        }

        return moy/count;

    }
}
