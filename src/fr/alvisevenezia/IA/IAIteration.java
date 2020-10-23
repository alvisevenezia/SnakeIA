package fr.alvisevenezia.IA;

import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import java.util.ArrayList;

public class IAIteration {

    private GlobalManager globalManager;
    private int iterationnbr;
    private SnakeManager snakeManager;
    private int firstLayerSize;
    private int secondLayerSize;

    private ArrayList<Object>layers;

    public IAIteration(GlobalManager globalManager,int i){

        this.globalManager = globalManager;
        this.iterationnbr = i;
        layers = new ArrayList<>();

    }

    public Object getLayer(int id){

        return layers.get(id);

    }

    public int getLayerID(Object o){

        int i = 0;

        for(Object o1 : layers){

            if(o == o1 ){


                return i;
            }

            i++;
        }

        return 0;
    }
    public ArrayList<Object> getLayers() {
        return layers;
    }

    public void addLayer(Object o){

        layers.add(o);

    }

    public void setFirstLayerSize(int firstLayerSize) {
        this.firstLayerSize = firstLayerSize;
    }

    public void setSecondLayerSize(int secondLayerSize) {
        this.secondLayerSize = secondLayerSize;
    }

    public int getFirstLayerSize() {
        return firstLayerSize;
    }

    public int getSecondLayerSize() {
        return secondLayerSize;
    }

    public SnakeManager getSnakeManager() {

        return snakeManager;
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

                if(i3 != 0 && i4 != 0){

                    if(i-i3 < 0 || i-i3 >49 || i2-i4 < 0 || i2-i4 >49){

                            data[count*3] = 0;

                    }else{

                        if(i-i3 > 25){

                            if(i-i4 > 25){

                                data[count*3] = (int)Math.sqrt(((50-(i-i3))*(50-(i-i3)))+((50-(i-i4))*(50-(i-i4))));

                            }else{

                                data[count*3] = (int)Math.sqrt(((50-(i-i3))*(50-(i-i3)))+((-(i-i4))*(-(i-i4))));
                            }

                        }else{

                            if(i-i4 > 25){

                                data[count*3] = (int)Math.sqrt(((-(i-i3))*(-(i-i3)))+((50-(i-i4))*(50-(i-i4))));

                            }else{

                                data[count*3] = (int)Math.sqrt(((-(i-i3))*(-(i-i3)))+((-(i-i4))*(-(i-i4))));
                            }
                        }

                    }

                    int[] qpos = snakeManager.getQueuePos();
                    int qd = (int)Math.sqrt(((i-qpos[0])*(i-qpos[0]))+((i2-qpos[1])*(i2-qpos[1])));

                    data[(count*3)+1] = qd;

                    data[(count*3)+2] = snakeManager.getAppleDistance(i3,i4);

                  count++;
                }

            }

        }

        return data;

    }




}
