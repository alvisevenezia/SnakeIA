package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class ComputeLayer extends Layer{

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private float[] bias;
    private HashMap<Integer,float[]>weights;

    public ComputeLayer(int size,IAIteration iaIteration){

        this.iaIteration = iaIteration;
        this.size = size;
        output = new float[size];
        bias = new float[size];
        weights = new HashMap<>();

    }

    public void setWeights(int ID,float[] val){

        weights.put(ID,val);

    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    public float[] getBias() {
        return bias;
    }

    @Override
    public HashMap<Integer, float[]> geWeights() {
        return weights;
    }

    public void compute(){

        float[] input = iaIteration.getLayer(iaIteration.getLayerID(this)-1).getOutput();
        for(int i = 0;i < size;i++){

            float out = 0;

            for(int i2 = 0;i2 < iaIteration.getLayer(iaIteration.getLayerID(this)).getSize();i2++) {

                out = out+input[i2]*weights.get(i)[i2];

            }

            out = out+bias[i];

            output[i] = out;

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

    public float[] getWeights(int id){

        return weights.get(id);

    }


    public void generateRandomWeights() {

        Random r = new Random();

        for(int i = 0;i < size;i++){

            float[] w = new float[iaIteration.getLayer(iaIteration.getLayerID(this)).getSize()];

            for(int i2 = 0;i2< iaIteration.getLayer(iaIteration.getLayerID(this)).getSize();i2++){

                w[i2] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;

            }

            bias[i] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;

            weights.put(i,w);
        }

    }

    public void mergeWeight(ComputeLayer computeLayer1,ComputeLayer computeLayer2){

        int splitID = iaIteration.getGlobalManager().getRandom().nextInt(computeLayer1.getSize());

        for(int ID = 0;ID<computeLayer1.getSize();ID++){

            if(ID<splitID){

                weights.put(ID,computeLayer1.getWeights(ID));
                bias[ID] = computeLayer1.getBias()[ID];

            }
            else {

                weights.put(ID,computeLayer2.getWeights(ID));
                bias[ID] = computeLayer1.getBias()[ID];

            }

        }

        if(iaIteration.getGlobalManager().getRandom().nextInt(iaIteration.getGlobalManager().getQuantity())<iaIteration.getGlobalManager().getQuantity()*(iaIteration.getGlobalManager().getMutationRate()/100)){

            int neuronID = iaIteration.getGlobalManager().getRandom().nextInt((computeLayer1.getSize()*2)-1);

            if(neuronID <computeLayer1.getSize()) {

                float newWeights[] = new float[computeLayer1.getWeights(0).length];

                for (int weightID = 0; weightID < computeLayer1.getWeights(0).length; weightID++) {

                    newWeights[weightID] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;

                }

                weights.replace(neuronID, newWeights);
            }else{

                bias[neuronID%computeLayer1.getWeights(0).length] = (iaIteration.getGlobalManager().getRandom().nextFloat()*2)-1;

            }
        }

        /*
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
*/

    }

    public float[] getOutput(){

        return output;

    }

    @Override
    public int getSize() {
        return size;
    }
}
