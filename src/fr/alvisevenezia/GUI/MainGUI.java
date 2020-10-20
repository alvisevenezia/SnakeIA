package fr.alvisevenezia.GUI;

import fr.alvisevenezia.GUI.snake.SnakePanel;
import fr.alvisevenezia.SNAKE.GlobalManager;
import fr.alvisevenezia.SNAKE.SnakeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.GregorianCalendar;

public class MainGUI extends JFrame implements ActionListener {

    private final double x = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
    private final double y = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

    private MainPanel main;
    private SnakePanel snake;

    private GlobalManager globalManager;

    public MainGUI(GlobalManager globalManager){

        this.globalManager = globalManager;

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
