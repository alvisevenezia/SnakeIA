package fr.alvisevenezia.IA.NN.weights;

public abstract class WeightsData<W,T> {

    public abstract W getData();

    public abstract int length();

    public abstract Float getFloat(int id1,int id2);

    public abstract void setAt(int id1,T val);

}
