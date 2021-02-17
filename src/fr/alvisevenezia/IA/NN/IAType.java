package fr.alvisevenezia.IA.NN;

public enum IAType {

    GANN("Genetic Algoritm and Neuronal Network");

    String name;

    IAType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
