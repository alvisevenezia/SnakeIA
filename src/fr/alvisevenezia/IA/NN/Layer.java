package fr.alvisevenezia.IA.NN;

public abstract class Layer {

    public abstract int getSize();

    public abstract float[] getOutput();

    public abstract  void compute();

}
