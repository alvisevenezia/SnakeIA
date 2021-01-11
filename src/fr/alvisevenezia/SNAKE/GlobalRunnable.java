package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.IA.IAIteration;

import java.util.Random;
import java.util.TimerTask;

public class GlobalRunnable extends TimerTask {

    GlobalManager globalManager;

    public GlobalRunnable(GlobalManager globalManager) {

        this.globalManager = globalManager;

    }

    @Override
    public void run() {


        if(globalManager.isStarted() && globalManager.getSnakeQuantity() > 0 && globalManager.getSnakeAliveQuantity() == 0){

            globalManager.stopRunnables();
            globalManager.setWinner(globalManager.getBestSnakes());
            globalManager.setStarted(false);
            globalManager.startIA(2000);

        }

        if(globalManager.isStarted()) {

            for (IAIteration iaIteration : globalManager.getManagers().keySet()) {

                if (iaIteration.getSnakeManager().isAlive()) {

                    Random r = new Random();
                    int n = r.nextInt(3);

                    if (iaIteration.getSnakeManager().getCurrentapple() <= 3) {

                        if (n == 0 && iaIteration.getSnakeManager().getCurrentapple() < 4) {

                            iaIteration.getSnakeManager().randomGenerateApple();

                        }

                    }

                    for (int i = 0; i < iaIteration.getLayers().size(); i++) {

                        iaIteration.getLayer(i).compute();

                    }

                    iaIteration.getSnakeManager().moovSnake(iaIteration.getMouvement());

                }

            }

            globalManager.getMainGUI().updateStats(globalManager.getSnakeQuantity(), globalManager.getBestSnake().getScore(), globalManager.getSnakeAliveQuantity(),globalManager.getGenerationCOunt());

        }

    }
}
