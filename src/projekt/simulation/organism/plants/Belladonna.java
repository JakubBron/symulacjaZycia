package projekt.simulation.organism.plants;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Plant;

import java.awt.*;

public class Belladonna extends Plant {
    public static int STRENGTH = 0;

    public Belladonna(Vector2d location) {
        super(location, STRENGTH);
    }

    @Override
    public void addModifier(Organism org) {
        org.kill();
    }

    @Override
    public String toString() {
        return "WILCZE_JAGODY";
    }

    @Override
    public Color draw() {
        return Color.MAGENTA;
    }

    @Override
    public Belladonna copy() {
        return new Belladonna(location);
    }
}
