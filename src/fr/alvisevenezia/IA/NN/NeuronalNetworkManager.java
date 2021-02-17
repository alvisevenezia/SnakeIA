package fr.alvisevenezia.IA.NN;

import fr.alvisevenezia.IA.IAIteration;
import fr.alvisevenezia.IA.NN.layers.ComputeLayer;
import fr.alvisevenezia.IA.NN.layers.DecisionLayer;
import fr.alvisevenezia.IA.NN.layers.FirstLayer;
import fr.alvisevenezia.IA.NN.layers.Layer;
import fr.alvisevenezia.SNAKE.GlobalManager;

import java.util.ArrayList;

public class NeuronalNetworkManager {

    private int hiddenLayers;
    private int size;
    private int[] layersSize;
    private GlobalManager globalManager;
    private ArrayList<Layer> layersList;
    private IAIteration iaIteration;

    public NeuronalNetworkManager(int size,int hiddenLayers, int[] layersSize, GlobalManager globalManager,IAIteration iaIteration) {
        this.hiddenLayers = hiddenLayers;
        this.size = size;
        this.layersSize = layersSize;
        this.iaIteration = iaIteration;
        this.globalManager = globalManager;
        layersList = new ArrayList<>();

    }

    public NeuronalNetworkManager(int size,int hiddenLayers, int[] layersSize, GlobalManager globalManager) {
        this.hiddenLayers = hiddenLayers;
        this.size = size;
        this.layersSize = layersSize;
        this.globalManager = globalManager;
        layersList = new ArrayList<>();

    }

    public NeuronalNetworkManager(int hiddenLayers, int size, GlobalManager globalManager) {
        this.hiddenLayers = hiddenLayers;
        this.size = size;
        this.globalManager = globalManager;
    }

    public NeuronalNetworkManager(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    public void setIaIteration(IAIteration iaIteration) {
        this.iaIteration = iaIteration;
    }

    public int[] getLayersSize() {
        return layersSize;
    }

    public void setLayersSize(int[] layersSize) {
        this.layersSize = layersSize;
    }

    public void createLayers(){

        FirstLayer firstLayer = new FirstLayer(layersSize[0],iaIteration);
        layersList.add(firstLayer);

        for(int layerID = 1;layerID<size-1;layerID++){

            ComputeLayer computeLayer = new ComputeLayer(layersSize[layerID],iaIteration);
            layersList.add(computeLayer);
        }

        DecisionLayer decisionLayer = new DecisionLayer(layersSize[size-1],iaIteration);
        layersList.add(decisionLayer);

    }

    public void randomInitializeLayers(){

        for(int layerID = 0;layerID<size;layerID++){

            layersList.get(layerID).generateRandomWeights();

        }

    }

    public int getSize() {
        return size;
    }

    public Layer getLayer(int id){

        return layersList.get(id);

    }

    public void mergeWeights(NeuronalNetworkManager neuronalNetworkManager1,NeuronalNetworkManager neuronalNetworkManager2){

        for(int layerID = 0;layerID<size;layerID++){

            layersList.get(layerID).mergeWeights(neuronalNetworkManager1.getLayer(layerID),neuronalNetworkManager2.getLayer(layerID));


        }

    }



    public int getLayerID(Layer layer){

        for(int layerID = 0;layerID<size;layerID++){

            if(layersList.get(layerID) == layer){

                return layerID;

            }

        }

        return -1;

    }

    public ArrayList<Layer> getLayersList() {
        return layersList;
    }
}
