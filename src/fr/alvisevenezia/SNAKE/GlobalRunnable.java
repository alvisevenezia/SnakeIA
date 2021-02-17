package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.IA.IAIteration;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class GlobalRunnable extends Thread {

    GlobalManager globalManager;

    public GlobalRunnable(GlobalManager globalManager) {

        this.globalManager = globalManager;

    }

    @Override
    public void run() {

        while(isAlive()) {

            if (globalManager.isStarted() && globalManager.getSnakeQuantity() > 0 && globalManager.getSnakeAliveQuantity() == 0) {

                globalManager.stopRunnables();
                globalManager.setWinner(globalManager.getBestSnakes(2));
                globalManager.setStarted(false);
          /*  ArrayList<SnakeManager> bests = globalManager.getBestSnakes(2);

            if(bests.size() == 2) {

                globalManager.getMainGUI().updateBests(bests.get(0).calculateFitness(), bests.get(1).calculateFitness());

            }*/

                globalManager.startIA(globalManager.getQuantity());

            }

            if (globalManager.isStarted()) {

                for (IAIteration iaIteration : globalManager.getManagers().keySet()) {

                    if (iaIteration.getSnakeManager().isAlive()) {

                        for (int i = 0; i < iaIteration.getNeuronalNetworkManager().getLayersList().size(); i++) {

                            iaIteration.getNeuronalNetworkManager().getLayer(i).compute();
                        }

                        iaIteration.getSnakeManager().moovSnake(iaIteration.getMouvement());

                    }

                }

                globalManager.getMainGUI().updateStats(globalManager.getSnakeQuantity(), globalManager.getBestSnake().calculateFitness().toString(), globalManager.getSnakeAliveQuantity(), globalManager.getGenerationCOunt(), globalManager.getMoyenne());


            }
        }

    }
}
