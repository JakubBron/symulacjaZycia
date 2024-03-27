package projekt.simulation.organism.animals;

import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.Animal;
import java.awt.*;

import static java.lang.Math.random;

public class Turtle extends Animal {

    public static final int STRENGTH = 2;
    public static final int INITIATIVE = 1;
    public static final double MOVE_PROBABILITY = 0.25;
    public static final double PROTECTIVENESS = 5;


    public Turtle(Vector2d location) {
        super(location, STRENGTH, INITIATIVE);
    }

    @Override
    public void action(){
        if(random() < MOVE_PROBABILITY)
        {
            generateRandomMove(1);
        }
    }

    @Override
    public boolean isAttackReflected(Organism other){
        return other.getStrength() < PROTECTIVENESS;
    }


    @Override
    public String toString() {
        return "ZOLW";
    }

    @Override
    public Color draw() {
        return new Color(33, 91, 17);
    }

    @Override
    public Turtle copy() {
        return new Turtle(location);
    }
}
