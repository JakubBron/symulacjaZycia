package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Animal;
import java.awt.*;

public class Wolf extends Animal {

    public static final int STRENGTH = 9;
    public static final int INITIATIVE = 5;


    public Wolf(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "WILK";
    }

    @Override
    public Color draw() {
        return Color.RED;
    }

    @Override
    public Wolf copy() {
        return new Wolf(location);
    }
}
