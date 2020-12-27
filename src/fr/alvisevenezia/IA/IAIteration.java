package fr.alvisevenezia.IA;

import fr.alvisevenezia.IA.NN.ComputeLayer;
import fr.alvisevenezia.IA.NN.DecisionLayer;
import fr.alvisevenezia.IA.NN.FirstLayer;
import fr.alvisevenezia.IA.NN.Layer;
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

    public IAIteration(GlobalManager globalManager,int i){

        this.globalManager = globalManager;
        this.iterationnbr = i;
        layers = new ArrayList<>();

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

    public void createIA(boolean first){

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

           ArrayList<IAIteration>ias = globalManager.getIaIterations(globalManager.getWinner());

            Random r = new Random();
            IAIteration ia1 = ias.get(r.nextInt(10));
            IAIteration ia2 = ias.get(r.nextInt(10));

            FirstLayer firstLayer = (FirstLayer) getLayer(0);
            firstLayer.mergeWeights(((FirstLayer)ia1.getLayer(0)).getWeights(),((FirstLayer)ia2.getLayer(0)).getWeights(),true);
            addLayer(firstLayer);

            for(int id = 0;id<24;id++) {

                ComputeLayer computeLayer1 = (ComputeLayer) getLayer(1);
                computeLayer1.mergeWeight(id,((ComputeLayer) ia1.getLayer(1)).getWeights(id), ((ComputeLayer) ia2.getLayer(1)).getWeights(id), true);
                addLayer(computeLayer1);
            }

            for(int id = 0;id<24;id++) {

                ComputeLayer computeLayer2= (ComputeLayer) getLayer(2);
                computeLayer2.mergeWeight(id,((ComputeLayer) ia1.getLayer(2)).getWeights(id), ((ComputeLayer) ia2.getLayer(2)).getWeights(id), true);
                addLayer(computeLayer2);
            }

            for(int id = 0;id<24;id++) {

                DecisionLayer decisionLayer= (DecisionLayer) getLayer(3);
                decisionLayer.mergeWeight(id,((DecisionLayer) ia1.getLayer(3)).getWeights(id), ((DecisionLayer) ia2.getLayer(3)).getWeights(id), true);
                addLayer(decisionLayer);
            }

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

    public float[] getSnakeInfo(int i,int i2){//i --> posX i2 --> posY

        float[] data = new float[24];
        int count = 0;
        //data:
        //distance bordure
        //distance queue
        //distance pomme la plus proche

        for(int i4 = -1;i4<2;i4++){

            for(int i3 = -1;i3<2;i3++){

                if(!(i3 == 0 && i4 == 0)){

                   /* if(i-i3 < 0 || i-i3 >49 || i2-i4 < 0 || i2-i4 >49){

                            data[count*3] = 0;

                    }else{

                        if(i-i3 > 25){

                            if(i-i4 > 25){

                                data[count*3] = (int)Math.sqrt(((50-(i-i3))*(50-(i-i3)))+((50-(i-i4))*(50-(i-i4))));

                            }else{

                                data[count*3] = (int)Math.sqrt(((50-(i-i3))*(50-(i-i3)))+((-(i-i4))*(-(i-i4))));
                            }

                        }else{

                            if(i-i4 > 25){

                                data[count*3] = (int)Math.sqrt(((-(i-i3))*(-(i-i3)))+((50-(i-i4))*(50-(i-i4))));

                            }else{

                                data[count*3] = (int)Math.sqrt(((-(i-i3))*(-(i-i3)))+((-(i-i4))*(-(i-i4))));
                            }
                        }

                    }*/

                    data[count*3] = getBorderDistance(i-i3,i2-i4);

                    float[] qpos = snakeManager.getQueuePos();
                    float qd = (float)Math.sqrt(((i-qpos[0])*(i-qpos[0]))+((i2-qpos[1])*(i2-qpos[1])));

                    data[(count*3)+1] = qd;

                    data[(count*3)+2] = snakeManager.getAppleDistance(i-i3,i2-i4);

                  count++;
                }

            }

        }

        return data;

    }




}
