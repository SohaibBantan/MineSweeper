
import java.util.Scanner;


public class Main{

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Would You Like Behind-the-Scenes Mode? Answer with 'Yes' or 'No'.");
        String takenDebug = s.nextLine();
        boolean isDebug = takenDebug.equals("Yes") || takenDebug.equals("yes"); // will be used to either call debug() or toString()

        Scanner s2 = new Scanner(System.in);
        System.out.println("Choose Difficulty Level: Easy, Medium, or Hard");
        String takenDiff = s2.nextLine();
        int rowNcol, flagsNbombs; // rowNcol: Row & col number. flagnsNbombs: flags and bombs number
        if (takenDiff.equals("Easy") || takenDiff.equals("easy")) { //Case1: easy difficulty
            rowNcol = 5;
            flagsNbombs = 5;
        } else if (takenDiff.equals("Medium") || takenDiff.equals("medium")) { //Case2: medium difficulty
            rowNcol = 9;
            flagsNbombs = 12;
        } else { //Case3: hard difficulty
            rowNcol = 20;
            flagsNbombs = 40;
        }

        Minefield tester = new Minefield(rowNcol, rowNcol, flagsNbombs);
        tester.createMines(0, 0, flagsNbombs);
        tester.evaluateField(); // sets the cells to their corresponding status

        Scanner a = new Scanner(System.in);
        System.out.println("Enter Starting coordinates in form of row,column");
        String starting = a.nextLine();
        String[] Startcords = starting.split(",");
        int xsCord = Integer.parseInt(Startcords[0]); // converts x into int
        int ysCord = Integer.parseInt(Startcords[1]); // converts y into int
//        System.out.println("The Acc Cell:" + tester.cellArray[xsCord][ysCord].getStatus());
        if ((xsCord >= 0 && ysCord >= 0) && (xsCord < rowNcol && ysCord < rowNcol)) { // checks starting coords are valid
            tester.revealStartingArea(xsCord, ysCord); // Starts from middle
        }
        else {
            System.out.println("Invalid Coordinates. Middle coordinate pair has been placed for you.");
            System.out.println();
            tester.revealStartingArea(rowNcol/2, rowNcol/2);
        }

        if (isDebug) { // if user wants debug mode
            tester.debug();
        }
        else {
            tester.toString();
        }
        while (!tester.gameOver()) {
            Scanner s3 = new Scanner(System.in);
            System.out.println("Enter Coordinates and if you wish to place flag (Remaining:" + tester.myFlags + ") in the form of row,column,yes/no");
            String userInp = s3.nextLine();
            String[] cords = userInp.split(",");
            int xCord = Integer.parseInt(cords[0]); // converts x into int
            int yCord = Integer.parseInt(cords[1]); // converts y into int
            if (cords.length >= 3 && (cords[2].equals("yes") || cords[2].equals("Yes"))) { // user wants to place flag
                tester.guess(xCord, yCord, true); // guess method call
                if (tester.cellArray[xCord][yCord].getStatus().equals("M")) {
                    tester.isWin = false;
                    break; // goes to the beggening of while loop to stop
                }
                if (isDebug) { // debug mode
                    tester.debug();
                    tester.seeTruths(); // shows revealing statuses
                } else
                    tester.toString();
            } else {
                tester.guess(xCord, yCord, false); // guess method call
                if (tester.cellArray[xCord][yCord].getStatus().equals("M")) {
                    tester.isWin = false;
                    break;
                }
                if (isDebug) { // debug mode
                    tester.debug();
                    System.out.println();
                    tester.seeTruths();
                } else {
                    tester.toString();
                    System.out.println();
                }
            }
        }
        if (tester.isWin) { // Case1: User won
            System.out.println("Congratulations, You won!");
            tester.finalResult(); // prints final result revealed
            System.out.println("The values in white are your flagged cells");
        }
        else { // Case 2: User lost
            System.out.println("You Lost, Better luck next time!");
            tester.finalResult(); // prints final result revealed
            System.out.println("The values in white are your flagged cells");
        }
    }
}
