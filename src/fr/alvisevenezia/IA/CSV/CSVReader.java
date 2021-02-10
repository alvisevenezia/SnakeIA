package fr.alvisevenezia.IA.CSV;

import fr.alvisevenezia.SNAKE.GlobalManager;

import java.io.*;
import java.net.Inet4Address;
import java.util.ArrayList;

public class CSVReader {

    private GlobalManager globalManager;
    private String path;
    private String name;

    public CSVReader(GlobalManager globalManager, String path, String name) {
        this.globalManager = globalManager;
        this.path = path;
        this.name = name;
    }

    public ArrayList<Float> readFloatFile(){

        ArrayList<Float>values = new ArrayList<>();

        File file = new File(path+"\\"+name);

        System.out.println(path+"\\"+name);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while((line = bufferedReader.readLine()) != null){

                String[] lines = line.split(",");

                for(int i = 0;i<lines.length;i++){

                    values.add(Float.parseFloat(lines[i]));

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;

    }

    public ArrayList<Integer> readIntFile(){

        ArrayList<Integer>values = new ArrayList<>();
        File file = new File(path+"\\"+name);

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while((line = bufferedReader.readLine()) != null){

                String[] lines = line.split(",");

                for(int i = 0;i<lines.length;i++){

                    values.add(Integer.parseInt(lines[i]));

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;

    }

}
