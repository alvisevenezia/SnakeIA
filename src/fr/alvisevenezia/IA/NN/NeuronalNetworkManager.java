package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.NN.layers.ComputeLayer;
import fr.alvisevenezia.IA.NN.layers.DecisionLayer;
import fr.alvisevenezia.IA.NN.layers.FirstLayer;
import fr.alvisevenezia.IA.NN.layers.Layer;
import fr.alvisevenezia.SNAKE.Direction;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;
import fr.alvisevenezia.SNAKE.SnakeMouvement;

import java.util.ArrayList;
import java.util.Random;

public class NeuronalNetworkManager {

    private int hiddenLayers;
    private int size;
    private int[] layersSize;
    private GlobalManager globalManager;
    private ArrayList<Layer> layersList;
    private SnakeManager snakeManager;

    public NeuronalNetworkManager(int size,int hiddenLayers, int[] layersSize, GlobalManager globalManager) {
        this.hiddenLayers = hiddenLayers;
        this.size = size;
        this.layersSize = layersSize;
        this.globalManager = globalManager;
        layersList = new ArrayList<>();

    }

    public NeuronalNetworkManager(int hiddenLayers, int size, GlobalManager globalManager) {
        this.hiddenLayers = hiddenLayers;
        this.size = size;
        this.globalManager = globalManager;
    }

