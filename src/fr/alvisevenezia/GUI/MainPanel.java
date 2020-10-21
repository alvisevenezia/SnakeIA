package fr.alvisevenezia.GUI;

import fr.alvisevenezia.SNAKE.GlobalManager;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private GlobalManager globalManager;
    private JLabel score;
    private JLabel max;

    public MainPanel(GlobalManager globalManager){

        this.globalManager = globalManager;

        this.setBounds(100,150,300,(int)(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()-300));
        this.setBackground(Color.YELLOW);

        JButton button = new JButton();
        button.setText("Lancer Snake");
        button.addActionListener(globalManager);

        this.add(button);

    }

    public void updateInfo(){

        for(Component component : this.getComponents()){

            if(component instanceof JLabel){

                this.remove(component);

            }

        }

        score = new JLabel("SCORE: "+globalManager.getBestSnake().getScore());
        max = new JLabel("MAX: "+globalManager.getBestSnake().getMax());

        this.add(score);
        this.add(max);

        this.repaint();

    }

    public void createInfo(){

        score = new JLabel("SCORE: "+globalManager.getBestSnake().getScore());
        max = new JLabel("MAX: "+globalManager.getBestSnake().getMax());

        this.add(score);
        this.add(max);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



    }
}
