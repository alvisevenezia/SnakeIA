package fr.alvisevenezia.SNAKE;

public enum SnakeMouvement {

    UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0),STAY(0,0);

    int x,y;

    SnakeMouvement(int x,int y){

        this.x = x;
        this.y = y;

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
