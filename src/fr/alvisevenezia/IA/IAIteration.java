package fr.alvisevenezia.IA;

import fr.alvisevenezia.IA.NN.ComputeLayer;
import fr.alvisevenezia.IA.NN.DecisionLayer;
import fr.alvisevenezia.IA.NN.FirstLayer;
import fr.alvisevenezia.IA.NN.Layer;
import fr.alvisevenezia.SNAKE.Direction;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;
import fr.alvisevenezia.SNAKE.SnakeMouvement;

import java.util.ArrayList;
import java.util.Random;

public class IAIteration {

    private GlobalManager globalManager;
    private int iterationnbr;
    private SnakeManager snakeManager;
    private int firstLayerSize;
    private int secondLayerSize;

    private ArrayList<Layer>layers;

    public int getIterationnbr() {
        return iterationnbr;
    }

    public GlobalManager getGlobalManager() {
        return globalManager;
    }

    public IAIteration(GlobalManager globalManager, int i){

        this.globalManager = globalManager;
        this.iterationnbr = i;
        layers = new ArrayList<>();

    }

    public ArrayList<String> getFloat(){

        ArrayList<String> val = new ArrayList<>();

        for(int ID = 0; ID < layers.get(0).getSize();ID++){

            val.add(String.valueOf(layers.get(0).getWeights()[ID]));
            val.add(String.valueOf(layers.get(0).getBias()[ID]));

        }

        for(int ID = 0; ID < layers.get(1).getSize();ID++){

            for(int weightID = 0; weightID < layers.get(1).geWeights().get(ID).length;weightID++){

                val.add(String.valueOf(layers.get(1).geWeights().get(ID)[weightID]));

            }

            val.add(String.valueOf(layers.get(1).getBias()[ID]));

        }

        for(int ID = 0; ID < layers.get(2).getSize();ID++){

            for(int weightID = 0; weightID < layers.get(2).geWeights().get(ID).length;weightID++){

                val.add(String.valueOf(layers.get(2).geWeights().get(ID)[weightID]));

            }

            val.add(String.valueOf(layers.get(2).getBias()[ID]));

        }

        for(int ID = 0; ID < layers.get(3).getSize();ID++){

            for(int weightID = 0; weightID < layers.get(3).geWeights().get(ID).length;weightID++){

                val.add(String.valueOf(layers.get(3).geWeights().get(ID)[weightID]));

            }

            val.add(String.valueOf(layers.get(1).getBias()[ID]));

        }


        return val;
    }

    public SnakeMouvement getMouvement(){

        DecisionLayer layer = (DecisionLayer) getLayer(layers.size()-1);

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

    public void createIA(boolean first,ArrayList<IAIteration> ias){

        if(first){

            FirstLayer firstLayer = new FirstLayer(24,this);
            firstLayer.generateRandomWeights();
            addLayer(firstLayer);

            ComputeLayer computeLayer1 = new ComputeLayer(24,this);
            computeLayer1.generateRandomWeights();
            addLayer(computeLayer1);

            ComputeLayer computeLayer2 = new ComputeLayer(24,this);
            computeLayer2.generateRandomWeights();
            addLayer(computeLayer2);

            DecisionLayer decisionLayer = new DecisionLayer(4,this);
            decisionLayer.generateRamdomWeights();
            addLayer(decisionLayer);
        }else{

            Random r = new Random();
            IAIteration ia1 = ias.get(0);
            IAIteration ia2 = ias.get(1);

            FirstLayer firstLayer = new FirstLayer(24,this);
            firstLayer.mergeWeights((FirstLayer)ia1.getLayer(0),(FirstLayer)ia2.getLayer(0));
            addLayer(firstLayer);

            ComputeLayer computeLayer1 = new ComputeLayer(24,this);
            computeLayer1.mergeWeight((ComputeLayer) ia1.getLayer(1), (ComputeLayer) ia2.getLayer(1));
            addLayer(computeLayer1);

            ComputeLayer computeLayer2= new ComputeLayer(24,this);
            computeLayer2.mergeWeight((ComputeLayer) ia1.getLayer(1), (ComputeLayer) ia2.getLayer(1));
            addLayer(computeLayer2);

            DecisionLayer decisionLayer  = new DecisionLayer(4,this);
            decisionLayer.mergeWeight((DecisionLayer) ia1.getLayer(3), (DecisionLayer) ia2.getLayer(3));
            addLayer(decisionLayer);

        }

    }

    public Layer getLayer(int id){

        return layers.get(id);

    }

    public int getLayerID(Object o){

        int i = 0;

        for(Object o1 : layers){

            if(o == o1 ){


                return i;
            }

            i++;
        }

        return 0;
    }
    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public void addLayer(Layer layer){

        layers.add(layer);

    }

    public void setFirstLayerSize(int firstLayerSize) {
        this.firstLayerSize = firstLayerSize;
    }

    public void setSecondLayerSize(int secondLayerSize) {
        this.secondLayerSize = secondLayerSize;
    }

    public int getFirstLayerSize() {
        return firstLayerSize;
    }

    public int getSecondLayerSize() {
        return secondLayerSize;
    }

    public SnakeManager getSnakeManager() {

        return snakeManager;
    }

    public void setSnakeManager(SnakeManager snakeManager){

        this.snakeManager = snakeManager;

    }

    public int getBorderDistance(int i,int i2){

        if(i  == 50||i2==50||i==-1||i2==-1)return 100;

        int d1 = 0;
        int d2 = 0;

        if(i<25) d1 = i+1;

        else d1 = 50 - i;

        if(i2<25)d2 = i2+1;

        else d2 = 50 - i2;

        if(d2 < d1) d1 = d2;

        return d1;

    }

    public float[] getSnakeInfo(int x,int y){

        float[] data = new float[24];
        //data:
        //distance bordure
        //distance queue
        //distance pomme la plus proche

        data[0] = snakeManager.getBordureDistance(x,y,Direction.LEFT_UP);
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




}
