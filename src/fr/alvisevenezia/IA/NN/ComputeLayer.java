package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class ComputeLayer {

    private int size;
    private IAIteration iaIteration;
    private float[] output;
    private HashMap<Integer,float[]>weights;

    public ComputeLayer(int size,IAIteration iaIteration){

        this.iaIteration = iaIteration;
        this.size = size;
        output = new float[size];
        weights = new HashMap<>();

    }

    public void compute(){


    }

    public float[] getWeights(int id){

        return weights.get(id);

    }

    public void generateRandomWeights(){

        Random r = new Random();
        float[] w = new float[iaIteration.getFirstLayerSize()];

        for(int i = 0;i < size;i++){

            for(int i2 = 0;i<= iaIteration.getFirstLayerSize();i++){

                w[i2] = r.nextFloat();

            }

            weights.put(i,w);
        }

    }

    public void mergeWeight(int id,float[] w1,float[] w2,boolean randomize){

        Random r = new Random();
        float[] w = new float[w1.length];

        for(int i = 0;i<size;i++){


            if(randomize) {

                if (r.nextBoolean()) {

                    w[i] = w2[i];

                } else {

                    w[i] = w1[i];
                }

            }else{

                w[i] = (w1[i]+w2[i])/2;

            }

        }

        weights.put(id,w);

    }

    public float[] getOutput(){

        return output;

    }





}
