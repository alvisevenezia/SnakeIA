package fr.alvisevenezia.SNAKE;

import java.util.TimerTask;

public class GlobalRunnable extends TimerTask {

    GlobalManager globalManager;

    public GlobalRunnable(GlobalManager globalManager) {

        this.globalManager = globalManager;

    }

    @Override
    public void run() {

        globalManager.getMainGUI().updateStats(globalManager.getSnakeQuantity(),globalManager.getBestSnake().getScore(),globalManager.getSnakeAliveQuantity());
        globalManager.setWinner(globalManager.getBestSnakes(10));

        if(globalManager.getSnakeQuantity() > 0 && globalManager.getSnakeAliveQuantity() < 10){

            globalManager.stopRunnables();
            globalManager.setWinner(globalManager.getBestSnakes(10));
            globalManager.startIA(globalManager.getSnakeQuantity());

        }

    }
}
