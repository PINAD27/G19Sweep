import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems.
 * It stores the following information:
 * - the state of all the ``dots'' on the dot (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough
 *  appropriate Getters.
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Faizaan Chishtie
 * @author Aidan Charles
 */
public class GameModel {

    private int numberOfSteps;
    private int width;
    private int heigth;
    private int numberOfMines;
    private int temp_board[][]; // FOR TESTING PURPOSES
    private int board[][];
    private DotInfo dot[][]; //Using DotInfo class for dot
    private int dotsUncovered;
    private static java.util.Random generator = new java.util.Random();

    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param width
     *            the width of the board
     *
     * @param heigth
     *            the heigth of the board
     *
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        this.width = width;
        this.heigth = heigth;
        this.numberOfMines = numberOfMines;
        this.numberOfSteps = 0;
        this.board = new int [width][heigth];
        this.dot = new DotInfo [width][heigth];
        this.temp_board = new int [width][heigth];
        this.dotsUncovered = 0;
        reset();
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up .
     */
    public void reset(){
        for (int i =0; i<width;i++){ // fills the board with zeros which will represent no bombs touching this square
            for (int j =0;j<heigth;j++){
              DotInfo d = new DotInfo(i,j); // changed to dotinfo so we can use the class to hold more info on board
              this.dot[i][j] = d;
              this.board[i][j] = 0;
              this.temp_board[i][j] = 11;//im prett

            }
        }
        int check =0;
        while(check<numberOfMines){ // this adds mines to the board randomly;
            int x= generator.nextInt(width);
            int y= generator.nextInt(heigth);
            if (this.board[x][y]!=9){ // A nine on the array is a mine
                this.board[x][y] = 9;
                get(x,y).setMined(); // sets mined to true for a dotinfo object
                check++;
            }
        }
        //gets all neighbooring mines
        for(int x = 0; x < width; x++){
          for(int y = 0; y < heigth; y++){
            this.dot[x][y].setNeighbooringMines(getNeighbooringMines(x,y));
          }
        }
    }

    /**
     * Getter method for the heigth of the game
     *
     * @return the value of the attribute heigthOfGame
     */
    public int getHeigth(){
        return(this.heigth);
    }
    public int[][] getBoard(){
      return(this.temp_board);
    }

    /**
     * Getter method for the width of the game
     *
     * @return the value of the attribute widthOfGame
     */
    public int getWidth(){
        return(this.width);
    }

    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isMined(int i, int j){
      return this.board[i][j] == 9;
    }

    /**
     * returns true if the dot  at location (i,j) has
     * been clicked, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean hasBeenClicked(int i, int j){
      return get(i,j).hasBeenClicked();
    }

  /**
     * returns true if the dot  at location (i,j) has zero mined
     * neighboor, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isBlank(int i, int j){
      return (getNeighbooringMines(i,j) == 0);
    }

    /**
     * returns true if the dot is covered, false otherwise
    *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isCovered(int i, int j){
      return (get(i,j).isCovered());
    }

    /**
     * returns the number of neighbooring mines os the dot
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */
    public int getNeighbooringMines(int i, int j){
      // sets min and max values for coordinates to scan based on pos
      /*
            x_min i x_max
              __ __ __
      y_max  |        |
      j      |        |
      y_min  |__ __ __|

      */
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
      if(y_max >= this.heigth){
        y_max -= 1;
      }

      int mines = 0; // counts mines
      for (int x = x_min; x <= x_max; x++){
        for (int y = y_min; y <= y_max; y++){
          if(isMined(x,y)){ //LOOK FOR BUG HERE
            mines += 1;
          }
        }
      }
      return mines;
    }

    /**
     * Sets the status of the dot at location (i,j) to uncovered
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void uncover(int i, int j){
      get(i,j).uncover();
      this.temp_board[i][j] = getNeighbooringMines(i,j); // assigns value to uncover
      this.dotsUncovered +=1;
    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void click(int i, int j){
      get(i,j).click();
      step();
    }

     /**
     * Uncover all remaining covered dot
     */
    public void uncoverAll(){
    for (int i = 0; i < this.width; i++){
      for (int j = 0; j < this.heigth; j++){
        if(isMined(i,j)){
        this.temp_board[i][j] = 9;
        }
        else if(isBlank(i,j)){
        this.temp_board[i][j] = 0;
      }
        else{
        this.temp_board[i][j] = this.dot[i][j].getNeighbooringMines();
      }
      }
    }
    }

    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps(){
        return(this.numberOfSteps);
    }

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */
    public DotInfo get(int i, int j) {
      return this.dot[i][j];
    }

   /**
     * The metod <b>step</b> updates the number of steps. It must be called
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
       this.numberOfSteps++;
    }

   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
      System.out.println("hi");
      for (int i = 0; i < this.width; i++){
        for(int j = 0; j < this.heigth; j++){
          if (this.temp_board[i][j]==11){
            return false;
          }
        }
      }
      return true;
    }

   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
      String out = "";
      for(int i = 0; i < this.width; i++){
        String s = "{";
        for(int j = 0; j < this.heigth; j++){
          s += " " + this.temp_board[i][j]; // creates a nice string representation of row ex: { 0 0 0 0 9 0 0 0 }
        }
        s += " }";
        out += s + "\n"; // appends to bigger string array
      }
      return out;
    }
}
