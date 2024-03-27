package projekt.simulation.organism;

import projekt.helpers.Vector2d;
import projekt.simulation.World;
import java.awt.*;

abstract public class Organism {

    protected int strength;
    protected int initiative;
    protected int age = 0;
    protected boolean isAlive = true;

    protected World world;
    protected Vector2d location;

    protected Organism(Vector2d location, int strength, int initiative) {
        this.location = location;
        this.strength = strength;
        this.initiative = initiative;

    }

    public Vector2d getLocation() {
        return location;
    }

    public void setLocation(Vector2d location) {
        this.location = location;
    }


    public int getInitiative() {
        return initiative;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        world.getLogs().insert(toString() + " umiera.");
        isAlive = false;
    }

    public int getAge() {
        return age;
    }

    public void addModifier(Organism organism) {

    }

    public boolean isAttackReflected(Organism org) {
        return false;
    }

    public boolean hasEscaped() {
        return false;
    }

    public boolean escape() {
        if(hasEscaped() == true)
        {
            Vector2d newLocation = world.getFreeSpaceAround(location);
            if(newLocation == location)
            {
                return false;
            }
            setLocation(newLocation);

            return true;
        }

        return false;
    }


    public void increaseAge() {
        age++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    abstract public String toString();

    public abstract void action();
    public abstract void collision();
    public abstract Color draw();
    public abstract void newRound();

    public abstract Organism copy();
    protected boolean canAttackStrengthenerEntity() {
        return false;
    }
}
