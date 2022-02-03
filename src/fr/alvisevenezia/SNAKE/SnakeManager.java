package fr.alvisevenezia.SNAKE;

import fr.alvisevenezia.IA.GA.GeneticAlgoritmManager;

import java.math.BigInteger;
import java.util.ArrayList;

public class SnakeManager {

    private int bodyStart = 2;
    private int appleID = 0;
    private int currentbody = 2;
    private int lifetime = 0;
    private int newbody = 2;
    private int currentapple = 0;
    private int moovCount = 0;
    private boolean headmooved = false;
    private SnakeMouvement currentmouvement = SnakeMouvement.STAY;
    private boolean alive;
    private int score = 0;
    private int max;

    private GeneticAlgoritmManager geneticAlgoritmManager;

    private int[][] pommes;
    private int[][] currentsnake;
    private int[][] newsnake;
    private int[] apple = {0,0,0,0,0,0,0,0};
    private ArrayList<SnakeMouvement> lastMvt = new ArrayList<>();

    public SnakeManager(GeneticAlgoritmManager geneticAlgoritmManager){

        setCurrentbody(bodyStart);
        this.geneticAlgoritmManager = geneticAlgoritmManager;
        pommes = new int[geneticAlgoritmManager.getGlobalManager().getSize()][geneticAlgoritmManager.getGlobalManager().getSize()];
        currentsnake = new int[geneticAlgoritmManager.getGlobalManager().getSize()][geneticAlgoritmManager.getGlobalManager().getSize()];
        newsnake = new int[geneticAlgoritmManager.getGlobalManager().getSize()][geneticAlgoritmManager.getGlobalManager().getSize()];
        max = geneticAlgoritmManager.getGlobalManager().getSize()*2;

    }

    public boolean isTurning(){

        int i = 0;

        if(lastMvt.size() == 8) {

            for (int ID = 0; ID < 4; ID++) {

                i += lastMvt.get(ID).getX() + lastMvt.get(ID).getY();
            }

            if (i == 0) {

                int i2 = 0;

                for (int ID = 4; ID < 8; ID++) {

                    i2 += lastMvt.get(ID).getX() + lastMvt.get(ID).getY();

                }

                if (i2 == 0) {

                    return true;

                }

            }

        }

        return false;

    }

    public void addMouvement(SnakeMouvement snakeMouvement){

        if(lastMvt.size() > 8){

            lastMvt.remove(0);

        }

        lastMvt.add(snakeMouvement);

    }

    public int getAppleID() {
        return appleID;
    }

    public int getMoovCount() {
        return moovCount;
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

        for(int i = 0;i< geneticAlgoritmManager.getGlobalManager().getSize();i++){

            for(int i2 = 0;i2<geneticAlgoritmManager.getGlobalManager().getSize();i2++){

                if(getSnake(i,i2) == 1){

                    return new int[]{i,i2};

                }

            }

        }

        return null;

    }

    public int[] getNewHeadPos(){

        for(int i = 0;i< geneticAlgoritmManager.getGlobalManager().getSize();i++){

            for(int i2 = 0;i2<geneticAlgoritmManager.getGlobalManager().getSize();i2++){

                if(getNewSnake(i,i2) == 1){

                    return new int[]{i,i2};

                }

            }

        }

        return null;

    }

    public void stopRunnable(){

        geneticAlgoritmManager.removeOneSnake();
        setAlive(false);

    }

