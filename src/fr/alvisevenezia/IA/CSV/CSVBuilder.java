package fr.alvisevenezia.IA.CSV;

import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVBuilder {

    private GlobalManager globalManager;
    private String path;

    public CSVBuilder(String path, GlobalManager globalManager){

        this.path = path;
        this.globalManager = globalManager;

    }

    public void buildFile(){

        switch (globalManager.getIaType()){

            case GANN:

                File file = new File(path+globalManager.getGeneticAlgoritmManager().getBestSnake().calculateFitness());
                file.mkdir();

                ArrayList<SnakeManager>bestManagers = globalManager.getGeneticAlgoritmManager().getBestSnakes(10);
                ArrayList<NeuronalNetworkManager>neuronalNetworkManagers  = new ArrayList<>(globalManager.getGeneticAlgoritmManager().getManagers().keySet());

                File config = new File(path+"\\Config");

                FileWriter configWriter = null;
                try {
                    configWriter = new FileWriter(config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedWriter configBufferedWriter = new BufferedWriter(configWriter);

                for(int serpentID = 0;serpentID<globalManager.getGeneticAlgoritmManager().getQuantity();serpentID++) {

                    File serpent = new File(path+"\\serpent"+serpentID);
                    try {
                        FileWriter fileWriter = new FileWriter(serpent);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


                        ArrayList<String>values = neuronalNetworkManagers.get(serpentID).getFloat();
                        for (int i = 0; i < values.size(); i++) {

                            bufferedWriter.write(values.get(i));
                            bufferedWriter.write(",");

                        }

                        for (int i = 0; i < bestManagers.size(); i++) {

                            if(bestManagers.get(i)==neuronalNetworkManagers.get(serpentID).getSnakeManager()){

                                configBufferedWriter.write(String.valueOf(serpentID));
                                configBufferedWriter.write(",");


                            }

                        }

                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

                try {
                    configBufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }


    }

}
