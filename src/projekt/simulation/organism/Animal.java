package projekt.simulation.organism;

import projekt.helpers.Vector2d;
import projekt.simulation.World;

import static java.lang.Math.random;

abstract public class Animal extends Organism {

    private boolean procreated = false;
    private Vector2d previousLocation;

    public Animal(Vector2d location, int strength, int initiative) {
        super(location,strength,initiative);
    }
    @Override
    public void action() {
        generateRandomMove(1);
    }

    @Override
    public void collision() {
        Organism other = world.getCollidingOrganism(this);
        if(other == null)
        {
            return;
        }

        if(other.toString().equals(toString()))
        {
            procreate((Animal) other);
        }
        else
        {
            fightWith(other);
        }
    }

    @Override
    public void newRound() {
        procreated = false;
    }

    protected void generateRandomMove(int range) {
        if(canAttackStrengthenerEntity() == true && areAllNeighboursStrengthen() == true)
        {
            return;
        }

        int[] position = {-1 * range, 0, range};

        Vector2d displacement = new Vector2d(0,0);
        Vector2d previousLocation = new Vector2d(location.getY(), location.getX());

        do
        {
            int randX = position[(int) (random() * 3)];
            int randY = position[(int) (random() * 3)];

            displacement = new Vector2d(randY,randX);
            changeLocation(displacement);

        }
        while( previousLocation.equals(location) || (canAttackStrengthenerEntity() && world.getCollidingOrganism(this) != null &&
                world.getCollidingOrganism(this).getStrength() > getStrength()) );

    }

    protected void changeLocation(Vector2d displacement) {
        if(world.getType() == World.Type.Hex && (displacement.equals(new Vector2d(-1,-1)) || displacement.equals(new Vector2d(1,-1))))
        {
            return;
        }

        if(!location.add(displacement).outOfBounds(world.getHeight(), world.getWidth()))
        {
            previousLocation = new Vector2d(location.getY(), location.getX());
            location.push_back(displacement);
        }
    }

    private void fightWith(Organism drugi) {

        if(escape() || drugi.escape())
            return;

        if(getStrength() < drugi.getStrength())
        {
            if(isAttackReflected(drugi))
            {
                undoMove();
                return;
            }
            world.getLogs().insert(drugi.toString() + " zabija " + toString());

            kill();
            addModifier(drugi);

        }
        else
        {
            if(drugi.isAttackReflected(this))
            {
                undoMove();
                return;
            }

            world.getLogs().insert(toString() + " zabija " + drugi.toString());

            drugi.kill();
            drugi.addModifier(this);
        }
    }

    private void procreate(Animal entity) {

        if(entity.getAge() == 0)
        {
            return;
        }

        Organism org = copy();
        undoMove();

        Vector2d birthplace = world.getFreeSpaceAround(entity.getLocation());

        if(birthplace.equals(entity.getLocation()) || procreated==true || entity.procreated==true)
        {
            return;
        }

        org.setLocation(birthplace);
        org.setAge(-1);

        world.addOrganism(org);
        procreated = true;
        entity.procreated = true;
    }

    private void undoMove() {
        setLocation(previousLocation);
    }

    private boolean areAllNeighboursStrengthen() {

        for(int y = -1; y <= 1; y++)
        {
            for(int x = -1; x <= 1; x++)
            {
                Vector2d pol = new Vector2d(y,x);

                Organism org = world.getOrganismAtPosition(location.add(pol));
                if(org != this && (org == null || org.getStrength() <= strength))
                {
                    return false;
                }
            }
        }

        return true;
    }
}
