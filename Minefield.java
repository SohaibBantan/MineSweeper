
import java.util.Random;
import java.util.Scanner;

public class Minefield {
   
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE = "\u001b[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /*
     * Class Variable Section
     *
    */

    int myRows,myCols, myFlags,myMines;
    Cell[][] cellArray;
    boolean isWin = true;

    public Minefield(int rows, int columns, int flags) {
        this.myRows = rows;
        this.myCols = columns;
        this.myFlags = flags;
        this.myMines = flags;
        cellArray = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) { //sets all cells to default value 0
            for (int j = 0; j < columns; j++) {
                cellArray[i][j] = new Cell(false,"0");
            }
        }
    }

    public void evaluateField() {
        for (int i =0; i < myRows; i++){  // loops through all cells
            for (int j =0; j < myCols; j++){
                Cell currentCell = cellArray[i][j];
                if (currentCell.getStatus().equals("M")){
                    incrementSurrounding(i,j);// calls helper method: increments surroundings
                }
            }
        }
    }

    public void incrementSurrounding(int x, int y) { // increments surrounding statuses by one
        for (int i =x-1; i <= x+1; i++){
            if (i < myRows && i >= 0){ // checks row in bounds
            for (int j =y-1; j <= y+1; j++){
                if (j < myCols && j >= 0){ // checks col in bound
                Cell currentCell = cellArray[i][j];
                if (i != x || j != y){ // makes sure starting M isnt changed
                    if (!currentCell.getStatus().equals("M")) { // if another bomb, else recurse calls
//                        System.out.println("x:" + i + "y:" + j);
                        String convertedSum = String.valueOf(Integer.parseInt(currentCell.getStatus()) + 1); // increments, then puts back to string
                        currentCell.setStatus(convertedSum);
                    }
                }
            }
            }
        }
        }
    }


    public void createMines(int x, int y, int mines) {
        Random rand = new Random();
        while (mines > 0) {
//            System.out.println("While loop is Working");
            int rand_X = rand.nextInt(myRows); // finds random x
            int rand_Y = rand.nextInt(myCols); // finds random y
            Cell curCell = cellArray[rand_X][rand_Y];
            // following if checks: coordinate has not been revealed, is not already a mine, and is not equal to the starting coordinates
            if (!curCell.getRevealed() && !curCell.getStatus().equals("M") && (rand_X != x || rand_Y != y)) {
                curCell.setStatus("M"); // updates cell to mine
                mines--; // decrements # of mines
            }
        }
    }


    public boolean guess(int x, int y, boolean flag) {
        if ((x >= 0 && y >= 0) && (x < myRows && y < myCols)) {
            if (flag && this.myFlags > 0){
                cellArray[x][y].setPrevStatus(cellArray[x][y].getStatus()); // holds the previous status
                cellArray[x][y].setStatus("F");
                cellArray[x][y].setRevealed(true);
                this.myFlags--; // decrements flag
                return false;
            }
            else if (cellArray[x][y].getStatus().equals("F")) { // If user wants to turn the flagged cell back
                cellArray[x][y].setStatus(cellArray[x][y].getprevStatus());
                cellArray[x][y].setRevealed(false);
                this.myFlags++;
                return false;
            }
            else if (cellArray[x][y].getStatus().equals("0")) { // If cell's status is zero
                revealZeroes(x, y);
                cellArray[x][y].setRevealed(true);
                return false;
                }
            else if (cellArray[x][y].getStatus().equals("M")) { // if cell's status is a bomb
                cellArray[x][y].setRevealed(true);
                gameOver();
                return true;
            }

            cellArray[x][y].setRevealed(true);// if cell's status is a number other than zero
            }
        return false;
        }


    public boolean gameOver() {
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                Cell currentCell = cellArray[i][j];
                if (currentCell.getStatus().equals("M")) { // if an M was revealed, isWin is false, and return true
                    if (currentCell.getRevealed()) {
                        isWin = false;
                        return true;
                    }
                } else {
                    if (!currentCell.getRevealed()) { // if one cell isnt revealed, game is still on
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void revealZeroes(int x, int y) {
        Stack1Gen<Cell> myStack = new Stack1Gen<Cell>();
        if (cellArray[x][y].getStatus().equals("0")) {
            myStack.push(cellArray[x][y]);
        }
        while (!myStack.isEmpty()) {
            int currX = 0;
            int currY = 0;
            boolean foundCell = false;
            Cell popped = myStack.pop();
            if (popped.getStatus().equals("0") && !popped.getRevealed())  { // if cell's status is zero and not checked yet
                popped.setRevealed(true);
            }
            for (int i =0; i < myRows; i++) { // finds index of current cell of top of stack that was popped
                for (int j =0; j < myCols; j++) {
                    if (cellArray[i][j] == popped && !foundCell) {
                        currX = i;
                        currY = j;
                        foundCell = true;
                    }
                }
            }

            for (int i = currX - 1; i <= currX + 1; i++) {
                if (i < myRows && i >= 0) { // checks row in bounds
                    for (int j = currY - 1; j <= currY + 1; j++) {
                        if (j < myCols && j >= 0) { // checksm if col in bound
                            Cell currentCell = cellArray[i][j];
                            if (i != x || j != y) { // makes sure starting 0 isnt pushed
                                if (!currentCell.getRevealed() && currentCell.getStatus().equals("0")) {// if not revealed and "0"
//                                    System.out.println("I ran the first time");
                                    myStack.push(cellArray[i][j]); // pushed current "0"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    

    public void revealStartingArea(int x, int y) {
        Q1Gen<Cell> myQueue = new Q1Gen<Cell>(); // queue initialization
        int movingX = x;
        while (cellArray[movingX][y].getStatus().equals("M")) { // takes care of edge case: first is M.
            movingX++;
        }
        myQueue.add(cellArray[movingX][y]);

        while (myQueue.length() > 0){
            int currX = 0;
            int currY = 0;
            boolean foundCell = false;
            Cell deQueued = myQueue.remove();

            if ( (currX < myRows && currY < myCols) && deQueued != null && !deQueued.getRevealed() && !deQueued.getStatus().equals("M")) {
                deQueued.setRevealed(true);
            }
            if (deQueued.getStatus().equals("M")) {
                break;
            }
            for (int i =0; i < myRows; i++) { // finds index of current cell of top of stack that was popped
                for (int j =0; j < myCols; j++) {
                    if (cellArray[i][j] == deQueued && !foundCell) {
                        currX = i;
                        currY = j;
                        foundCell = true;
                    }
                }
            }
            for (int i = currX - 1; i <= currX + 1; i++) {
                if (i < myRows && i >= 0) { // checks row in bounds
                    for (int j = currY - 1; j <= currY + 1; j++) {
                        if (j < myCols && j >= 0) { // checksm if col in bound
                            Cell currentCell = cellArray[i][j];
                            if (i != x || j != y) {
                                if (!currentCell.getRevealed()) {// if not revealed and "0"
                                    myQueue.add(cellArray[i][j]);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public String colorSetter(String status){ // sets number to its respective unique color. If M, or F, changw to red and white, respectively
        String[] colors ={ANSI_YELLOW_BRIGHT,ANSI_GREEN,ANSI_BLUE,ANSI_PURPLE,ANSI_CYAN,ANSI_RED_BRIGHT,ANSI_BLUE_BRIGHT};
        for (int i = 0; i < 8; i++) {
            if (status.equals("M")){ // if bomb make red
                return ANSI_RED + status + ANSI_GREY_BACKGROUND;
            }
            if (status.equals("F")){ //if flag make white
                return ANSI_WHITE + status + ANSI_GREY_BACKGROUND;
            }
            if (Integer.parseInt(status) == i){
                return colors[i] + status + ANSI_GREY_BACKGROUND ; // set number status to respective color
            }
        }
        return status;
    }
    
    public void debug() {
            System.out.print("  "); // to help match the indexes
            for (int i = 0; i < cellArray[0].length; i++) {// prints row indexes
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i < cellArray.length; i++) {
                System.out.print(i + " "); // prints column indexes
                for (int j = 0; j < cellArray[i].length; j++)
                    System.out.print(colorSetter(cellArray[i][j].getStatus()) + " ");
                System.out.println();
            }
    }

    public String toString() {
        System.out.print("  "); // to help match the indexes
        for (int i = 0; i < cellArray[0].length; i++) {// prints row indexes
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < cellArray.length; i++) {
            System.out.print(i + " "); // prints column indexes
            for (int j = 0; j < cellArray[i].length; j++) {
                if (cellArray[i][j].getRevealed()) { // if cell is revealed
                    System.out.print(colorSetter(cellArray[i][j].getStatus()) + " ");
                } else {
                    System.out.print("-" + " "); // if not revealed
                }
            }
            System.out.println(); // after finishing one full row, this gets us to next row
        }
        return " "; // returns nthn. All the printing has been done by then
    }

    public void seeTruths() { // for debugging purposes. Reveals the isRevealed for all cells
        System.out.print("  "); // to help match the indexes
        for (int i = 0; i < cellArray[0].length; i++) {// prints row indexes
            System.out.print("  " + i + "  ");
        }
        System.out.println();
        for (int i = 0; i < cellArray.length; i++) {
            System.out.print(i + " "); // prints column indexes
            for (int j = 0; j < cellArray[i].length; j++) {
                if (cellArray[i][j].getRevealed()) {
                    System.out.print(ANSI_GREEN + cellArray[i][j].getRevealed() + ANSI_GREY_BACKGROUND + " ");
                }
                else {
                    System.out.print(ANSI_RED + cellArray[i][j].getRevealed() + ANSI_GREY_BACKGROUND + " ");
                }
            }
            System.out.println();
        }
    }

    public void finalResult() {
        System.out.print("  "); // to help match the indexes
        for (int i = 0; i < cellArray[0].length; i++) {// prints row indexes
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < cellArray.length; i++) {
            System.out.print(i + " "); // prints column indexes
            for (int j = 0; j < cellArray[i].length; j++){
                if (cellArray[i][j].getStatus().equals("F")) { // takes care of the flagged cells
                    System.out.print(ANSI_WHITE + cellArray[i][j].getprevStatus() + ANSI_GREY_BACKGROUND + " ");
                } // prints the prev value of the flag
                else {
                    System.out.print(colorSetter(cellArray[i][j].getStatus()) + " ");
                }
        }
            System.out.println();
        }
    }
}



