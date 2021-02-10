package fr.alvisevenezia.SNAKE;

public enum Direction {

    RIGHT(1,0,0),RIGHT_UP(1,-1,1),UP(0,-1,2),LEFT_UP(-1,-1,3),LEFT(-1,0,4),LEFT_DOWN(-1,1,5),DOWN(0,1,6),RIGHT_DOWN(1,1,7);

    int xCoeff,yCoeff,id;

    Direction(int x,int y,int id){

        this.xCoeff = x;
        this.yCoeff = y;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public int[] getCoordsToDisplay(int x,int y,GlobalManager globalManager){

        int[] coord = {x,y};

        if(x == 0 || y == 0){

            coord[0] = getXCoord(x,50,globalManager);
            coord[1] = getYCoord(y,50,globalManager);

        }else{

            int i2 = 0 ;

            for(i2 = 0;i2< globalManager.getSize(); i2++){

                if((getXCoord(x,i2,globalManager) != (x+i2*getxCoeff()))){

                    break;

                }

            }

            int i3 = 0;

            for(i3 = 0;i3< globalManager.getSize(); i3++){

                if((getYCoord(y,i3,globalManager) != (y+i3*getyCoeff()))){

                    break;

                }

            }

            if(i2 >= i3){

                coord[0] = getXCoord(x,i3,globalManager);
                coord[1] = getYCoord(y,i3,globalManager);

            }else if(i2 < i3){

                coord[0] = getXCoord(x,i2,globalManager);
                coord[1] = getYCoord(y,i2,globalManager);

            }

        }

        return coord;

    }

    public int getxCoeff() {
        return xCoeff;
    }

    public int getyCoeff() {
        return yCoeff;
    }

    public int getXCoord(int x,int i,GlobalManager globalManager){

        int val = x+(xCoeff*i);

        if(val < 0){

            val = 0;
            //x = i-(i3+(direction.getX()*i));

        }else if(val > globalManager.getSize()-1){

            val = globalManager.getSize()-1;
            // x = i+(49-(i3+(direction.getX()*i)));

        }

        return val;
    }

    public int getYCoord(int y,int i,GlobalManager globalManager){

        int val = y+(yCoeff*i);

        if(val < 0){

            val = 0;
            //x = i-(i3+(direction.getX()*i));

        }else if(val > globalManager.getSize()-1){

            val = globalManager.getSize()-1;
            // x = i+(49-(i3+(direction.getX()*i)));

        }

        return val;
    }

}
