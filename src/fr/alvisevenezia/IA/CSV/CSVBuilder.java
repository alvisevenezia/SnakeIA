package fr.alvisevenezia.IA.CSV;

import fr.alvisevenezia.IA.IAIteration;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CSVBuilder {

    private GlobalManager globalManager;
    private String path;
    private ArrayList<IAIteration>iaIterations;

    public CSVBuilder(String path, ArrayList<IAIteration> iaIterations, GlobalManager globalManager){

        this.path = path;
        this.iaIterations = iaIterations;
        this.globalManager = globalManager;

    }

    public void buildFile(){

        File file = new File(path);
        file.mkdir();

        ArrayList<SnakeManager>bestManagers = globalManager.getBestSnakes(10);

        File config = new File(path+"\\Config");

        FileWriter configWriter = null;
        try {
            configWriter = new FileWriter(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter configBufferedWriter = new BufferedWriter(configWriter);

        for(int serpentID = 0;serpentID<globalManager.getQuantity();serpentID++) {

            File serpent = new File(path+"\\serpent"+serpentID);
            try {
                FileWriter fileWriter = new FileWriter(serpent);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


                ArrayList<String>values = iaIterations.get(serpentID).getFloat();
                for (int i = 0; i < values.size(); i++) {

                    bufferedWriter.write(values.get(i));
                    bufferedWriter.write(",");

                }

                for (int i = 0; i < bestManagers.size(); i++) {

                    if(bestManagers.get(i)==iaIterations.get(serpentID).getSnakeManager()){

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
    }

}
