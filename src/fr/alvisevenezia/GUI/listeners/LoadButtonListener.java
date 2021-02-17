package fr.alvisevenezia.GUI.listeners;

import fr.alvisevenezia.IA.CSV.CSVBuilder;
import fr.alvisevenezia.IA.CSV.CSVReader;
import fr.alvisevenezia.IA.IAIteration;
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

            if(globalManager.isStarted()){

                CSVBuilder csvBuilder = new CSVBuilder(globalManager.getPath()+"\\"+globalManager.getBestSnake().calculateFitness(),new ArrayList<IAIteration>(globalManager.getManagers().keySet()),globalManager);
                csvBuilder.buildFile();

            }

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

                globalManager.setGenerationCOunt(globalManager.getGenerationCOunt() + 1);
                globalManager.generateApple();

                IAIteration iaIteration;
                SnakeManager snakeManager;
                NeuronalNetworkManager neuronalNetworkManager;

                CSVReader fileReader;
                ArrayList<Float> values;



                for (int i = 0; i < quantity; i++) {

                    fileReader = new CSVReader(globalManager, selectedFile.getPath(), "serpent" + i);
                    values = fileReader.readFloatFile();

                    iaIteration = new IAIteration(globalManager, i);

                    neuronalNetworkManager = new NeuronalNetworkManager(4,2, globalManager.getLayersSize(),globalManager,iaIteration);
                    neuronalNetworkManager.createLayers();

                    iaIteration.setNeuronalNetworkManager(neuronalNetworkManager);



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

                    snakeManager = new SnakeManager(globalManager,iaIteration);
                    snakeManager.createSnake();
                    snakeManager.initilizeApple();
                    snakeManager.setAlive(true);
                    snakeManager.setApple(globalManager.getAppleCoord(0)[0], globalManager.getAppleCoord(0)[1], 1);
                    iaIteration.setSnakeManager(snakeManager);
                    iaIteration.setSnakeManager(snakeManager);

                    globalManager.addManager(iaIteration,snakeManager);

                }

                globalManager.setLoaded(true);
                globalManager.createFirstGeneration(globalManager.getQuantity());

                for(Component component : globalManager.getMainGUI().getMain().getComponents()){

                    if(component instanceof JButton){

                        JButton button = (JButton) component;

                        if(button.getText() == "Start IA"){

                            button.setText("Stop IA");

                        }

                    }

                }

            }

        }

    }
}
