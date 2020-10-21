package fr.alvisevenezia.IA;

import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

public class IAIteration {

    private GlobalManager globalManager;
    private int iterationnbr;
    private SnakeManager snakeManager;

    public IAIteration(GlobalManager globalManager,int i){

        this.globalManager = globalManager;
        this.iterationnbr = i;

    }

    public void setSnakeManager(SnakeManager snakeManager){

        this.snakeManager = snakeManager;

    }

    public int[] getSnakeInfo(int i,int i2){//i --> posX i2 --> posY

        int[] data = new int[24];
        int count = 0;
        //data:
        //distance bordure
        //distance queue
        //distance pomme la plus proche

        for(int i3 = -1;i3<2;i3++){

            for(int i4 = -1;i4<2;i4++){

                if(i3 == 0 && i4 == 0){

                    if(i-i3 < 0 || i-i3 >49 || i2-i4 < 0 || i2-i4 >49){

                            data[count*3] = 0;

                    }

                    int[] qpos = snakeManager.getQueuePos();
                    int qd = (int)Math.sqrt(((i-qpos[0])*(i-qpos[0]))+((i2-qpos[1])*(i2-qpos[1])));

                    data[(count*3)+1] = qd;

                    data[(count*3)+2] = snakeManager.getAppleDistance(i3,i4);

                  count++;
                }

            }

        }

        return null;

    }




}
