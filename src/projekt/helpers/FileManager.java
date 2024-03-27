package projekt.helpers;

import projekt.simulation.World;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.plants.*;
import projekt.simulation.organism.animals.*;

import javax.swing.*;
import java.io.*;

public class FileManager {

    public void save(World world, File file) {

        try
        {
            FileWriter out = new FileWriter(file);
            String typStr = world.getType().equals(World.Type.Hex) ? "HEX" : "KART";

            out.write(world.getRoundCounter() + " " + world.getHeight() + " " + world.getWidth()  + " " + typStr+ "\n");

            for(Organism org : world.getEntities())
            {
                out.write(org.toString() + " " + org.getAge() + " " + org.getLocation().getY() + " " +
                        org.getLocation().getX());

                if(org instanceof Human)
                {
                    out.write(" " + ((Human) org).getSuperpowerCounter());
                }
                out.write("\n");
            }
            out.close();

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"błąd zapisu","błąd",JOptionPane.ERROR_MESSAGE);
        }

    }

    public World read(File file) {

        try
        {
            FileInputStream fs = new FileInputStream(file);
            DataInputStream ds = new DataInputStream(fs);
            BufferedReader in = new BufferedReader(new InputStreamReader(ds));

            String[] worldOprions = in.readLine().split(" ");
            World.Type type = World.Type.Squares;

            if(worldOprions[3].equals("HEX"))
            {
                type = World.Type.Hex;
            }

            World sw = new World(Integer.parseInt(worldOprions[1]),Integer.parseInt(worldOprions[2]), type);
            sw.setRoundCounter(Integer.parseInt(worldOprions[0]));

            String line;
            while((line = in.readLine()) != null)
            {
                Organism org = readOrganism(line);
                if(org != null)
                {
                    sw.addOrganism(org);
                }
            }

            ds.close();
            return sw;

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"błąd wczytywania/składniowy","błąd",JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private Organism readOrganism(String line) throws Exception {

        String[] attributes = line.split(" ");

        Organism newOrganism = allocateUsingNames(attributes[0]);

        if(newOrganism != null)
        {
            newOrganism.setAge(Integer.parseInt(attributes[1]));
            newOrganism.setLocation(new Vector2d(Integer.parseInt(attributes[2]), Integer.parseInt(attributes[3])));

            if(newOrganism instanceof Human)
            {
                ((Human) newOrganism).setSuperpowerCounter(Integer.parseInt(attributes[4]));
            }

            return newOrganism;
        }
        return null;

    }

    private Organism allocateUsingNames(String name) {

        Vector2d p0 = new Vector2d(0,0);
        Organism[] organism = {
                new Human(p0),
                new Wolf(p0),
                new Sheep(p0),
                new Fox(p0),
                new Turtle(p0),
                new Antelope(p0),
                new Grass(p0),
                new Dandelion(p0),
                new Guarana(p0),
                new Belladonna(p0),
                new Hogweed(p0)
        };

        for(Organism org : organism)
        {
            if(org.toString().equals(name))
            {
                return org.copy();
            }
        }
        return null;
    }

}
