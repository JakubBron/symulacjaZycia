package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Animal;
import java.awt.*;

public class Fox extends Animal {

    public static final int STRENGTH = 3;
    public static final int INITIATIVE = 7;

    public Fox(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "LIS";
    }

    @Override
    public Color draw() {
        return Color.ORANGE;
    }

    @Override
    public Organism copy() {
        return new Fox(location);
    }

    @Override
    protected boolean canAttackStrengthenerEntity() {
        return true;
    }
}