    public NeuronalNetworkManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    public SnakeManager getSnakeManager() {
        return snakeManager;
    }

    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }

    public int[] getLayersSize() {
        return layersSize;
    }

    public void setLayersSize(int[] layersSize) {
        this.layersSize = layersSize;
    }

    public void createLayers(){

        FirstLayer firstLayer = new FirstLayer(layersSize[0],this);
        layersList.add(firstLayer);

        for(int layerID = 1;layerID<size-1;layerID++){

            ComputeLayer computeLayer = new ComputeLayer(layersSize[layerID],this);
            layersList.add(computeLayer);
        }

        DecisionLayer decisionLayer = new DecisionLayer(layersSize[size-1],this);
        layersList.add(decisionLayer);

    }

    public void createIA(boolean first,ArrayList<NeuronalNetworkManager> ias){

        createLayers();

        if(first){

            randomInitializeLayers();

        }else{

            Random r = new Random();
            NeuronalNetworkManager ia1 = ias.get(0);
            NeuronalNetworkManager ia2 = ias.get(1);

            mergeWeights(ia1,ia2);
        }

    }

    public void randomInitializeLayers(){

        for(int layerID = 0;layerID<size;layerID++){

            layersList.get(layerID).generateRandomWeights();

        }

    }

    public GlobalManager getGlobalManager() {
        return globalManager;
    }

    public int getSize() {
        return size;
    }

    public Layer getLayer(int id){

        return layersList.get(id);

    }

    public void mergeWeights(NeuronalNetworkManager neuronalNetworkManager1,NeuronalNetworkManager neuronalNetworkManager2){

        for(int layerID = 0;layerID<size;layerID++){

            layersList.get(layerID).mergeWeights(neuronalNetworkManager1.getLayer(layerID),neuronalNetworkManager2.getLayer(layerID));


        }

    }

    public int getLayerID(Layer layer){

        for(int layerID = 0;layerID<size;layerID++){

            if(layersList.get(layerID) == layer){

                return layerID;

            }

        }

        return -1;

    }

    public ArrayList<String> getFloat(){

        ArrayList<String> val = new ArrayList<>();

        for(int ID = 0; ID < getLayersSize()[0];ID++){

            val.add(String.valueOf(getLayer(0).getWeights().getFloat(ID,0)));
            val.add(String.valueOf(getLayer(0).getBias()[ID]));

        }

        for(int ID = 0; ID < getLayer(1).getSize();ID++){

            for(int weightID = 0; weightID < getLayersSize()[1];weightID++){

                val.add(String.valueOf(getLayer(1).getWeights().getFloat(ID,weightID)));

            }

            val.add(String.valueOf(getLayer(1).getBias()[ID]));

        }

        for(int ID = 0; ID < getLayer(2).getSize();ID++){

            for(int weightID = 0; weightID < getLayersSize()[2];weightID++){

                val.add(String.valueOf(getLayer(2).getWeights().getFloat(ID,weightID)));

            }

            val.add(String.valueOf(getLayer(2).getBias()[ID]));

        }

        for(int ID = 0; ID < getLayer(3).getSize();ID++){

            for(int weightID = 0; weightID < getLayersSize()[3];weightID++){

                val.add(String.valueOf(getLayer(3).getWeights().getFloat(ID,weightID)));

            }

            val.add(String.valueOf(getLayer(3).getBias()[ID]));

        }

        return val;
    }

    public SnakeMouvement getMouvement(){

        Layer layer = getLayer(getSize()-1);

        float[] input = layer.getOutput();

        if(input[0] > input[1] && input[0] > input[2] && input[0] > input[3]){

            return SnakeMouvement.LEFT;

        }else if(input[1] > input[0] && input[1] > input[2] && input[1] > input[3]){

            return SnakeMouvement.UP;

        }else if(input[2] > input[0] && input[2] > input[1] && input[2] > input[3]){

            return SnakeMouvement.RIGHT;

        }else if(input[3] > input[0] && input[3] > input[1] && input[3] > input[2]){

            return SnakeMouvement.DOWN;

        }

        return SnakeMouvement.UP;

    }

    public float[] getSnakeInfo(int x,int y){

        float[] data = new float[24];
        //data:
        //distance bordure
        //distance queue
        //distance pomme la plus proche

        data[0] = snakeManager.getBordureDistance(x,y, Direction.LEFT_UP);
        data[1] = snakeManager.getBodyDistance(x,y,Direction.LEFT_UP);
        data[2] = snakeManager.getAppleDistance(x,y,Direction.LEFT_UP);
        data[3] = snakeManager.getBordureDistance(x,y,Direction.UP);
        data[4] = snakeManager.getBodyDistance(x,y,Direction.UP);
        data[5] = snakeManager.getAppleDistance(x,y,Direction.UP);
        data[6] = snakeManager.getBordureDistance(x,y,Direction.RIGHT_UP);
        data[7] = snakeManager.getBodyDistance(x,y,Direction.RIGHT_UP);
        data[8] = snakeManager.getAppleDistance(x,y,Direction.RIGHT_UP);
        data[9] = snakeManager.getBordureDistance(x,y,Direction.RIGHT);
        data[10] = snakeManager.getBodyDistance(x,y,Direction.RIGHT);
        data[11] = snakeManager.getAppleDistance(x,y,Direction.RIGHT);
        data[12] = snakeManager.getBordureDistance(x,y,Direction.RIGHT_DOWN);
        data[13] = snakeManager.getBodyDistance(x,y,Direction.RIGHT_DOWN);
        data[14] = snakeManager.getAppleDistance(x,y,Direction.RIGHT_DOWN);
        data[15] = snakeManager.getBordureDistance(x,y,Direction.DOWN);
        data[16] = snakeManager.getBodyDistance(x,y,Direction.DOWN);
        data[17] = snakeManager.getAppleDistance(x,y,Direction.DOWN);
        data[18] = snakeManager.getBordureDistance(x,y,Direction.LEFT_DOWN);
        data[19] = snakeManager.getBodyDistance(x,y,Direction.LEFT_DOWN);
        data[20] = snakeManager.getAppleDistance(x,y,Direction.LEFT_DOWN);
        data[21] = snakeManager.getBordureDistance(x,y,Direction.LEFT);
        data[22] = snakeManager.getBodyDistance(x,y,Direction.LEFT);
        data[23] = snakeManager.getAppleDistance(x,y,Direction.LEFT);



        return data;

    }

    public ArrayList<Layer> getLayersList() {
        return layersList;
    }
}
