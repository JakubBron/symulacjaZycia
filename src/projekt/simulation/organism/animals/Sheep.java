package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Animal;
import java.awt.*;

public class Sheep extends Animal {

    public static final int STRENGTH = 4;
    public static final int INITIATIVE = 4;

    public Sheep(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "OWCA";
    }

    @Override
    public Color draw() {
        return Color.GRAY;
    }

    @Override
    public Organism copy() {
        return new Sheep(location);
    }
}
