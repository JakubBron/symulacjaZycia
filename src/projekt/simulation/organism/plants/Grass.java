package projekt.simulation.organism.plants;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Plant;

import java.awt.*;

public class Grass extends Plant {

    public static final int STRENGTH = 0;

    public Grass(Vector2d location) {
        super(location, STRENGTH);
    }

    @Override
    public String toString() {
        return "TRAWA";
    }

    @Override
    public Color draw() {
        return Color.GREEN;
    }

    @Override
    public Organism copy() {
        return new Grass(location);
    }

}
