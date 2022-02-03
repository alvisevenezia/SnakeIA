package fr.alvisevenezia.Thread;

import fr.alvisevenezia.IA.GA.GeneticAlgoritmManager;
import fr.alvisevenezia.IA.NN.NeuronalNetworkManager;
import fr.alvisevenezia.SNAKE.GlobalManager;

import java.awt.*;

public class GACalculationThead extends Thread {

    GeneticAlgoritmManager geneticAlgoritmManager;

    public GACalculationThead(GeneticAlgoritmManager geneticAlgoritmManager) {

        this.geneticAlgoritmManager = geneticAlgoritmManager;

    }

    @Override
    public void run() {

        while(isAlive()) {

            if (geneticAlgoritmManager.getGlobalManager().isStarted() && geneticAlgoritmManager.getSnakeQuantity() > 0 && geneticAlgoritmManager.getSnakeAliveQuantity() < 1) {

                System.out.println("Meilleur score: " + geneticAlgoritmManager.getBestSnakes(1).get(0).calculateFitness());
                geneticAlgoritmManager.getGlobalManager().setStarted(false);
                geneticAlgoritmManager.startIA(geneticAlgoritmManager.getQuantity());

            }

            if (geneticAlgoritmManager.getGlobalManager().isStarted()) {

                for (NeuronalNetworkManager neuronalNetworkManager : geneticAlgoritmManager.getManagers().keySet()) {

                    if (neuronalNetworkManager.getSnakeManager().isAlive()) {

                        for (int i = 0; i < neuronalNetworkManager.getLayersList().size(); i++) {

                            neuronalNetworkManager.getLayer(i).compute();
                        }

                        neuronalNetworkManager.getSnakeManager().moovSnake(neuronalNetworkManager.getMouvement());
                        neuronalNetworkManager.getSnakeManager().updateAll(neuronalNetworkManager.getMouvement());

                    }

                }


                //geneticAlgoritmManager.getGlobalManager().getMainGUI().updateStats(geneticAlgoritmManager.getSnakeQuantity(), geneticAlgoritmManager.getBestSnake().calculateFitness().toString(), geneticAlgoritmManager.getSnakeAliveQuantity(), geneticAlgoritmManager.getGenerationCOunt(), geneticAlgoritmManager.getMoyenne());


            }
        }

    }
}
