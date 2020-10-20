package fr.alvisevenezia.SNAKE;

import com.sun.source.util.TaskListener;

import java.util.Random;
import java.util.TimerTask;

public class SnakeRunnable extends TimerTask {

    private GlobalManager globalManager;
    public SnakeRunnable(GlobalManager globalManager){

        this.globalManager = globalManager;

    }

    @Override
    public void run() {

        Random  r = new Random();
        int i = r.nextInt(4);

        switch (i){

            case 0:
                System.out.println("DOWN");
                globalManager.getSnakeManager().moovSnake(SnakeMouvement.DOWN);


                break;
            case 1:
                System.out.println("LEFT");
                globalManager.getSnakeManager().moovSnake(SnakeMouvement.LEFT);

                break;
            case 2:
                System.out.println("UP");
                globalManager.getSnakeManager().moovSnake(SnakeMouvement.UP);

                break;
            case 3:

                System.out.println("RIGHT");
                globalManager.getSnakeManager().moovSnake(SnakeMouvement.RIGHT);
                break;

        }

    }
}
