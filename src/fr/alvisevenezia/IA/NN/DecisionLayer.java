package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class DecisionLayer extends Layer{

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private float bias[];
    private HashMap<Integer,float[]> weights;

    public DecisionLayer(int size,IAIteration iaIteration){

        this.iaIteration = iaIteration;
        this.size = size;
        output = new float[size];
        bias = new float[size];
        weights = new HashMap<>();

    }

    public float[] getBias() {
        return bias;
    }

    @Override
    public HashMap<Integer, float[]> geWeights() {
        return weights;
    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    public void setWeights(int ID,float[] val) {
        weights.put(ID,val);
    }

    public float[] getWeights(int id){

        return weights.get(id);

    }

    public void generateRamdomWeights(){

        Random r = new Random();

        for(int i = 0;i < size;i++){

            float[] w = new float[iaIteration.getLayer(iaIteration.getLayerID(this)).getSize()];

            for(int i2 = 0;i2< iaIteration.getLayer(iaIteration.getLayerID(this)).getSize();i2++){

                w[i2] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

            }

            bias[i] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

            weights.put(i,w);
        }


    }

    public void mergeWeight(DecisionLayer decisionLayer1,DecisionLayer decisionLayer2){

        int splitID = iaIteration.getGlobalManager().getRandom().nextInt(decisionLayer1.getSize());

        for(int ID = 0;ID<decisionLayer1.getSize();ID++){

            if(ID<splitID){

                weights.put(ID,decisionLayer1.getWeights(ID));
                bias[ID] = decisionLayer1.getBias()[ID];

            }
            else {

                weights.put(ID,decisionLayer2.getWeights(ID));
                bias[ID] = decisionLayer1.getBias()[ID];

            }

        }

        if(iaIteration.getGlobalManager().getRandom().nextInt(iaIteration.getGlobalManager().getQuantity())<iaIteration.getGlobalManager().getQuantity()*(iaIteration.getGlobalManager().getMutationRate()/100)){

            int neuronID = iaIteration.getGlobalManager().getRandom().nextInt((decisionLayer1.getSize()*2)-1);

            if(neuronID <decisionLayer1.getWeights(0).length) {

                float newWeights[] = new float[decisionLayer1.getWeights(0).length];

                for (int weightID = 0; weightID < decisionLayer1.getWeights(0).length; weightID++) {

                    newWeights[weightID] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

                }

                weights.replace(neuronID, newWeights);
            }else{

                bias[neuronID%decisionLayer1.getSize()] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

            }
        }

    }

    public void compute(){

        float[] input = iaIteration.getLayer(iaIteration.getLayerID(this)-1).getOutput();

        for(int i = 0;i < size;i++){

            float out = 0;

            for(int i2 = 0;i2 < (iaIteration.getLayer(iaIteration.getLayerID(this)).getSize());i2++) {

                out = out+input[i2]*weights.get(i)[i2];

            }

            if(out > 0){

                output[i] = out;

            }else{

                output[i] = 0;

            }

        }

    }

    @Override
    public float[] getWeights() {
        return new float[0];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public float[] getOutput() {
        return output;
    }
}

