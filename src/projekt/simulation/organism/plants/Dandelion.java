package projekt.simulation.organism.plants;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Plant;
import java.awt.*;

public class Dandelion extends Plant {

    public static final int STRENGTH = 0;
    public static final int SPAWN_CHANCES = 3;

    public Dandelion(Vector2d location) {
        super(location, STRENGTH);
    }

    @Override
    public void action() {

        for(int i = 0; i < SPAWN_CHANCES; i++)
        {
            spawn();
        }
    }

    @Override
    public String toString() {
        return "MLECZ";
    }

    @Override
    public Color draw() {
        return Color.YELLOW;
    }

    @Override
    public Dandelion copy() {
        return new Dandelion(location);
    }
}