    //from greerviau/SnakeAI
    public BigInteger calculateFitness(){

        BigInteger fitness = BigInteger.ZERO;

        if(score < 1){

            fitness = BigInteger.valueOf(lifetime);
            fitness = fitness.pow(2);

            BigInteger powOfTwo = BigInteger.valueOf(2);
            powOfTwo = powOfTwo.pow(score);

            fitness = fitness.multiply(powOfTwo);

            //fitness = (int)(Math.floor(lifetime*lifetime)*Math.pow(2,score));

            if(fitness.compareTo(BigInteger.valueOf(5000)) == 1){

                fitness = BigInteger.valueOf(5000);

            }

        }else if(score == 1 || score  == 2){

            fitness = BigInteger.valueOf(lifetime);
            fitness = fitness.pow(2);

            BigInteger powOfTwo = BigInteger.valueOf(2);
            powOfTwo = powOfTwo.pow(score);

            fitness = fitness.multiply(powOfTwo);


        }else{

            fitness = BigInteger.valueOf(lifetime);
            fitness = fitness.pow(2);

            BigInteger powOfTwo = BigInteger.valueOf(2);
            powOfTwo = powOfTwo.pow(score);

            fitness = fitness.multiply(powOfTwo);
            fitness = fitness.multiply(BigInteger.valueOf(score-1));

           // fitness = (int) (Math.floor(lifetime*lifetime)*Math.pow(2,10)*(score-1));

        }

        return fitness;
    }

