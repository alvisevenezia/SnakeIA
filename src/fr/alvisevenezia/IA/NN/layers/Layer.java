package fr.alvisevenezia.IA.NN.layers;

import fr.alvisevenezia.IA.NN.weights.WeightsData;

import java.util.HashMap;

public abstract class Layer <T extends Layer,W extends WeightsData>{

    public abstract int getSize();

    public abstract float[] getOutput();

    public abstract  void compute();

    public abstract W getWeights();

    public abstract float[] getBias();

    public abstract void generateRandomWeights();

    public abstract void mergeWeights(T layer1,T layer2);
}
