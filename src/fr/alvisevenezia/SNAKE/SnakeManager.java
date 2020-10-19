package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.GUI.snake.Body;

import java.util.ArrayList;

public class SnakeManager {

    private int bodyStart = 2;
    private int currentbody;

    private int[][] pommes = new int[50][50];
    private int[][] snake = new int[50][50];
    private ArrayList<Body> parts = new ArrayList<>();

    public SnakeManager(){

        currentbody = bodyStart;
    }


    public void createSnake(){

        for(int i = 0;i < 50;i++){

            for(int i2 = 0;i2 < 50;i++){

                switch(snake[i][i2]){

                    case 1:



                        break;

                }



            }

        }

    }

    public void createBodyPart(boolean isHead){



    }

    public int getCurrentbody() {
        return currentbody;
    }

    public void setCurrentbody(int currentbody) {
        this.currentbody = currentbody;
    }


}
