package fr.alvisevenezia.GUI;

import fr.alvisevenezia.GUI.panels.snake.SnakeButtonPanel;
import fr.alvisevenezia.GUI.panels.menu.MenuPanel;
import fr.alvisevenezia.GUI.panels.snake.SnakePanel;
import fr.alvisevenezia.GUI.panels.stats.StatPanel;
import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    private final double x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    private final double y = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    private SnakeButtonPanel main;
    private SnakePanel snake;
    private StatPanel statPanel;
    private MenuPanel menuPanel;
    private JPanel mainPanel;
    private int scale;

    private GlobalManager globalManager;

    public MainGUI(GlobalManager globalManager){

        this.globalManager = globalManager;

        this.setSize((int)x,(int)y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SnakeIA");

    }

    public void createMenuGUI(){

        menuPanel = new MenuPanel(globalManager);
        menuPanel.setup();
        menuPanel.setVisible(false);
        this.getContentPane().add(menuPanel);

    }

    public void createSnakeGUI(){

        scale = 500/globalManager.getSize();

        statPanel = new StatPanel(globalManager);
        statPanel.setVisible(false);
        statPanel = new StatPanel(globalManager);
        statPanel.setBounds(100, 75, 300,75);
        statPanel.setBackground(Color.YELLOW);
        statPanel.setVisible(false);

        main = new SnakeButtonPanel(globalManager);
        main.setVisible(false);

        this.setSize((int)x,(int)y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SnakeIA");

        snake = new SnakePanel(globalManager);
        snake.setBounds(600,75,globalManager.getSize()*scale,globalManager.getSize()*scale);
        snake.setBackground(Color.GRAY);
        snake.setVisible(false);

        this.getContentPane().add(statPanel);
        this.getContentPane().add(snake);
        this.getContentPane().add(main);


    }


    public void displayMainPanel(){

        this.getContentPane().setBackground(Color.GREEN);

        menuPanel.setVisible(false);
        snake.setVisible(true);
        main.setVisible(true);
        statPanel.setVisible(true);


    }

    private void displayMenuPanel() {

        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        snake.setVisible(false);
        main.setVisible(false);
        statPanel.setVisible(false);
        menuPanel.setVisible(true);

    }

    public int getScale() {
        return scale;
    }

    public void updateStats(int nbr, String bs, int a, int nbGen, int moy){

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

    public boolean openFrame(GUIType guiType){

        switch (guiType){

            case Snake:

                displayMainPanel();
                break;

            default:

                displayMenuPanel();
                break;

        }

        this.setVisible(true);

        return true;

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {


    }

    public SnakeButtonPanel getMain() {
        return main;
    }

    public void setMain(SnakeButtonPanel main) {
        this.main = main;
    }

    public SnakePanel getSnake() {
        return snake;
    }

    public void setSnake(SnakePanel snake) {
        this.snake = snake;
    }
}
