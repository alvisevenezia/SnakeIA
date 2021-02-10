package fr.alvisevenezia.GUI.listeners;

import fr.alvisevenezia.IA.CSV.CSVBuilder;
import fr.alvisevenezia.IA.CSV.CSVReader;
import fr.alvisevenezia.IA.IAIteration;
import fr.alvisevenezia.IA.NN.ComputeLayer;
import fr.alvisevenezia.IA.NN.DecisionLayer;
import fr.alvisevenezia.IA.NN.FirstLayer;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadButtonListener implements ActionListener {

    GlobalManager globalManager;

    public LoadButtonListener(GlobalManager globalManager) {
        this.globalManager = globalManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() instanceof JButton){

            if(globalManager.isStarted()){

                CSVBuilder csvBuilder = new CSVBuilder(null,globalManager.getPath()+"\\"+globalManager.getBestSnake().calculateFitness(),new ArrayList<IAIteration>(globalManager.getManagers().keySet()),globalManager);
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

                CSVReader fileReader;
                ArrayList<Float> values;



                for (int i = 0; i < quantity; i++) {

                    fileReader = new CSVReader(globalManager, selectedFile.getPath(), "serpent" + i);
                    values = fileReader.readFloatFile();

                    iaIteration = new IAIteration(globalManager, i);
                    iaIteration.createIA(true, null);
                    float[] bias = new float[iaIteration.getLayer(0).getSize()];

                    for (int ID = 0; ID < iaIteration.getLayer(0).getSize(); ID++) {

                        ((FirstLayer) iaIteration.getLayer(0)).setWeight(ID, values.get(2 * ID));
                        bias[ID] = values.get(2 * ID + 1);

                    }

                    ((FirstLayer) iaIteration.getLayer(0)).setBias(bias);

                    bias = new float[iaIteration.getLayer(1).getSize()];

                    for (int ID = 0; ID < iaIteration.getLayer(1).getSize(); ID++) {

                        float[] val = new float[iaIteration.getLayer(1).geWeights().get(ID).length];

                        for (int weightID = 0; weightID < iaIteration.getLayer(1).geWeights().get(ID).length; weightID++) {

                            val[weightID] = values.get((iaIteration.getLayer(0).getSize() * 2) + (iaIteration.getLayer(1).geWeights().get(ID).length + 1) * ID + weightID);

                        }
                        ((ComputeLayer) iaIteration.getLayer(1)).setWeights(ID, val);
                        bias[ID] = values.get((iaIteration.getLayer(0).getSize() * 2) + (iaIteration.getLayer(1).geWeights().get(ID).length) * (ID + 1) + 1);

                    }

                    ((ComputeLayer) iaIteration.getLayer(1)).setBias(bias);

                    bias = new float[iaIteration.getLayer(2).getSize()];

                    for (int ID = 0; ID < iaIteration.getLayer(2).getSize(); ID++) {

                        float[] val = new float[iaIteration.getLayer(2).geWeights().get(ID).length];

                        for (int weightID = 0; weightID < iaIteration.getLayer(2).geWeights().get(ID).length; weightID++) {

                            val[weightID] = values.get((iaIteration.getLayer(0).getSize() * 2) + (iaIteration.getLayer(2).geWeights().get(ID).length + 1) * (iaIteration.getLayer(1).getSize() + ID) + weightID);

                        }
                        ((ComputeLayer) iaIteration.getLayer(2)).setWeights(ID, val);
                        bias[ID] = values.get((iaIteration.getLayer(0).getSize() * 2) + (iaIteration.getLayer(1).geWeights().get(ID).length) * ((iaIteration.getLayer(1).getSize()+ 1))+(iaIteration.getLayer(2).geWeights().get(ID).length*(ID+1))+ 1);

                    }

                    ((ComputeLayer) iaIteration.getLayer(1)).setBias(bias);

                    bias = new float[iaIteration.getLayer(2).getSize()];

                    for (int ID = 0; ID < iaIteration.getLayer(3).getSize(); ID++) {

                        float[] val = new float[iaIteration.getLayer(3).geWeights().get(ID).length];

                        for (int weightID = 0; weightID < iaIteration.getLayer(3).geWeights().get(ID).length; weightID++) {

                            val[weightID] = values.get((iaIteration.getLayer(0).getSize() * 2) + ((iaIteration.getLayer(2).geWeights().get(ID).length + 1) * (iaIteration.getLayer(1).getSize()) + iaIteration.getLayer(2).getSize()) + (iaIteration.getLayer(3).getSize() + 1) * ID + weightID);

                        }

                        ((DecisionLayer) iaIteration.getLayer(3)).setWeights(ID, val);
                        bias[ID] = values.get((iaIteration.getLayer(0).getSize() * 2) + ((iaIteration.getLayer(2).geWeights().get(ID).length + 1) * (iaIteration.getLayer(1).getSize()) + iaIteration.getLayer(2).getSize()) + (iaIteration.getLayer(3).getSize() + 1) * (ID + 1));

                    }

                    ((DecisionLayer) iaIteration.getLayer(3)).setBias(bias);

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
                globalManager.createFirstGeneration(2000);

                for(Component component : globalManager.getMainGUI().getMain().getComponents()){

                    if(component instanceof JButton){

                        JButton button = (JButton) component;

                        if(button.getText() == "Lancer Snake"){

                            button.setText("Arreter IA");

                        }

                    }

                }

            }

        }

    }
}
