package fr.alvisevenezia.IA.NN.layers;

import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.IA.NN.weights.SimpleWeightsData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FirstLayer extends Layer<FirstLayer, SimpleWeightsData> {

    private int size;
    private SimpleWeightsData weights;
    private float[] output;
    private float[] bias;
    private NeuronalNetworkManager neuronalNetworkManager;

    public FirstLayer(int size, NeuronalNetworkManager neuronalNetworkManager){

        this.size = size;
        this.neuronalNetworkManager = neuronalNetworkManager;
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

        float[] input = neuronalNetworkManager.getSnakeInfo(neuronalNetworkManager.getSnakeManager().getHeadPos()[0],neuronalNetworkManager.getSnakeManager().getHeadPos()[1]);

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


        int splitID = neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextInt(firstLayer1.getSize());

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

        if(neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextInt(neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getQuantity())<neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getQuantity()*(neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getMutationRate()/100)){

            int neuronID = neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextInt((firstLayer1.getWeights().length()*2)-1);

            if(neuronID <firstLayer1.getWeights().length()) {

                weights.setAt(neuronID,(neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextFloat()*2)-1);

            }else{

                bias[neuronID%firstLayer1.getWeights().length()] = (neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextFloat()*2)-1;
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

        switch (neuronalNetworkManager.getGlobalManager().getIaType()){

            case GANN:

                for(int i = 0;i<size;i++){

                    setWeight(i,(neuronalNetworkManager.getGlobalManager().getGeneticAlgoritmManager().getRandom().nextFloat()*2)-1);

                }

                break;

        }

    }

    @Override
    public int getSize() {
        return size;
    }
}