    public void moovSnake(SnakeMouvement snakeMouvement){

        if(isTurning()){

            stopRunnable();
            return;

        }

        if(max <= 0){

            stopRunnable();
            return;

        }
        moovCount++;
        lifetime++;
        max--;

        for(int i = 0;i<geneticAlgoritmManager.getGlobalManager().getSize();i++) {

            for (int i2 = 0; i2 < geneticAlgoritmManager.getGlobalManager().getSize(); i2++) {

                switch (getSnake(i,i2)){

                    case 0:

                        setSnake(i,i2,0);

                        break;

                    case 1:

                        if(i + snakeMouvement.getX() > geneticAlgoritmManager.getGlobalManager().getSize()-1 || i + snakeMouvement.getX() < 0 || i2 + snakeMouvement.getY() > geneticAlgoritmManager.getGlobalManager().getSize()-1 || i2 + snakeMouvement.getY() < 0){

                            stopRunnable();
                            appleID = 0;
                            break;

                        }

                        if(getSnake(i + snakeMouvement.getX(), i2 + snakeMouvement.getY()) != 0 || ((currentmouvement.getY()+snakeMouvement.getY() == 0)&& (currentmouvement.getX()+snakeMouvement.getX() == 0)&& currentmouvement != SnakeMouvement.STAY)){

                            stopRunnable();
                            appleID = 0;
                            break;

                        }else if(!isHeadmooved()) {


                            if(geneticAlgoritmManager.isApple(appleID,i + snakeMouvement.getX(), i2 + snakeMouvement.getY())){

                                setApple(i + snakeMouvement.getX(), i2 + snakeMouvement.getY(),0);
                                appleID++;
                                setApple(geneticAlgoritmManager.getAppleCoord(appleID)[0],geneticAlgoritmManager.getAppleCoord(appleID)[1],1);
                                newbody = currentbody+1;
                                score ++;
                                max += 75;

                            }
                            setSnake(i + snakeMouvement.getX(), i2 + snakeMouvement.getY(), 1);
                            setSnake(i, i2, 2);
                            setHeadmooved(true);

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

        setHeadmooved(false);

        //geneticAlgoritmManager.getGlobalManager().getMainGUI().getSnake().repaint();

    }

    public void resetMvt(){

        newbody = currentbody;
        newsnake = currentsnake;
        alive = true;

    }

    public int[][] getSnakeMatrix(){

        return newsnake;

    }

    public int getCurrentbody() {
        return currentbody;
    }

    public int getNewbody() {
        return newbody;
    }

    public void updateAll(SnakeMouvement snakeMouvement){

        currentmouvement = snakeMouvement;
        addMouvement(currentmouvement);
        updateSnake();
        updateBody();

    }

    public void initilizeApple(){

        for(int i = 0;i < geneticAlgoritmManager.getGlobalManager().getSize();i++){

            for(int i2 = 0;i< geneticAlgoritmManager.getGlobalManager().getSize();i++){

                setApple(i,i2,0);

            }

        }

     //   randomGenerateApple();

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

        for(int i = 0;i<geneticAlgoritmManager.getGlobalManager().getSize();i++) {

            for (int i2 = 0; i2 < geneticAlgoritmManager.getGlobalManager().getSize(); i2++) {

                setSnake(i,i2,0);

            }
        }

        setSnake(geneticAlgoritmManager.getGlobalManager().getSize()/2,geneticAlgoritmManager.getGlobalManager().getSize()/2,1);
        setSnake((geneticAlgoritmManager.getGlobalManager().getSize()/2)+1,geneticAlgoritmManager.getGlobalManager().getSize()/2,2);
        updateSnake();

    }

    public int getNewSnake(int x,int y){

        return newsnake[x][y];

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

    public void setCurrentbody(int newbody) {
        this.newbody = newbody;
    }

    public boolean isHeadmooved() {
        return headmooved;
    }

    public void setHeadmooved(boolean headmooved) {
        this.headmooved = headmooved;
    }

    public int[] getApple() {
        return apple;
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

    public float getAppleDistance(int i3, int i4,Direction direction) {

        int posX = i3;
        int posY = i4;

        for (int i = 0;i<geneticAlgoritmManager.getGlobalManager().getSize();i++){

            posX = direction.getXCoord(i3,i,geneticAlgoritmManager.getGlobalManager().getSize());
            posY = direction.getYCoord(i4,i,geneticAlgoritmManager.getGlobalManager().getSize());

            if(geneticAlgoritmManager.getAppleCoord(appleID)[0] == posX && geneticAlgoritmManager.getAppleCoord(appleID)[1] == posY){

                apple[direction.getId()] = 1;
                return 1;

            }

        }

        apple[direction.getId()] = 0;
        return 0;

/*        int posX = i3+(direction.getxCoeff()*1);
        int posY = i3+(direction.getyCoeff()*1);
        int appleX = globalManager.getAppleCoord(appleID)[0];
        int appleY = globalManager.getAppleCoord(appleID)[1];

        return (float)Math.sqrt(((posX-appleX)*(posX-appleX))+((posY-appleY)*(posY-appleY)));*/

    }

    public float getBordureDistance(int i2,int i3,Direction direction){

        int x = i2+direction.getxCoeff();
        int y = i3+direction.getyCoeff();

        if(x > geneticAlgoritmManager.getGlobalManager().getSize()-1 || x < 0 || y > geneticAlgoritmManager.getGlobalManager().getSize()-1 || y < 0){

            return 1;

        }else{

            return 0;

        }

     /*   if(globalManager.getSize()-i2 > globalManager.getSize()/2){

            if(globalManager.getSize()-i3 > globalManager.getSize()/2){

                return (float)Math.sqrt(((i2)*(i2))+((i3)*(i3)));

            }else{

                return (float)Math.sqrt(((i2)*(i2)+((globalManager.getSize()-i3))*(globalManager.getSize()-i3)));
            }

        }else{

            if(globalManager.getSize()-i3 > globalManager.getSize()/2){

                return (float)Math.sqrt(((globalManager.getSize()-i2)*(globalManager.getSize()-i2))+(i3*i3));

            }else{

                return (float)Math.sqrt(((globalManager.getSize()-i2)*(globalManager.getSize()-i2))+((globalManager.getSize()-i3)*(globalManager.getSize()-i3)));
            }

        }*/

    }

    public float getBodyDistance(int i2, int i3, Direction direction){

       // for(int i = 1;i<50;i++){

            int x = direction.getXCoord(i2,1,geneticAlgoritmManager.getGlobalManager().getSize());
            int y = direction.getYCoord(i3,1,geneticAlgoritmManager.getGlobalManager().getSize());

            if(getSnake(x,y) == currentbody){

                return 0;

            }

      //  }

        return 1;

    }
}