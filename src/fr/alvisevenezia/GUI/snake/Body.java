package fr.alvisevenezia.GUI.snake;

import java.awt.*;

public class Body {

    Graphics g;
    boolean isHead;

    public Body(Graphics g,boolean isHead){

        this.g = g;
        this.isHead = isHead;
    }

    public void drawHead(int x,int y){

        g.drawRect(x,y,10,10);

        if(isHead){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.BLUE);
        }

    }

}
