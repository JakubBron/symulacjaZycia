package projekt.simulation.organism;

import projekt.helpers.Vector2d;
import static java.lang.Math.random;

public abstract class Plant extends Organism {

    static final int DEFAULT_INITIATIVE = 0;
    static final double PLANT_SPAWN_CHANCES = 0.05;

    public Plant(Vector2d location, int strength) {
        super(location, strength, DEFAULT_INITIATIVE);
    }
    @Override
    public void action() {
        spawn();
    }

    @Override
    public void collision() {

    }

    @Override
    public void newRound() {

    }

    protected void spawn(){

        if(random() < PLANT_SPAWN_CHANCES)
        {
            Vector2d freeSpace = world.getFreeSpaceAround(location);
            if(freeSpace == location)
            {
                return;
            }

            Organism org = copy();
            org.setAge(0);

            org.setLocation(freeSpace);
            world.addOrganism(org);
        }
    }
}
