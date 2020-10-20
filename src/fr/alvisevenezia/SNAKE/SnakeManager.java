package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.snake.Body;

import java.awt.*;
import java.util.ArrayList;

public class SnakeManager {

    private int bodyStart = 2;
    private int currentbody = 2;
    private int newbody = 2;
    private SnakeMouvement currentmouvement = SnakeMouvement.UP;

    private GlobalManager globalManager;

    private int[][] pommes = new int[50][50];
    private int[][] currentsnake = new int[50][50];
    private int[][] newsnake = new int[50][50];
    private ArrayList<Body> parts = new ArrayList<>();

    public SnakeManager(GlobalManager globalManager){

        setCurrentbody(bodyStart);
        this.globalManager = globalManager;
    }

    public void moovSnake(SnakeMouvement snakeMouvement){

        currentmouvement = snakeMouvement;

        for(int i = 0;i<50;i++) {

            for (int i2 = 0; i2 < 50; i2++) {

                if(getSnake(i,i2) == 1){

                    System.out.println("HEAD: "+i+","+i2);
                    System.out.println("NEXT: "+(i+snakeMouvement.getX())+","+(i2+snakeMouvement.getY())+": "+getSnake(i+snakeMouvement.getX(),i2+snakeMouvement.getY()));
                    System.out.println("OLD:"+(i+snakeMouvement.getX())+","+(i2+snakeMouvement.getY())+": "+currentsnake[i+snakeMouvement.getX()][i2+snakeMouvement.getY()]);
                    if(i+snakeMouvement.getX() < 50 && i2+snakeMouvement.getY() < 50 && i+snakeMouvement.getX() >= 0 && i2+snakeMouvement.getY() >= 0){

                        if(getSnake(i+snakeMouvement.getX(),i2+snakeMouvement.getY()) != 0){

                            globalManager.stopRunnable();
                            System.out.println("GAME OVER: "+i+","+i2);
                            System.out.println("TU AS MANGE TON SERPENT");
                            break;

                        }else {

                            newsnake[i + snakeMouvement.getX()][i2 + snakeMouvement.getY()] = 1;
                         //   setSnake(i + snakeMouvement.getX(), i2 + snakeMouvement.getY(), 1);
                            setSnake(i, i2, 2);
                        }
                    }else{

                        globalManager.stopRunnable();
                        System.out.println("GAME OVER: "+i+","+i2);
                        break;

                    }



                }else if(getSnake(i,i2) <currentbody && getSnake(i,i2) != 0){

                    setSnake(i,i2,getSnake(i,i2)+1);

                }else if(getSnake(i,i2) == currentbody ){

                    if(currentbody == newbody){

                        setSnake(i, i2, 0);

                    }else {

                        setSnake(i, i2, getSnake(i, i2) + 1);
                        System.out.println("NOUVEAU CORPS EN " + i + "," + i2);

                    }

                }else if(getSnake(i,i2) == 0){

                    setSnake(i,i2,0);
                }

            }
        }

        updateBody();
        updateSnake();
        globalManager.getMainGUI().getSnake().repaint();

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


}