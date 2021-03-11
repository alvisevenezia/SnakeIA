package fr.alvisevenezia.IA.RL;

import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;
import fr.alvisevenezia.SNAKE.SnakeMouvement;

import java.util.HashMap;
import java.util.Random;

public class ReinforcementLearningManager {

    private GlobalManager globalManager;
    private NeuronalNetworkManager neuronalNetworkManager;
    private Random random;
    private HashMap<Integer,int[]> appleCoord;

    private int size;

    public ReinforcementLearningManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
        size = globalManager.getSize();
        random = new Random();
    }

    public ReinforcementLearningManager(GlobalManager globalManager, NeuronalNetworkManager neuronalNetworkManager) {
        this.globalManager = globalManager;
        this.neuronalNetworkManager = neuronalNetworkManager;
        size = globalManager.getSize();
        random = new Random();
    }

    public void createIA(){

        neuronalNetworkManager = new NeuronalNetworkManager(4,2,globalManager.getLayersSize(),globalManager);
        neuronalNetworkManager.createLayers();
        neuronalNetworkManager.randomInitializeLayers();

    }

    public void generateApple(){

        for(int i = 0;i <15000;i++){

            int[] apple = {random.nextInt(globalManager.getSize()-2)+1,random.nextInt(globalManager.getSize()-2)+1};

            appleCoord.put(i,apple);

        }

    }

    public GlobalManager getGlobalManager() {
        return globalManager;
    }

    public void setGlobalManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    public NeuronalNetworkManager getNeuronalNetworkManager() {
        return neuronalNetworkManager;
    }

    public void setNeuronalNetworkManager(NeuronalNetworkManager neuronalNetworkManager) {
        this.neuronalNetworkManager = neuronalNetworkManager;
    }

    public SnakeMouvement getBestMouvement(){

        SnakeMouvement bestMouvement = null;
        int currentbestRLScore = 1500;

        for(SnakeMouvement snakeMouvement : SnakeMouvement.values()){

            neuronalNetworkManager.getSnakeManager().moovSnake(snakeMouvement);

            if(neuronalNetworkManager.getSnakeManager().isAlive()) {

                int rlscore = computeRLScore(neuronalNetworkManager.getSnakeManager().getSnakeMatrix(), appleCoord.get(neuronalNetworkManager.getSnakeManager().getAppleID())[0], appleCoord.get(neuronalNetworkManager.getSnakeManager().getAppleID())[1]);

                if (rlscore < currentbestRLScore) {

                    currentbestRLScore = rlscore;
                    bestMouvement = snakeMouvement;

                }

            }
            neuronalNetworkManager.getSnakeManager().resetMvt();

        }

        return bestMouvement;
    }

    public int computeRLScore(int[][] snake,int xApple,int yApple){

        int score = 0;

        if(neuronalNetworkManager.getSnakeManager().getCurrentbody() != neuronalNetworkManager.getSnakeManager().getNewbody()){

            score = 0;

        }else{

            score = (int) Math.sqrt((neuronalNetworkManager.getSnakeManager().getNewHeadPos()[0]-xApple)*(neuronalNetworkManager.getSnakeManager().getNewHeadPos()[0]-xApple)+(neuronalNetworkManager.getSnakeManager().getNewHeadPos()[1]-yApple)*(neuronalNetworkManager.getSnakeManager().getNewHeadPos()[1]-yApple));

        }

        return score;

    }
}

