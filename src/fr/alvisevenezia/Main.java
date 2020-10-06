package fr.alvisevenezia;

import fr.alvisevenezia.GUI.MainGUI;

import javax.swing.*;

public class Main {

    public static MainGUI mainGUI;
    public static JFrame currentFrame;

    public static void main(String[] args) {

        mainGUI = new MainGUI();
        currentFrame = mainGUI;
        mainGUI.openFrame();

    }
}
