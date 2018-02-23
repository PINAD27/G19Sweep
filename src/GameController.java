import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Faizaan Chishtie
 * @author Aidan Charles
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE

    private int width;
    private int height;
    private int numberOfMines;
    private GameModel game;
    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
      this.width = width;
      this.height = height;
      this.numberOfMines = numberOfMines;
      this.game = new GameModel(width, height, numberOfMines);
      System.out.println(game);

    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("reset")){
          reset();
        }
        if(e.getActionCommand().equals("quit")){
          this.game.uncoverAll(); //temp exit command
        }
    }

    /**
     * resets the game
     */
    private void reset(){
      this.game.reset();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares.
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int height){
      boolean clicked = this.game.hasBeenClicked(width, height);
      if (!clicked){
        boolean mine = this.game.isMined(width, height);
        if(mine){
           this.game.uncoverAll();
        }
        this.game.click(width,height);
        this.game.uncover(width,height);
        clearZone(this.game.get(width,height));
        this.game.step();
      }

    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered''
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    @SuppressWarnings("unchecked")
    private void clearZone(DotInfo initialDot) {
      //creating a huge stack for now:
      int capacity = this.width*this.height;
      GenericArrayStack<DotInfo> stack = new GenericArrayStack(capacity);
      stack.push(initialDot);
      boolean empty = stack.isEmpty();
      while (!empty){
        DotInfo d = stack.pop();
        if (d == null){ //really shabby catch right here but it works
          break;
        }
        //THIS IS UGLY TEMP FOR ALGORITHM TO WORK
        int i = d.getX();
        int j = d.getY();
        int x_min = i-1;
        int x_max = i+1;
        int y_min = j-1;
        int y_max = j+1;
        // deals with case if user has clicked on a corner or a side
        if(x_min < 0){
          x_min += 1;
        }
        if(y_min < 0){
          y_min += 1;
        }
        if(x_max > this.width){
          x_min -= 1;
        }
        if(y_max > this.height){
          y_min -= 1;
        }
        int mines = 0; // counts mines
        for (int x = x_min; x < x_max; x++){
          for (int y = y_min; y < y_max; y++){
            DotInfo tmp = this.game.get(x,y);
            boolean covered = tmp.isCovered();
            if(covered){
              if (!(tmp.isMined() || tmp.getNeighbooringMines() > 0)){
                tmp.uncover();
                game.uncover(x,y); //UNCOVERS TEMP BOARD
                stack.push(tmp);
              }
              //end if
            }
            //end if
          }

        }
        //end for
        System.out.println(game); //DELETEM
        empty = stack.isEmpty(); //update while loop
      }
      //end while
    }

    /*
        DELETEM
    */
    public static void main(String[] args) {
      GameController g = new GameController(10,10,10);
      g.play(3,4); //DELETEM
    }

}
