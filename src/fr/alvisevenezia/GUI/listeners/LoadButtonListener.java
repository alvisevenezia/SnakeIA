package fr.alvisevenezia.GUI.listeners;

import fr.alvisevenezia.IA.CSV.CSVBuilder;
import fr.alvisevenezia.IA.CSV.CSVReader;
import fr.alvisevenezia.IA.GA.GeneticAlgoritmManager;
import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.IA.NN.layers.ComputeLayer;
import fr.alvisevenezia.IA.NN.layers.DecisionLayer;
import fr.alvisevenezia.IA.NN.layers.FirstLayer;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadButtonListener implements ActionListener {

    GlobalManager globalManager;

    public LoadButtonListener(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            switch (globalManager.getIaType()){

                case GANN:

                    GeneticAlgoritmManager geneticAlgoritmManager = new GeneticAlgoritmManager(globalManager);
                    globalManager.setGeneticAlgoritmManager(geneticAlgoritmManager);

                    JFileChooser jFileChooser = new JFileChooser(globalManager.getPath());
                    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int returnValue = jFileChooser.showOpenDialog(null);
                    File selectedFile = jFileChooser.getSelectedFile();

                    if(returnValue == JFileChooser.APPROVE_OPTION) {

                        if (!jFileChooser.getSelectedFile().isDirectory()) {

                            JOptionPane.showMessageDialog(new JFrame(), selectedFile.getName() + " is not a folder");
                            return;

                        }
                        int quantity = selectedFile.list().length - 1;

                        System.out.println(quantity);

                        CSVReader configReader = new CSVReader(globalManager, selectedFile.getPath(), "Config");
                        ArrayList<Integer> best = configReader.readIntFile();

                        geneticAlgoritmManager.generateApple();

                        SnakeManager snakeManager;
                        NeuronalNetworkManager neuronalNetworkManager;

                        CSVReader fileReader;
                        ArrayList<Float> values;



                        for (int i = 0; i < quantity; i++) {

                            fileReader = new CSVReader(globalManager, selectedFile.getPath(), "serpent" + i);
                            values = fileReader.readFloatFile();

                            neuronalNetworkManager = new NeuronalNetworkManager(4,2, globalManager.getLayersSize(),globalManager);
                            neuronalNetworkManager.createLayers();


                            float[] bias = new float[neuronalNetworkManager.getLayersSize()[0]];

                            int counter = 0;

                            for (int ID = 0; ID < neuronalNetworkManager.getLayersSize()[0]; ID++) {

                                neuronalNetworkManager.getLayer(0).getWeights().setAt(ID,values.get(counter));
                                counter++;
                                bias[ID] = values.get(counter);
                                counter++;

                            }

                            ((FirstLayer) neuronalNetworkManager.getLayer(0)).setBias(bias);

                            bias = new float[neuronalNetworkManager.getLayersSize()[1]];

                            for (int ID = 0; ID < neuronalNetworkManager.getLayersSize()[1]; ID++) {

                                List<Float> val = new ArrayList<>();

                                for (int weightID = 0; weightID < neuronalNetworkManager.getLayersSize()[1]; weightID++) {

                                    val.add(values.get(counter));
                                    counter++;
                                }
                                neuronalNetworkManager.getLayer(1).getWeights().setAt(ID,val);
                                bias[ID] = values.get(counter);
                                counter++;

                            }

                            ((ComputeLayer) neuronalNetworkManager.getLayer(1)).setBias(bias);

                            bias = new float[neuronalNetworkManager.getLayersSize()[2]];

                            for (int ID = 0; ID < neuronalNetworkManager.getLayersSize()[2]; ID++) {

                                List<Float> val = new ArrayList<>();

                                for (int weightID = 0; weightID < neuronalNetworkManager.getLayersSize()[2]; weightID++) {

                                    val.add(values.get(counter));
                                    counter++;
                                }
                                neuronalNetworkManager.getLayer(2).getWeights().setAt(ID,val);
                                bias[ID] = values.get(counter);
                                counter++;
                            }

                            ((ComputeLayer) neuronalNetworkManager.getLayer(2)).setBias(bias);

                            bias = new float[neuronalNetworkManager.getLayersSize()[3]];

                            for (int ID = 0; ID < neuronalNetworkManager.getLayersSize()[3]; ID++) {

                                List<Float> val = new ArrayList<>();

                                for (int weightID = 0; weightID < neuronalNetworkManager.getLayersSize()[3]; weightID++) {

                                    val.add(values.get(counter));
                                    counter++;
                                }

                                neuronalNetworkManager.getLayer(3).getWeights().setAt(ID,val);
                                bias[ID] = values.get(counter);
                                counter++;
                            }

                            ((DecisionLayer) neuronalNetworkManager.getLayer(3)).setBias(bias);

                            snakeManager = new SnakeManager(geneticAlgoritmManager);
                            snakeManager.createSnake();
                            snakeManager.initilizeApple();
                            snakeManager.setAlive(true);
                            snakeManager.setApple(geneticAlgoritmManager.getAppleCoord(0)[0], geneticAlgoritmManager.getAppleCoord(0)[1], 1);
                            neuronalNetworkManager.setSnakeManager(snakeManager);

                            geneticAlgoritmManager.addManager(neuronalNetworkManager,snakeManager);

                        }

                        globalManager.setLoaded(true);
                        geneticAlgoritmManager.createFirstGeneration();

                        for(Component component : globalManager.getMainGUI().getMain().getComponents()){

                            if(component instanceof JButton){

                                JButton button = (JButton) component;

                                if(button.getText() == "Start IA"){

                                    button.setText("Stop IA");

                                }

                            }

                        }

                    }

                    break;
            }


        }

    }
}
