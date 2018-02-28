import java.awt.event.*;
import java.util.*;

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
    private GameView gameView;
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
      this.gameView = new GameView(game, this);

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

         // System.out.println("reset"); //DELETEM
          reset();
          gameView.update();
        }
        else if(e.getActionCommand().equals("quit")){
          // System.out.println("quit"); //DELETEM
           System.exit(0);
        }
        else{//we should make this check that this block isnt reavealed yet
          String token = e.getActionCommand();
          StringTokenizer tokenizer = new StringTokenizer(token, ",");
          int i = Integer.parseInt(tokenizer.nextToken());
          int j = Integer.parseInt(tokenizer.nextToken());
          play(i,j);
          gameView.update();
        }
    }

    /**
     * resets the game
     */
    private void reset(){
      this.game.resetSteps();
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
    public void play(int width, int height){ // DELETEM CHANGE TO PRIVATE !!!
      boolean clicked = this.game.hasBeenClicked(width, height); //hi
      if (!clicked){
        boolean mine = this.game.isMined(width, height);
        if(mine){
           this.game.uncoverAll();
           gameView.update();
           Object[] options = { "Quit", "Play Again" };
           int r =JOptionPane.showOptionDialog(null, "Aouh, you lost in "+game.getNumberOfSteps()+" steps!\n Would you like to play again?", "BOOM!!",
           JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
           null, options, options[0]);

           if (r == JOptionPane.YES_OPTION)
                System.exit(0);
           else if (r == JOptionPane.NO_OPTION)
                reset();   

                }
        else{
        this.game.click(width,height);
        this.game.uncover(width,height);
        clearZone(this.game.get(width,height));
        if (this.game.isFinished()){
            game.uncoverAll(); //ATWAD
            gameView.update();
          Object[] options = { "Quit", "Play Again" };
          int k=JOptionPane.showOptionDialog(null, "Congratulations you've won in "+game.getNumberOfSteps()+" steps!\n Would you like to play again?", "Victery!!",
          JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
          null, options, options[0]);

          if (k == JOptionPane.YES_OPTION)
                System.exit(0);
          else if (k == JOptionPane.NO_OPTION) 
                reset();
        }
      }
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
      GenericArrayStack<DotInfo> stack = new GenericArrayStack<DotInfo>(capacity);
      stack.push(initialDot);
      boolean empty = stack.isEmpty();
      while (!empty){
        DotInfo d = stack.pop();


        //THIS IS UGLY TEMP FOR ALGORITHM TO WORK

        int i = d.getX();
        int j = d.getY();
        if (!game.isBlank(i,j)){ //CATCH IF DOT PRESSED IS MINE
          game.uncover(i,j);
          break;
        }


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
        if(x_max >= this.width){
          x_max -= 1;
        }
        if(y_max >= this.height){
          y_max -= 1;
        }
        for (int x = x_min; x <= x_max; x++){
          for (int y = y_min; y <= y_max; y++){
            DotInfo tmp = this.game.get(x,y);
            boolean covered = tmp.isCovered();
            if(covered && (!tmp.isMined())){
              //dotsUncovered
              game.uncover(x,y);   //UNCOVERS TEMP BOARD
              if (game.isBlank(x,y)){
                stack.push(tmp);
              }
              //end if
            }
            //end if
          }

        }
        //end for
        empty = stack.isEmpty(); //update while loop
      }
      //end while
    }

}
