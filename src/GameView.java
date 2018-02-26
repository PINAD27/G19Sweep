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
    private DotButton Dots[][];
    private JLabel label;
    private JPanel panel;

    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        this.Dots = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];
        this.gameModel = gameModel;
        this.gameController= gameController;
        this.frame = new JFrame ("Minesweeper");
        frame = new JFrame ("Minesweeper");
        frame. setSize(700, 500);

        JButton reset, quit;
        panel = new JPanel();
        label = new JLabel("Number of Steps: " + gameModel.getNumberOfSteps());

        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());
        quit = new JButton("Quit");
        reset = new JButton("Reset");

        panel.add(label);
        panel.add(reset);
        panel.add(quit);
        frame.add(panel, BorderLayout.SOUTH);

        JPanel innerGame = new JPanel();
        innerGame.setLayout(new GridLayout(gameModel.getWidth(), gameModel.getHeigth())); //grid width x length
        for (int i = 0; i < gameModel.getWidth(); i++) {
            for (int j = 0; j < gameModel.getHeigth(); j++) {
                    DotButton d = new DotButton(i,j,getIcon(i,j));
                    d.addActionListener(this.gameController);
                    d.setActionCommand(i + "," + j);//DELETEm
                    d.setBorder(null);
                    Dots[i][j] = d;
                    innerGame.add(d);//we have to add buttons to the frame
                }
        }
        frame.add(innerGame);
        //frame.pack();
        innerGame.setVisible(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        quit.setActionCommand("quit");
        quit.addActionListener(this.gameController);
        reset.setActionCommand("reset");
        reset.addActionListener(this.gameController);
    }


    /**
     * update the status of the board's DotButton instances based
     * on the current game model, then redraws the view
     */

    public void update(){//we need to add a udate to the dotbutton status
    gameModel.step();
    this.label.setText("Number of Steps: " + gameModel.getNumberOfSteps());
    
    System.out.println(gameModel.getNumberOfSteps()); //DELETEm

    for(int i = 0; i < gameModel.getWidth(); i++){
      for(int j = 0; j < gameModel.getHeigth(); j++){
        Dots[i][j].setIconNumber(getIcon(i,j));
        Dots[i][j].setBorder(null);
      }
    }
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
      return (gameModel.getBoard()[i][j]);
    }

}
