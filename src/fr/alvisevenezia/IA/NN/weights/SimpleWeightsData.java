package fr.alvisevenezia.IA.NN.weights;

import java.util.List;

public class SimpleWeightsData extends WeightsData<List<Float>,Float>{


    private List<Float>data;

    @Override
    public List<Float> getData() {
        return data;
    }

    @Override
    public int length() {
        return data.size();
    }

    @Override
    public Float getFloat(int id1, int id2) {
        return data.get(id1);
    }

    @Override
    public void setAt(int id, Float val) {

        data.set(id,val);

    }

    public SimpleWeightsData(List<Float> data) {
        this.data = data;
    }

    public Float getAt(int id){

        return data.get(id);

    }

}
