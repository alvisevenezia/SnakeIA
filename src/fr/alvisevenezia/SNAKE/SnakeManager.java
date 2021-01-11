package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.snake.Body;
import fr.alvisevenezia.IA.IAIteration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class SnakeManager {

    private int bodyStart = 2;
    private int currentbody = 2;
    private int newbody = 2;
    private int currentapple = 0;
    private boolean headmooved = false;
    private SnakeMouvement currentmouvement = SnakeMouvement.STAY;
    private SnakeRunnable snakeRunnable;
    private Timer timer;
    private IAIteration iaIteration;
    private boolean alive;

    private int score = 0;
    private int max = 1500;

    private GlobalManager globalManager;

    private int[][] pommes = new int[50][50];
    private int[][] currentsnake = new int[50][50];
    private int[][] newsnake = new int[50][50];
    private ArrayList<Body> parts = new ArrayList<>();

    public SnakeManager(GlobalManager globalManager,IAIteration iaIteration){

        setCurrentbody(bodyStart);
        this.globalManager = globalManager;
        this.iaIteration = iaIteration;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public float[] getQueuePos(){

        for(int i = 0;i< 50;i++){

            for(int i2 = 0;i2 < 50;i2++){

                if(getSnake(i,i2) == currentbody){

                    return new float[]{i, i2};
                }

            }
        }

        return null;
    }

    public int[] getHeadPos(){

        for(int i = 0;i< 50;i++){

            for(int i2 = 0;i2<50;i2++){

                if(getSnake(i,i2) == 1){

                    return new int[]{i,i2};

                }

            }

        }

        return null;

    }

    public void startRunnable(){

        timer = new Timer();
        timer.schedule(new SnakeRunnable(globalManager,iaIteration),1000,1000);

    }

    public void stopRunnable(){

        globalManager.removeOneSnake();
        //timer.cancel();
        setAlive(false);

    }

    public void moovSnake(SnakeMouvement snakeMouvement){

        if(max <= 0){

            stopRunnable();
            return;

        }

        for(int i = 0;i<50;i++) {

            for (int i2 = 0; i2 < 50; i2++) {

                switch (getSnake(i,i2)){

                    case 0:

                        setSnake(i,i2,0);

                        break;

                    case 1:

                        if(i + snakeMouvement.getX() > 49 || i + snakeMouvement.getX() < 0 || i2 + snakeMouvement.getY() > 49 || i2 + snakeMouvement.getY() < 0){

                            stopRunnable();
                            break;

                        }

                        if(getSnake(i + snakeMouvement.getX(), i2 + snakeMouvement.getY()) != 0 || ((currentmouvement.getY()+snakeMouvement.getY() == 0)&& (currentmouvement.getX()+snakeMouvement.getX() == 0)&& currentmouvement != SnakeMouvement.STAY)){

                            stopRunnable();
                            break;

                        }else if(!isHeadmooved()) {


                            if(isApple(i + snakeMouvement.getX(), i2 + snakeMouvement.getY())){

                                newbody = currentbody+1;
                                score += 50;
                                max += 150;

                            }
                            setSnake(i + snakeMouvement.getX(), i2 + snakeMouvement.getY(), 1);
                            setSnake(i, i2, 2);
                            setHeadmooved(true);

                            score += 1;
                            max -= 10;

                        }

                        break;

                    default:

                        if(getSnake(i,i2) == currentbody) {

                            if(currentbody != newbody) {

                                setSnake(i, i2, getSnake(i, i2) + 1);

                            }else{

                                setSnake(i,i2,0);

                            }

                        }else{


                            setSnake(i, i2, getSnake(i, i2) + 1);

                        }
                        break;
                }

            }
        }
        updateBody();
        updateSnake();
        globalManager.getMainGUI().getSnake().repaint();
        currentmouvement = snakeMouvement;

        setHeadmooved(false);

    }

    public void initilizeApple(){

        for(int i = 0;i < 50;i++){

            for(int i2 = 0;i< 50;i++){

                setApple(i,i2,0);

            }

        }

        randomGenerateApple();

    }

    public void randomGenerateApple(){

        int x = globalManager.getRandom().nextInt(50);
        int y = globalManager.getRandom().nextInt(50);

            while(getHeadPos()[0] == x || getHeadPos()[1] == y){

                x = globalManager.getRandom().nextInt(50);
                y = globalManager.getRandom().nextInt(50);

            }

        setApple(x,y,1);
        currentapple++;
    }

    public void setApple(int i,int i2,int i3){

        pommes[i][i2] = i3;

    }

    public boolean isApple(int i,int i2){

        if(pommes[i][i2] == 1){

            return true;

        }

        return false;

    }

    public void updateBody(){

        currentbody = newbody;

    }

    public void createSnake() {



        for(int i = 0;i<50;i++) {

            for (int i2 = 0; i2 < 50; i2++) {

                setSnake(i,i2,0);

            }
        }

        setSnake(25,25,1);
        setSnake(26,25,2);
        updateSnake();

    }

    public void createBodyPart(boolean isHead){


    }

    public int getSnake(int x,int y){

        return currentsnake[x][y];

    }

    public void setSnake(int x,int y,int i){

        newsnake[x][y] = i;

    }

    public void updateSnake(){

        currentsnake = newsnake;

    }

    public int getCurrentbody() {
        return currentbody;
    }

    public void setCurrentbody(int newbody) {
        this.newbody = newbody;
    }

    public boolean isHeadmooved() {
        return headmooved;
    }

    public void setHeadmooved(boolean headmooved) {
        this.headmooved = headmooved;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrentapple() {
        return currentapple;
    }

    public void setCurrentapple(int currentapple) {
        this.currentapple = currentapple;
    }

    public SnakeRunnable getSnakeRunnable() {
        return snakeRunnable;
    }

    public void setSnakeRunnable(SnakeRunnable snakeRunnable) {
        this.snakeRunnable = snakeRunnable;
    }

    public float getAppleDistance(int i3, int i4) {

        float min = 10000000;

        for(int i = 0;i< 50;i++){

            for(int i2 = 0;i2<50;i2++){

                if(isApple(i,i2)){

                    float da = (float)Math.sqrt(((i3-i)*(i3-i))+((i4-i2)*(i4-i2)));

                    if(da < min){

                        min = da;

                    }

                }

            }

        }

        return min;

    }
}