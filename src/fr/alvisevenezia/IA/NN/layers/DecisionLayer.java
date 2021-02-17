package fr.alvisevenezia.IA.NN.layers;

import fr.alvisevenezia.IA.IAIteration;
import fr.alvisevenezia.IA.NN.weights.MultipleWeightsData;

import java.util.*;

public class DecisionLayer extends Layer<DecisionLayer, MultipleWeightsData> {

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private float bias[];
    private MultipleWeightsData weights;

    public DecisionLayer(int size,IAIteration iaIteration){

        this.iaIteration = iaIteration;
        this.size = size;
        output = new float[size];
        bias = new float[size];
        weights = new MultipleWeightsData(size);

    }

    @Override
    public float[] getBias() {
        return bias;
    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    public void setWeights(int ID, List<Float> val) {
        weights.setAt(ID,val);
    }

    public List<Float> getWeights(int id){

        return weights.getAt(id);

    }

    @Override
    public void generateRandomWeights(){

        Random r = new Random();

        for(int i = 0;i < size;i++){

            List<Float> w = new ArrayList<>(size);

            for(int i2 = 0;i2< iaIteration.getNeuronalNetworkManager().getLayer(iaIteration.getNeuronalNetworkManager().getLayerID(this)).getSize();i2++){

                w.add(iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1)));

            }

            bias[i] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

            weights.setAt(i,w);
        }


    }

    @Override
    public void mergeWeights(DecisionLayer decisionLayer1, DecisionLayer decisionLayer2){

        int splitID = iaIteration.getGlobalManager().getRandom().nextInt(decisionLayer1.getSize());

        for(int ID = 0;ID<decisionLayer1.getSize();ID++){

            if(ID<splitID){

                weights.setAt(ID,decisionLayer1.getWeights(ID));
                bias[ID] = decisionLayer1.getBias()[ID];

            }
            else {

                weights.setAt(ID,decisionLayer2.getWeights(ID));
                bias[ID] = decisionLayer1.getBias()[ID];

            }

        }

        if(iaIteration.getGlobalManager().getRandom().nextInt(iaIteration.getGlobalManager().getQuantity())<iaIteration.getGlobalManager().getQuantity()*(iaIteration.getGlobalManager().getMutationRate()/100)){

            int neuronID = iaIteration.getGlobalManager().getRandom().nextInt((decisionLayer1.getSize()*2)-1);

            if(neuronID <decisionLayer1.getWeights().length()) {

                List<Float> newWeights = new ArrayList<>(Collections.nCopies(size,0f));

                for (int weightID = 0; weightID < decisionLayer1.getWeights().length(); weightID++) {

                    newWeights.set(weightID, iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1)));

                }

                weights.setAt(neuronID, newWeights);

            }else{

                bias[neuronID%decisionLayer1.getSize()] = iaIteration.getGlobalManager().getRandom().nextFloat() * ((float) (iaIteration.getGlobalManager().getRandom().nextBoolean() ? 1 : -1));

            }
        }

    }

    public void compute(){

        float[] input = iaIteration.getNeuronalNetworkManager().getLayer(iaIteration.getNeuronalNetworkManager().getLayerID(this)-1).getOutput();

        for(int i = 0;i < size;i++){

            float out = 0;

            for(int i2 = 0;i2 < (iaIteration.getNeuronalNetworkManager().getLayer(iaIteration.getNeuronalNetworkManager().getLayerID(this)).getSize());i2++) {

                out = out+input[i2]* weights.getAt(i).get(i2);

            }

            if(out > 0){

                output[i] = out;

            }else{

                output[i] = 0;

            }

        }

    }

    @Override
    public MultipleWeightsData getWeights() {
        return weights;
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

