package fr.alvisevenezia.SNAKE;

public enum Direction {

    RIGHT(1,0),RIGHT_UP(1,1),UP(0,1),LEFT_UP(-1,1),LEFT(0,-1),LEFT_DOWN(-1,-1),DOWN(-1,0),RIGHT_DOWN(1,1);

    int xCoeff,yCoeff;

    Direction(int x,int y){

        this.xCoeff = x;
        this.yCoeff = y;

    }

    public int getxCoeff() {
        return xCoeff;
    }

    public int getyCoeff() {
        return yCoeff;
    }

    public int getXCoord(int x,int i){

        int val = x+(xCoeff*i);

        if(val < 0){

            val = 0;
            //x = i-(i3+(direction.getX()*i));

        }else if(val > 49){

            val = 49;
            // x = i+(49-(i3+(direction.getX()*i)));

        }

        return val;
    }

    public int getYCoord(int y,int i){

        int val = y+(yCoeff*i);

        if(val < 0){

            val = 0;
            //x = i-(i3+(direction.getX()*i));

        }else if(val > 49){

            val = 49;
            // x = i+(49-(i3+(direction.getX()*i)));

        }

        return val;
    }

}
