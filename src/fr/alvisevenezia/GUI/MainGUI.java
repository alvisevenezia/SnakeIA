package fr.alvisevenezia.GUI;

import fr.alvisevenezia.GUI.snake.SnakePanel;
import fr.alvisevenezia.GUI.stats.StatPanel;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    private final double x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    private final double y = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    private MainPanel main;
    private SnakePanel snake;
    private StatPanel statPanel;

    private GlobalManager globalManager;

    public MainGUI(GlobalManager globalManager){

        this.globalManager = globalManager;

        statPanel = new StatPanel(globalManager);
        statPanel.setVisible(false);
        statPanel = new StatPanel(globalManager);
        statPanel.setBounds(100, 75, 300,100);
        statPanel.setBackground(Color.YELLOW);
        this.getContentPane().add(statPanel);

        main = new MainPanel(globalManager);

        this.setSize((int)x,(int)y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SnakeIA");

        this.getContentPane().setBackground(Color.GREEN);
        this.getContentPane().setLayout(null);
        this.getContentPane().add(main);

        snake = new SnakePanel(globalManager);

        snake.setBounds(600,75,500,500);
        snake.setBackground(Color.GRAY);

        this.getContentPane().add(snake);


    }

    public void updateStats(int nbr,int bs,int a,int nbGen,int moy){

        statPanel.update(nbr,bs,a,nbGen,moy);

        /*statPanel.setVisible(false);
        statPanel = new StatPanel(globalManager,nbr,bs,a,nbGen);

        statPanel.setBounds(100, 75, 300,100);
        statPanel.setBackground(Color.YELLOW);

        this.getContentPane().add(statPanel);*/

    }

    public void updateBests(int bestScore1,int bestScore2){

        statPanel.updateBests(bestScore1,bestScore2);

    }

    public boolean openFrame(){

        this.setVisible(true);

        return true;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


    }

    public MainPanel getMain() {
        return main;
    }

    public void setMain(MainPanel main) {
        this.main = main;
    }

    public SnakePanel getSnake() {
        return snake;
    }

    public void setSnake(SnakePanel snake) {
        this.snake = snake;
    }
}
