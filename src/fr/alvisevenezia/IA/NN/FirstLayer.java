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

        float[] input = iaIteration.getSnakeInfo(iaIteration.getSnakeManager().getHeadPos()[0],iaIteration.getSnakeManager().getHeadPos()[1]);

     /*   for(int i = 0;i<size;i++){

           // System.out.println("Pos Queue "+i+" "+iaIteration.getSnakeManager().getQueuePos()[0]+" "+iaIteration.getSnakeManager().getQueuePos()[1]);
           // System.out.println("Pos Head "+i+" "+iaIteration.getSnakeManager().getHeadPos()[0]+" "+iaIteration.getSnakeManager().getHeadPos()[1]);
            //  System.out.println("Input "+i+" "+input[i]);

            if(i % 3 == 0){

                if(i != 6 && i != 9 && i !=12 && i != 21)System.out.print(input[i]+" ");
                else if(i == 9)System.out.print(input[i]+"      ");
                else System.out.println(input[i]+" ");
            }


            output[i] = (float)(1/(1+(Math.exp((float)(weights[i]*input[i])))));


        }

        System.out.println("");

        for(int i = 0;i<size;i++){

            if(i % 3 == 1){

                if(i != 7 && i != 10 && i !=13 && i != 22)System.out.print(input[i]+" ");
                else if(i == 10)System.out.print(input[i]+"      ");
                else System.out.println(input[i]+" ");
            }

        }

        System.out.println("");

        for(int i = 0;i<size;i++){

            if(i % 3 == 2){

                if(i != 8 && i != 11 && i !=14 && i != 23)System.out.print(input[i]+" ");
                else if(i == 11)System.out.print(input[i]+"      ");
                else System.out.println(input[i]+" ");
            }

        }*/

        System.out.println();
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
