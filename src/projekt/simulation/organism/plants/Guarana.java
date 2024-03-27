package projekt.simulation.organism.plants;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Plant;
import java.awt.*;

public class Guarana extends Plant {

    public static final int STRENGTH = 0;
    public static final int POWER_INCREASE_VALUE = 3;

    public Guarana(Vector2d location) {
        super(location, STRENGTH);
    }

    @Override
    public void addModifier(Organism organism){
        organism.setStrength(organism.getStrength() + POWER_INCREASE_VALUE);
    }

    @Override
    public String toString() {
        return "GUARANA";
    }

    @Override
    public Color draw() {
        return new Color(198, 76, 81);
    }

    @Override
    public Guarana copy() {
        return new Guarana(location);
    }
}
