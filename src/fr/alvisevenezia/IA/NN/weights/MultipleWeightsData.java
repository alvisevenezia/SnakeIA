package fr.alvisevenezia.IA.NN.weights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MultipleWeightsData extends WeightsData<HashMap<Integer,List<Float>>,List<Float>>{

   private HashMap<Integer,List<Float>>data;

    public MultipleWeightsData(int size) {

        data = new HashMap<>();

        for(int i = 0;i<size;i++){

            data.put(i,new ArrayList<>(Collections.nCopies(size,0f)));

        }
    }

    @Override
    public HashMap<Integer, List<Float>> getData() {
        return null;
    }

    @Override
    public int length() {
        return data.get(0).size();
    }

    @Override
    public Float getFloat(int id1, int id2) {
        return data.get(id1).get(id2);
    }

    public List<Float> getAt(int id){

        return data.get(id);

    }

    public void setAt(int id,List<Float> val){

        data.replace(id,val);

    }
}
