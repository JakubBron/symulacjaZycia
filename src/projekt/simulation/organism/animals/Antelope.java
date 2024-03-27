package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Animal;
import java.awt.*;

import static java.lang.Math.random;

public class Antelope extends Animal {

    public static final int STRENGTH = 4;
    public static final int INITIATIVE = 4;
    public static final int RANGE_MAX = 2;
    public static final double ESCAPE_CHANCES = 0.5;

    public Antelope(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public void action() {
        generateRandomMove(RANGE_MAX);
    }

    @Override
    public String toString() {
        return "ANTYLOPA";
    }

    @Override
    public Color draw() {
        return new Color(213, 128, 58);
    }

    @Override
    public Antelope copy() {
        return new Antelope(location);
    }

    @Override
    public boolean hasEscaped() {
        return (random() < ESCAPE_CHANCES);
    }

}
