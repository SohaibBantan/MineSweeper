
public class Cell {
    private boolean revealed; // True if the Cell has been revealed to the user, false otherwise.
    private String status; // "-": Blank, "F": Flagged by the user, "M": Mine, "0" through "8": Number of mines
    private String prevStatus; // Previous status. Will be used when status is set to F
    public Cell(boolean revealed, String status) {
        this.revealed = revealed;
        this.status = status;
    }

    public boolean getRevealed() {
        return revealed;
    }

    public void setRevealed(boolean r) { revealed = r; }


    public String getStatus() {
        return status;
    }

    public void setStatus(String c) {
        status = c;
    }

    public void setPrevStatus(String c) {
        prevStatus = c;
    } // setter for prevStatus

    public String getprevStatus() {
        return prevStatus;
    }
}
