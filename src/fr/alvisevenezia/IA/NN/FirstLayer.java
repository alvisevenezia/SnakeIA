package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;

import java.util.Random;

public class FirstLayer extends Layer{

    private int size;
    private float[] weights;
    private float[] output;
    private IAIteration iaIteration;

    public FirstLayer(int size, IAIteration iaIteration){

        this.size = size;
        this.iaIteration = iaIteration;
        weights = new float[size];
        output = new float[size];

    }

    public void compute(){

        int[] input = iaIteration.getSnakeInfo(iaIteration.getSnakeManager().getHeadPos()[0],iaIteration.getSnakeManager().getHeadPos()[1]);

        for(int i = 0;i<size;i++){

            output[i] = (float)(1/(1+(Math.exp((float)(weights[i]*input[i])))));

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


    }

    public void mergeWeights(float[] w1,float[] w2,boolean randomize){

        Random r = new Random();

        for(int i = 0;i<size;i++){


            if(randomize) {

                if (r.nextBoolean()) {

                    weights[i] = w2[i];

                } else {

                    weights[i] = w1[i];
                }

            }else{

                weights[i] = (w1[i]+w2[i])/2;

            }

        }


    }

    public void generateRandomWeights(){

        Random r = new Random();

        for(int i = 0;i<size;i++){

            setWeight(i,r.nextFloat());

        }

    }

    @Override
    public int getSize() {
        return size;
    }
}
