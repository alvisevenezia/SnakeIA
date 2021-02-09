package fr.alvisevenezia.IA.NN;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Layer {

    public abstract int getSize();

    public abstract float[] getOutput();

    public abstract  void compute();

    public abstract float[] getWeights();

    public abstract float[] getBias();

    public abstract HashMap<Integer,float[]> geWeights();
}
