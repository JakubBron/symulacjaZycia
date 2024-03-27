package projekt.gui;

import projekt.helpers.Log;
import projekt.helpers.Vector2d;
import projekt.simulation.World;
import projekt.simulation.organism.Organism;
import projekt.simulation.organism.plants.*;
import projekt.simulation.organism.animals.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowPrinterAndHandler extends JPanel implements MouseListener, KeyListener {

    private static final Color COLOT_BACKGROUND = new Color(0,0,0);
    private static final Color COLOR_MESSAGES = new Color(255,200,200);

    private JPopupMenu newOrganismMenu;

    private World world;
    private int w;
    private int h;

    private Vector2d newPosition;
    private final int windowHeight;
    private int entitySize;


    public WindowPrinterAndHandler(int windowHeight, World world) {

        this.w = world.getHeight();
        this.h = world.getWidth();
        this.windowHeight = windowHeight;
        this.world = world;

        this.newPosition = new Vector2d(0,0);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        initiatePopupMenu();

    }

    private void initiatePopupMenu(){

        newOrganismMenu = new JPopupMenu();

        Vector2d p0 = new Vector2d(0,0);

        Organism[] organisms = {

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


        for(Organism el : organisms){

            JMenuItem elMenu = new JMenuItem(el.toString());

            elMenu.setBackground(el.draw());

            elMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    placeEntity(el);
                }

            });

            newOrganismMenu.add(elMenu);
        }
    }


    public Log getLog() {
        return world.getLogs();
    }

    public void setWorld(World world) {

        this.world = world;
        this.h = world.getWidth();
        this.w = world.getHeight();

        paint(this.getGraphics());
    }

    public void nextTurn() {
        world.makeRound();
        paint(this.getGraphics());
        System.out.print(world.getLogs().print());
        requestFocus();
    }

    public boolean isHuman() {

        for(Organism org : world.getEntities())
        {
            if(org instanceof Human)
            {
                return true;
            }
        }

        return false;
    }


    @Override
    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 16));
        g.drawString("Author: Jakub Bronowski, 193208",600, 16);

        g.setColor(COLOT_BACKGROUND);
        entitySize = windowHeight / w;
        g.fillRect(0,0, h * entitySize, w * entitySize);

        for(int y = 0; y < w; y++)
        {
            for(int x = 0; x < h; x++)
            {
                Organism org = world.getOrganismAtPosition(new Vector2d(y,x));
                if(org != null)
                {
                    g.setColor(org.draw());
                    if(world.getType() == World.Type.Squares)
                    {
                        g.fillRect(x*entitySize,y*entitySize, entitySize, entitySize);
                    }
                    else
                    {
                        int[] xPoints = new int[6];
                        int[] yPoints = new int[6];
                        double xtemp = x;
                        if(y %2 == 0)
                        {
                            xtemp = x + 0.5;
                        }

                        for (int i = 0; i < 6; i++)
                        {
                            int xval = (int) (xtemp * entitySize + entitySize /2 * Math.sin(i * 2 * Math.PI / 6D));
                            int yval = (int) (y * entitySize + entitySize /2 * Math.cos(i * 2 * Math.PI / 6D));

                            xPoints[i] = xval;
                            yPoints[i] = yval;
                        }

                        g.fillPolygon(xPoints, yPoints, yPoints.length);
                    }
                }
            }

        }

        g.setFont(new Font("Comic Sans", Font.PLAIN, 16));

       if(isHuman() == true)
       {
           humanMessageGenerator(g);
       }
       else
       {
           g.setColor(COLOR_MESSAGES);
           g.drawString("Twoj czlowiek zginal, ale mozesz ogladac swiat.", 300,10);
       }
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        newPosition = new Vector2d(y/ entitySize,x/ entitySize);

        newOrganismMenu.show(this,x,y);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("ruch");

        switch(keyEvent.getKeyCode())
        {
            case KeyEvent.VK_UP:
                world.setMove(World.Move.UP);
                break;

            case KeyEvent.VK_DOWN:
                world.setMove(World.Move.DOWN);
                break;

            case KeyEvent.VK_LEFT:
                world.setMove(World.Move.LEFT);
                break;

            case KeyEvent.VK_RIGHT:
                world.setMove(World.Move.RIGHT);
                break;

            case KeyEvent.VK_K:
                world.setMove(World.Move.SUPERPOWER);
                break;

        }

        paint(getGraphics());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public World getWorld() {
        return world;
    }

    private void humanMessageGenerator(Graphics g) {

        g.setColor(COLOR_MESSAGES);
        String messages = "Ruch czlowieka: ";

        switch(world.getMove())
        {
            case UP:
                messages+="do gory";
                break;
            case DOWN:
                messages+= "na dol";
                break;
            case STAND_STILL:
                messages+="bedzie stal";
                break;
            case LEFT:
                messages+="w lewo";
                break;
            case RIGHT:
                messages+="w prawo";
                break;
            case SUPERPOWER:
                messages+="uruchomi umiejetnosc specjalna";
                break;
        }

        g.drawString(messages,0,10);
    }


    private void placeEntity(Organism org) {

        Organism colliding = world.getOrganismAtPosition(newPosition);
        while(colliding != null)
        {
            colliding.kill();
            colliding = world.getOrganismAtPosition(newPosition);
        }


        org.setLocation(newPosition);
        world.addOrganism(org.copy());

        paint(getGraphics());

    }

}
