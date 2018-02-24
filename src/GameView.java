import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Faizaan Chishtie
 * @author Aidan Charles
 */

public class GameView extends JFrame {

     // ADD YOUR INSTANCE VARIABLES HERE
    private GameModel gameModel;
    private GameController gameController;
    private JFrame frame;

    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {

        this.gameModel = gameModel;
        this.gameController= gameController;
        this.frame = new JFrame ("Minesweeper");
        frame = new JFrame ("Minesweeper");
        frame. setSize(700, 500);
        
        JButton reset, quit; 
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Number of Steps:" + gameModel.getNumberOfSteps());
        
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());
        quit = new JButton("quit");
        reset = new JButton("Reset");

        panel.add(label);
        panel.add(reset);
        panel.add(quit);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        quit.setActionCommand("quit");
        reset.setActionCommand("reset");
    }


    /**
     * update the status of the board's DotButton instances based
     * on the current game model, then redraws the view
     */

    public void update(){

    // ADD YOU CODE HERE
    frame.repaint();

    }

    /**
     * returns the icon value that must be used for a given dot
     * in the game
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */
    private int getIcon(int i, int j){

    // ADD YOU CODE HERE

    }


}
