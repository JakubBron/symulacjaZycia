package projekt.gui;

import projekt.helpers.FileManager;
import projekt.simulation.World;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class App extends JFrame {

    public static final String TITLE = "OOP Project - Game of life (Java)";
    public static final int DEFAULT_HEIGHT = 720;
    public static final int DEFAULT_WIDTH = 720;

    private final WindowPrinterAndHandler windowPrinterAndHandler;
    private final FileManager fileManager;

    private JButton button_nextTurn;
    private JButton button_log;
    private JMenuItem menuList_newGameSquares;
    private JMenuItem menuList_newGameHex;
    private JMenuItem menuList_readGame;
    private JMenuItem menuList_writeGame;

    public App(int width, int height) {

        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        revalidate();

        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        windowPrinterAndHandler = new WindowPrinterAndHandler(DEFAULT_HEIGHT * 8/10, World.setOnBoard(World.Type.Hex));
        fileManager = new FileManager();

        initiateUpperBarMenu();
        initiateMainWindow();
    }

    public void startGame() {
        setVisible(true);
    }

    private void initiateUpperBarMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuNewGame = new JMenu("New game");
        JMenu menuFile = new JMenu("File");

        initiateUpperBarMenuButtons();

        menuNewGame.add(menuList_newGameSquares);
        menuNewGame.add(menuList_newGameHex);

        menuFile.add(menuList_readGame);
        menuFile.add(menuList_writeGame);

        menuBar.add(menuNewGame);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }

    private void initiateUpperBarMenuButtons() {

        menuList_newGameSquares = new JMenuItem("squares");
        menuList_newGameHex = new JMenuItem("hexagons");
        menuList_readGame = new JMenuItem("load");
        menuList_writeGame = new JMenuItem("save");

        menuList_newGameSquares.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowPrinterAndHandler.setWorld(World.setOnBoard(World.Type.Squares));
            }
        });


        menuList_newGameHex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowPrinterAndHandler.setWorld(World.setOnBoard(World.Type.Hex));

            }
        });

        menuList_readGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose file to read:");

                int result = fc.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    World world = fileManager.read(file);
                    if(world != null)
                    {
                        windowPrinterAndHandler.setWorld(world);
                    }
                }
            }
        });

        menuList_writeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose file to save:");

                int result = fc.showOpenDialog(null);

                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    fileManager.save(windowPrinterAndHandler.getWorld(), file);

                }

            }
        });

    }


    private void initiateMainWindow() {
       initiateButtons();
       JPanel mainWindowButtons = new JPanel();

       GridLayout layout = new GridLayout(0,2);
       mainWindowButtons.setLayout(layout);

       mainWindowButtons.add(button_nextTurn);
       mainWindowButtons.add(button_log);

       JSplitPane splitPane = new JSplitPane();

       splitPane.setEnabled(false);
       splitPane.setDividerLocation( DEFAULT_HEIGHT * 8 / 10);
       splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

       splitPane.addMouseListener(windowPrinterAndHandler);

       splitPane.setTopComponent(windowPrinterAndHandler);
       splitPane.setBottomComponent(mainWindowButtons);

       add(splitPane);
    }

    private void initiateButtons() {

        button_nextTurn = new JButton("Next round");
        button_log = new JButton("Log");

        button_nextTurn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                windowPrinterAndHandler.nextTurn();
            }
        });

        button_log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showConfirmDialog(null, windowPrinterAndHandler.getLog().print(),"Dziennik", JOptionPane.DEFAULT_OPTION);
            }
        });
    }

}
