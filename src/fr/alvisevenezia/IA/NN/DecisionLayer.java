package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class DecisionLayer extends Layer{

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private float bias;
    private HashMap<Integer,float[]> weights;

    public DecisionLayer(int size,IAIteration iaIteration){

        this.iaIteration = iaIteration;
        this.size = size;
        output = new float[size];
        weights = new HashMap<>();

    }

    public float getBias() {
        return bias;
    }

    public void setBias(float bias) {
        this.bias = bias;
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

            weights.put(i,w);
        }

        bias = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

    }

    public void mergeWeight(DecisionLayer decisionLayer1,DecisionLayer decisionLayer2){

        float[] w = new float[decisionLayer1.getSize()];
        int splitId = iaIteration.getGlobalManager().getRandom().nextInt(decisionLayer1.getSize());

        if(iaIteration.getGlobalManager().getRandom().nextInt(100) == 0) {

            int mutAmount = iaIteration.getGlobalManager().getRandom().nextInt(15);

            for (int i = 0; i < mutAmount; i++) {

                int mutID = iaIteration.getGlobalManager().getRandom().nextInt((97));

                if (mutID == 96){

                    if (iaIteration.getGlobalManager().getRandom().nextBoolean()) {

                        decisionLayer1.setBias(iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1)));
                    } else {

                        decisionLayer2.setBias(iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1)));

                    }

                }else {
                    int neuronID = mutID / 24;
                    int weightToMuteID = neuronID % 24;
                    float mutedWeight = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

                    if (iaIteration.getGlobalManager().getRandom().nextBoolean()) {

                        decisionLayer1.getWeights(neuronID)[weightToMuteID] = mutedWeight;

                    } else {

                        decisionLayer2.getWeights(neuronID)[weightToMuteID] = mutedWeight;

                    }
                }
            }
        }

        for(int weightID = 0;weightID<decisionLayer1.getSize();weightID++) {

            for (int i = 0; i < decisionLayer1.getSize(); i++) {

                if (i > splitId) {

                    w[i] = decisionLayer1.getWeights(weightID)[i];

                } else {

                    w[i] = decisionLayer2.getWeights(weightID)[i];

                }

            }

            weights.put(weightID, w);
        }
        if(iaIteration.getGlobalManager().getRandom().nextBoolean())bias = decisionLayer1.getBias();
        else bias = decisionLayer2.getBias();
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
    public int getSize() {
        return size;
    }

    @Override
    public float[] getOutput() {
        return output;
    }
}

