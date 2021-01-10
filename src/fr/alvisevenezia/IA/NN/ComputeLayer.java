package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.HashMap;
import java.util.Random;

public class ComputeLayer extends Layer{

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

        float[] input = iaIteration.getLayer(iaIteration.getLayerID(this)).getOutput();

        for(int i = 0;i < size;i++){

            for(int i2 = 0;i2 < iaIteration.getLayer(iaIteration.getLayerID(this)).getSize();i2++) {

                //System.out.println("CL: "+i);
                //System.out.println("CL: "+i2);
                //System.out.println("CL2: "+weights.size());
                //System.out.println("CL3: "+weights.get(i)[i2]);

                output[i2] = (float) (1 / (1 + (Math.exp((float) (weights.get(i)[i2] * input[i2])))));

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

                w[i2] = r.nextFloat();

            }

            weights.put(i,w);
        }

    }

    public void mergeWeight(int id,float[] w1,float[] w2,boolean randomize){

        Random r = new Random();
        float[] w = new float[w1.length];

        for(int i = 0;i<24;i++){

            if(randomize) {

                if (r.nextBoolean()) {

                    w[i] = w2[i];

                } else {

                    w[i] = w1[i];
                }

            }else{

                    w[i] = (w1[i] + w2[i]) / 2;

            }



        }

        weights.put(id,w);

    }

    public float[] getOutput(){

        return output;

    }


    @Override
    public int getSize() {
        return size;
    }
}
