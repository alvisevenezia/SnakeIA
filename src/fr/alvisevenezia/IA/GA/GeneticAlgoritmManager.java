package fr.alvisevenezia.IA.GA;

import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.Thread.GACalculationThead;
import fr.alvisevenezia.SNAKE.SnakeManager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class GeneticAlgoritmManager {

    private GlobalManager globalManager;
    private HashMap<NeuronalNetworkManager, SnakeManager> managers;
    private final double mutationRate = 2;
    private final double crossOverRate = 95;
    private HashMap<Integer,int[]>appleCoord;
    private final int quantity = 2000;
    private int snakeQuantity;
    private int snakeAliveQuantity;
    private int generationCOunt = 0;
    private Thread globalThread;
    private Random random;

    public GeneticAlgoritmManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
        managers = new HashMap<>();
        appleCoord = new HashMap<>();
        random = new Random();
    }

    public GlobalManager getGlobalManager() {
        return globalManager;
    }

    public int[] getAppleCoord(int id){

        return appleCoord.get(id);

    }

    public void clearApple(){

        appleCoord.clear();

    }

    public void generateApple(){

        for(int i = 0;i <15000;i++){

            int[] apple = {random.nextInt(globalManager.getSize()-2)+1,random.nextInt(globalManager.getSize()-2)+1};

            appleCoord.put(i,apple);

        }

    }

    public Random getRandom(){
        return random;
    }

    public void addManager(NeuronalNetworkManager neuronalNetworkManager, SnakeManager snakeManager){

        managers.put(neuronalNetworkManager,snakeManager);

    }

    public int getQuantity() {
        return quantity;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setGenerationCOunt(int generationCOunt) {
        this.generationCOunt = generationCOunt;
    }

    public HashMap<NeuronalNetworkManager, SnakeManager> getManagers() {
        return managers;
    }

    public int getGenerationCOunt() {
        return generationCOunt;
    }

    public int getSnakeAliveQuantity() {
        return snakeAliveQuantity;
    }

    public void setSnakeAliveQuantity(int snakeAliveQuantity) {
        this.snakeAliveQuantity = snakeAliveQuantity;
    }

    public ArrayList<SnakeManager> getBestSnakesAlives(int amount){

        ArrayList<SnakeManager> bests = new ArrayList<>();
        ArrayList<SnakeManager> snakemanagers =  new ArrayList<>(managers.values());
        for(int i = 0;i < Math.min(amount,snakeAliveQuantity);i++){

            SnakeManager best = null;
            BigInteger fitness;
            BigInteger max = BigInteger.valueOf(-1);

            for(SnakeManager m : snakemanagers){

                fitness = m.calculateFitness();

                if(fitness.compareTo(max) == 1 && m.isAlive()){

                    max = fitness;
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
            BigInteger fitness;
            BigInteger max = BigInteger.valueOf(-1);
            int maxApple = getMostAppleEaten();

            for(SnakeManager m : snakemanagers){

                fitness = m.calculateFitness();

                if(!m.isAlive()){

                    fitness = fitness.add(BigInteger.valueOf((m.getAppleID()-maxApple)*1000));

                }

                if(fitness.compareTo(max) == 1){

                    max = fitness;
                    best = m;

                }

            }

            bests.add(best);
            snakemanagers.remove(best);

        }

        return bests;
    }

    public SnakeManager getBestSnake(){

        BigInteger max = BigInteger.valueOf(-1);
        BigInteger fitness;
        SnakeManager best = null;

        for(SnakeManager snakeManager : managers.values()){

            fitness = snakeManager.calculateFitness();

            if(fitness.compareTo(max) == 1){

                max = fitness;
                best = snakeManager;

            }

        }

        return best;

    }

    public int getMostAppleEaten() {

        int max = 0;

        for (SnakeManager snakeManager : managers.values()) {

            if (snakeManager.getAppleID() > max) {

                max = snakeManager.getAppleID();

            }

        }
        return max;

    }

    public ArrayList<NeuronalNetworkManager> getNeuronalNetworkManagers(ArrayList<SnakeManager> snakeManagers){

        Collection<NeuronalNetworkManager> manager = managers.keySet();
        ArrayList<NeuronalNetworkManager> ia = new ArrayList<>();

        for(int i = 0;i<snakeManagers.size();i++) {

            for (NeuronalNetworkManager neuronalNetworkManager : manager) {

                if (neuronalNetworkManager.getSnakeManager() == snakeManagers.get(i)) {

                    ia.add(neuronalNetworkManager);

                }

            }
        }

        return ia;

    }

    public void startIA(int quantite){

        generationCOunt++;

        clearApple();
        generateApple();

        globalManager.setStarted(false);

        ArrayList<NeuronalNetworkManager>ias = getNeuronalNetworkManagers(getBestSnakes(10));
        HashMap<NeuronalNetworkManager, SnakeManager>list = new HashMap<>();

        setSnakeQuantity(quantite);
        setSnakeAliveQuantity(quantite);

        ArrayList<NeuronalNetworkManager>randomIAs = new ArrayList<>();

        for(int i = 0;i<quantite-10;i++){

            randomIAs.add(ias.get(random.nextInt(ias.size())));
            randomIAs.add(ias.get(random.nextInt(ias.size())));


            NeuronalNetworkManager nnm = new NeuronalNetworkManager(4,2, globalManager.getLayersSize(),globalManager);
            nnm.createIA(false, randomIAs);
            SnakeManager snakeManager = new SnakeManager(this);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.setAlive(true);
            snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
            //   snakeManager.randomGenerateApple();
            nnm.setSnakeManager(snakeManager);
            list.put(nnm, snakeManager);

            randomIAs.clear();
        }

        for(int i = 0;i<10;i++){

            NeuronalNetworkManager nnm = new NeuronalNetworkManager(4,2, globalManager.getLayersSize(),globalManager);
            nnm.createIA(true,null);
            SnakeManager snakeManager = new SnakeManager(this);
            snakeManager.createSnake();
            snakeManager.initilizeApple();
            snakeManager.setAlive(true);
            snakeManager.setApple(getAppleCoord(0)[0],getAppleCoord(0)[1],1);
            //    snakeManager.randomGenerateApple();
            nnm .setSnakeManager(snakeManager);

            list.put(nnm,snakeManager);

        }

        managers = list;

        globalManager.setStarted(true);

    }

    public void createFirstGeneration(){

        setSnakeQuantity(quantity);
        setSnakeAliveQuantity(quantity);

        if(!globalManager.isLoaded()) {

            int i = quantity;

            generationCOunt++;
            NeuronalNetworkManager nnm;

            while (i >= 0) {

                nnm = new NeuronalNetworkManager(4,2, globalManager.getLayersSize(),globalManager);
                nnm.createIA(true, null);

                SnakeManager snakeManager = new SnakeManager(this);
                snakeManager.createSnake();
                snakeManager.initilizeApple();
                snakeManager.setAlive(true);
                snakeManager.setApple(getAppleCoord(0)[0], getAppleCoord(0)[1], 1);
                nnm.setSnakeManager(snakeManager);

                managers.put(nnm, snakeManager);

                i--;

            }

        }

        globalManager.setStarted(true);
        startCalculationThread();

    }

    public void startCalculationThread(){

        globalThread = new GACalculationThead(this);
        globalThread.start();
    }

    public void stopCalculationThread(){

        globalThread.stop();

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

    public boolean isApple(int appleID, int x, int y) {

        if(appleCoord.get(appleID)[0] == x && appleCoord.get(appleID)[1] == y)return true;

        return false;
    }
}
