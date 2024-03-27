package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Animal;
import java.awt.*;

public class Human extends Animal {

    public static final int STRENGTH = 5;
    public static final int INITIATIVE = 4;
    public static final int SUPERPOWER_DURATION = 5;
    private int superpowerCounter = -6;

    public Human(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public boolean isAttackReflected(Organism org) {
        if(superpowerCounter >= 0)
        {
            world.getLogs().insert("\t" + toString() + "używa umiejetności specjalnej!");
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void action() {

        if(superpowerCounter >= 0)
        {
            world.getLogs().insert("\tPozostało " + superpowerCounter + " tur specjalnych.");
            superpowerCounter--;
        }

        switch(world.popMove())
        {
            case UP:
                changeLocation(new Vector2d(-1, 0));
                break;

            case DOWN:
                changeLocation(new Vector2d(1, 0));
                break;

            case RIGHT:
                changeLocation(new Vector2d(0, 1));
                break;

            case LEFT:
                changeLocation(new Vector2d(0, -1));
                break;

            case SUPERPOWER:

                if(superpowerCounter <= -5)
                {
                    superpowerCounter = SUPERPOWER_DURATION;
                    world.getLogs().insert("\tAktywowano umiejetność specjalną.");
                }
                else
                {
                    world.getLogs().insert("Jeszcze nie możesz użyć tury specjalnej.");
                    superpowerCounter--;
                }
                break;
        }

    }

    public void setSuperpowerCounter(int superpowerCounter) {
        this.superpowerCounter = superpowerCounter;
    }

    @Override
    public String toString() {
        return "CZŁOWIEK";
    }

    @Override
    public Color draw() {
        return new Color(243,133,70,255);
    }


    public int getSuperpowerCounter() {
        return superpowerCounter;
    }

    @Override
    public Organism copy() {
        return new Human(location);
    }
}
