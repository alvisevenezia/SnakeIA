package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class ComputeLayer extends Layer{

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private float bias;
    private HashMap<Integer,float[]>weights;

    public ComputeLayer(int size,IAIteration iaIteration){

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

    public void compute(){

        float[] input = iaIteration.getLayer(iaIteration.getLayerID(this)).getOutput();

        for(int i = 0;i < size;i++){

            float out = 0;

            for(int i2 = 0;i2 < iaIteration.getLayer(iaIteration.getLayerID(this)).getSize();i2++) {

                out = out+input[i2]*weights.get(i)[i2];

            }

            out = out+bias;

            output[i] = out;

            if(out > 0){

                output[i] = out;

            }else{

                output[i] = 0;

            }
        }

    }

    public float[] getWeights(int id){

        return weights.get(id);

    }


    public void generateRandomWeights() {

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

    public void mergeWeight(ComputeLayer computeLayer1,ComputeLayer computeLayer2){

        float[] w = new float[computeLayer1.getSize()];
        int splitId = iaIteration.getGlobalManager().getRandom().nextInt(computeLayer1.getSize());

        if(iaIteration.getGlobalManager().getRandom().nextInt(100) == 0){

            int mutAmount = iaIteration.getGlobalManager().getRandom().nextInt(300);

            for(int i = 0;i<mutAmount;i++){

                int mutID = iaIteration.getGlobalManager().getRandom().nextInt((577));

                if(mutID == 576){

                    if(iaIteration.getGlobalManager().getRandom().nextBoolean()){

                        computeLayer1.setBias(iaIteration.getGlobalManager().getRandom().nextFloat()*((float)(iaIteration.getGlobalManager().getRandom().nextBoolean()? 1 : -1)));
                    }else{

                        computeLayer2.setBias(iaIteration.getGlobalManager().getRandom().nextFloat()*((float)(iaIteration.getGlobalManager().getRandom().nextBoolean()? 1 : -1)));

                    }

                }else {
                    int neuronID = mutID / 24;
                    int weightToMuteID = neuronID % 24;
                    float mutedWeight = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

                    if (iaIteration.getGlobalManager().getRandom().nextBoolean()) {

                        computeLayer1.getWeights(neuronID)[weightToMuteID] = mutedWeight;

                    } else {

                        computeLayer2.getWeights(neuronID)[weightToMuteID] = mutedWeight;

                    }
                }

            }

        }

        for(int weightID = 0;weightID<computeLayer1.getSize();weightID++) {

            for (int i = 0; i < computeLayer1.getSize(); i++) {

                if (i > splitId) {

                    w[i] = computeLayer1.getWeights(weightID)[i];

                } else {

                    w[i] = computeLayer2.getWeights(weightID)[i];

                }

            }

            weights.put(weightID, w);
        }
        if(iaIteration.getGlobalManager().getRandom().nextBoolean())bias = computeLayer1.getBias();
        else bias = computeLayer2.getBias();


    }

    public float[] getOutput(){

        return output;

    }

    @Override
    public int getSize() {
        return size;
    }
}
