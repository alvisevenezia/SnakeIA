package fr.alvisevenezia.SNAKE;

import com.sun.source.util.TaskListener;
import fr.alvisevenezia.IA.IAIteration;

import java.util.Random;
import java.util.TimerTask;

public class SnakeRunnable extends TimerTask {

    private GlobalManager globalManager;
    private IAIteration iaIteration;
    public SnakeRunnable(GlobalManager globalManager, IAIteration iaIteration){

        this.globalManager = globalManager;
        this.iaIteration = iaIteration;

    }

    @Override
    public void run() throws NullPointerException {

        Random r = new Random();
        int n = r.nextInt(3);

        if(iaIteration.getSnakeManager().getCurrentapple() <= 3){

            if(n == 0 && iaIteration.getSnakeManager().getCurrentapple() < 4){

                iaIteration.getSnakeManager().generateApple();

            }

        }

        for(int i = 0;i<iaIteration.getLayers().size();i++){

            iaIteration.getLayer(i).compute();

        }

        iaIteration.getSnakeManager().moovSnake(iaIteration.getMouvement());

    }
}
