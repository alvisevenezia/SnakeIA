package fr.alvisevenezia.IA.NN.layers;

import fr.alvisevenezia.IA.IAIteration;
import fr.alvisevenezia.IA.NN.weights.SimpleWeightsData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class FirstLayer extends Layer<FirstLayer, SimpleWeightsData> {

    private int size;
    private SimpleWeightsData weights;
    private float[] output;
    private float[] bias;
    private IAIteration iaIteration;

    public FirstLayer(int size, IAIteration iaIteration){

        this.size = size;
        this.iaIteration = iaIteration;
        weights = new SimpleWeightsData(new ArrayList<>(Collections.nCopies(size,0.0f)));
        bias = new float[size];
        output = new float[size];

    }

    @Override
    public float[] getBias() {
        return bias;
    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    @Override
    public void compute(){

        float[] input = iaIteration.getSnakeInfo(iaIteration.getSnakeManager().getHeadPos()[0],iaIteration.getSnakeManager().getHeadPos()[1]);

        for(int i  = 0;i <size;i++) {

            output[i] = input[i]*weights.getAt(i);
        }

    }

    @Override
    public float[] getOutput() {
        return output;
    }

    @Override
    public SimpleWeightsData getWeights(){

        return weights;

    }

    public Float getWeights(int id) {
        return weights.getAt(id);
    }

    public void setWeight(int i,float f){

        weights.setAt(i,f);

    }

    @Override
    public void mergeWeights(FirstLayer firstLayer1,FirstLayer firstLayer2){

        int splitID = iaIteration.getGlobalManager().getRandom().nextInt(firstLayer1.getSize());

        for(int ID = 0;ID<firstLayer1.getSize();ID++){

            if(ID<splitID){

                weights.setAt(ID,firstLayer1.getWeights().getAt(ID));
                bias[ID] = firstLayer1.getBias()[ID];
            }
            else {

                weights.setAt(ID,firstLayer2.getWeights().getAt(ID));
                bias[ID] = firstLayer2.getBias()[ID];

            }

        }

        if(iaIteration.getGlobalManager().getRandom().nextInt(iaIteration.getGlobalManager().getQuantity())<iaIteration.getGlobalManager().getQuantity()*(iaIteration.getGlobalManager().getMutationRate()/100)){

            int neuronID = iaIteration.getGlobalManager().getRandom().nextInt((firstLayer1.getWeights().length()*2)-1);

            if(neuronID <firstLayer1.getWeights().length()) {

                weights.setAt(neuronID,(iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1);

            }else{

                bias[neuronID%firstLayer1.getWeights().length()] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;
            }
        }

     /*   float w;
        int splitId = iaIteration.getGlobalManager().getRandom().nextInt(firstLayer1.size);

        if(iaIteration.getGlobalManager().getRandom().nextInt(150) == 0){

            int mutAmount = iaIteration.getGlobalManager().getRandom().nextInt(4);

            for(int i = 0;i<mutAmount;i++){

                int mutID = iaIteration.getGlobalManager().getRandom().nextInt(24);
                float mutedWeight = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

                if (iaIteration.getGlobalManager().getRandom().nextBoolean()) {

                    firstLayer1.getWeights()[mutID] = mutedWeight;

                } else {

                    firstLayer1.getWeights()[mutID] = mutedWeight;

                }

            }

        }

        for(int i = 0;i<firstLayer1.getWeights().length;i++) {

            if(i> splitId){

                w = firstLayer1.getWeights()[i];

            }else{

                w = firstLayer2.getWeights()[i];

            }

            setWeight(i,w);

        }*/

    }

    @Override
    public void generateRandomWeights(){

        Random r = new Random();

        for(int i = 0;i<size;i++){

            setWeight(i,(iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1);

        }



    }

    @Override
    public int getSize() {
        return size;
    }
}
