package projekt.simulation;

import projekt.helpers.Log;
import projekt.helpers.Vector2d;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.plants.*;
import projekt.simulation.organism.animals.*;


import java.util.Vector;

public class World {
    private final int height;
    private final int width;
    private int roundCounter;
    private Move move = Move.STAND_STILL;
    private Type type = Type.Squares;
    private Log log;
    Vector<Organism> entities;
    public enum Move {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        SUPERPOWER,
        STAND_STILL
    }
    public enum Type {
        Squares,
        Hex
    }


    public World(int height, int width, Type type) {
        this.height = height;
        this.width = width;
        entities = new Vector<>();
        log = new Log();
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Organism getCollidingOrganism(Organism org) {

        for (Organism organism : entities)
        {
            if (org.getLocation().equals(organism.getLocation()) && org != organism && org.isAlive())
            {
                return organism;
            }
        }

        return null;
    }

    public Vector2d getFreeSpaceAround(Vector2d p) {

        for (int dy = -1; dy <= 1; dy++)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                Vector2d current = new Vector2d(p.getY() + dy, p.getX() + dx);
                if (type == Type.Hex && ((dy == -1 && dx == -1) || (dy == 1 && dx == -1)))
                {
                    continue;
                }

                if (!current.equals(p) && getOrganismAtPosition(current) == null && !current.outOfBounds(height, width))
                {
                    return current;
                }
            }
        }
        return p;
    }

    public Log getLogs() {
        return log;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
    }

    public Type getType() {
        return type;
    }

    public void makeRound() {
        announceNewRound();
        roundCounter++;
        moveAllEntities();
        removeKilled();
    }

    public void addOrganism(Organism organism) {

        organism.setAge(organism.getAge() + 1);
        organism.setWorld(this);

        entities.add(organism);
    }

    public Organism getOrganismAtPosition(Vector2d p) {

        Organism result = null;

        for (Organism org : entities)
        {
            if (org.getLocation().equals(p) && org.isAlive())
            {
                if (result == null || result.getStrength() < org.getStrength())
                {
                    result = org;
                }
            }
        }
        return result;

    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Move popMove() {

        Move current = move;
        move = Move.STAND_STILL;

        return current;
    }

    public Move getMove() {
        return move;
    }

    public Vector<Organism> getEntities() {
        return entities;
    }

    public static World setOnBoard(Type type) {
        World world = new World(30, 30, type);

        world.addOrganism(new Wolf(new Vector2d(0, 3)));
        world.addOrganism(new Wolf(new Vector2d(0, 4)));

        world.addOrganism(new Sheep(new Vector2d(6, 6)));
        world.addOrganism(new Sheep(new Vector2d(7, 6)));
        world.addOrganism(new Dandelion(new Vector2d(10, 10)));
        world.addOrganism(new Fox(new Vector2d(10, 5)));
        world.addOrganism(new Fox(new Vector2d(10, 6)));
        world.addOrganism(new Fox(new Vector2d(10, 7)));
        world.addOrganism(new Turtle(new Vector2d(20, 20)));
        world.addOrganism(new Grass(new Vector2d(20, 4)));
        world.addOrganism(new Antelope(new Vector2d(30, 5)));
        world.addOrganism(new Antelope(new Vector2d(30, 6)));
        world.addOrganism(new Guarana(new Vector2d(25, 5)));
        world.addOrganism(new Guarana(new Vector2d(25, 6)));

        world.addOrganism(new Belladonna(new Vector2d(0, 0)));
        world.addOrganism(new Belladonna(new Vector2d(1, 0)));

        world.addOrganism(new Hogweed(new Vector2d(21, 24)));
        world.addOrganism(new Human(new Vector2d(25, 25)));

        return world;
    }

    private void moveAllEntities() {

        entities.sort((Organism o1, Organism o2) -> {

            if (o1.getInitiative() == o2.getInitiative())
            {
                return o2.getAge() - o1.getAge();
            }
            return o2.getInitiative() - o1.getInitiative();
        });

        for (int i = 0; i < entities.size(); i++)
        {
            Organism organism = entities.get(i);
            if (organism.isAlive())
            {
                organism.action();
                organism.collision();
            }

            organism.increaseAge();
        }
    }


    private void removeKilled() {

        for (int i = 0; i < entities.size(); i++)
        {
            if (!entities.get(i).isAlive())
            {
                entities.remove(i);
                removeKilled();
                break;
            }
        }
    }

    private void announceNewRound() {

        log.clear();
        for (Organism organism : entities)
        {
            organism.newRound();
        }
    }

}

