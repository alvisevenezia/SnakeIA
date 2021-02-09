package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class FirstLayer extends Layer{

    private int size;
    private float[] weights;
    private float[] output;
    private float[] bias;
    private IAIteration iaIteration;

    public FirstLayer(int size, IAIteration iaIteration){

        this.size = size;
        this.iaIteration = iaIteration;
        weights = new float[size];
        bias = new float[size];
        output = new float[size];

    }

    public float[] getBias() {
        return bias;
    }

    @Override
    public HashMap<Integer, float[]> geWeights() {
        return null;
    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    public void compute(){

        float[] input = iaIteration.getSnakeInfo(iaIteration.getSnakeManager().getHeadPos()[0],iaIteration.getSnakeManager().getHeadPos()[1]);

        for(int i  = 0;i <size;i++) {

            output[i] = input[i]*weights[i];
        }

    }

    @Override
    public float[] getOutput() {
        return output;
    }

    public float[] getWeights(){

        return weights;

    }

    public float getWeight(int i){

        return weights[i];
    }

    public void setWeight(int i,float f){

        weights[i] = f;

    }

    public void mergeWeights(FirstLayer firstLayer1,FirstLayer firstLayer2){

        int splitID = iaIteration.getGlobalManager().getRandom().nextInt(firstLayer1.getSize());

        for(int ID = 0;ID<firstLayer1.getSize();ID++){

            if(ID<splitID){

                weights[ID] = firstLayer1.getWeights()[ID];
                bias[ID] = firstLayer1.getBias()[ID];
            }
            else {

                weights[ID] = firstLayer2.getWeights()[ID];
                bias[ID] = firstLayer2.getBias()[ID];

            }

        }

        if(iaIteration.getGlobalManager().getRandom().nextInt(iaIteration.getGlobalManager().getQuantity())<iaIteration.getGlobalManager().getQuantity()*(iaIteration.getGlobalManager().getMutationRate()/100)){

            int neuronID = iaIteration.getGlobalManager().getRandom().nextInt((firstLayer1.getWeights().length*2)-1);

            if(neuronID <firstLayer1.getWeights().length) {

                weights[neuronID] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;

            }else{

                bias[neuronID%firstLayer1.getWeights().length] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;
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
