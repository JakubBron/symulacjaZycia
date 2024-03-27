package projekt.simulation.organism.plants;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Plant;
import projekt.simulation.organism.Animal;
import java.awt.*;

public class Hogweed extends Plant {

    public static int STRENGTH = 0;

    public Hogweed(Vector2d location) {
        super(location, STRENGTH);
    }

    @Override
    public void addModifier(Organism organism){
        organism.kill();
    }

    @Override
    public void action(){

        for(int y = -1; y <= 1; y++)
        {
            for(int x = -1; x <= 1; x++)
            {
                Organism org = world.getOrganismAtPosition(location.add(new Vector2d(y,x)));
                if(org instanceof Animal)
                {
                    org.kill();
                }
            }
        }
        super.action();
    }

    @Override
    public String toString() {
        return "BARSZCZ_SOSNOWSKIEGO";
    }

    @Override
    public Color draw() {
        return Color.WHITE;
    }

    @Override
    public Hogweed copy() {
        return new Hogweed(location);
    }
}
