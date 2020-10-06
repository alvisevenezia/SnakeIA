package fr.alvisevenezia.GUI;

import javax.swing.*;

public class MainGUI extends JFrame {

    private final int x = 900;
    private final int y = 900;

    public MainGUI(){

        this.setSize(x,y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public boolean closeFrame(){

        this.closeFrame();

        return true;

    }

    public boolean openFrame(){

        this.setVisible(true);

        return true;

    }
}
