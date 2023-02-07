package battleships;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    
    private List<Ship> playerShips = Arrays.asList(new Ship(2), new Ship(2), new Ship(3), new Ship(3), new Ship(4));
    private List<Ship> enemyShips = new ArrayList<>();
    private Board playerBoard = new Board();
    private int totalHits = 0;

    public Player() {

    }
// Plasserer et skip på brettet i form av å bytte ut 0'er i matrixen med 1'ere.
    public void placeShip(int startX, int startY, int shipNumber, String direction) throws IllegalArgumentException {
        int xCoord = startX - 1; 
        int yCoord = 10 - startY;
        if (!isValidPlacement(startX, startY, shipNumber, direction)) {
            throw new IllegalArgumentException("Not a valid place on the board!");
        } if (!getPlayerShips().get(shipNumber).getShipCoordinates().isEmpty()) {
            throw new IllegalArgumentException("This ship has already been placed");
        } if (direction.equals("right")) {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                        place(xCoord, yCoord, shipNumber);
                    xCoord += 1;    
                }
                System.out.println("Placed ship at " + playerShips.get(shipNumber).shipCoordsToString());           
        } else if (direction.equals("left")) {           
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                        place(xCoord, yCoord, shipNumber);
                    xCoord -= 1;
                }
                System.out.println("Placed ship at " + playerShips.get(shipNumber).shipCoordsToString());
        } else if (direction.equals("down")) {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                        place(xCoord, yCoord, shipNumber);
                    yCoord += 1;
                }
                System.out.println("Placed ship at " + playerShips.get(shipNumber).shipCoordsToString());
        } else if (direction.equals("up")) {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                        place(xCoord, yCoord, shipNumber);
                    yCoord -= 1;
                }
                System.out.println("Placed ship at " + playerShips.get(shipNumber).shipCoordsToString());
        }
    }

/* Skyter en tile på brettet i form av å øke verdien for koordinatet med 2. Sjekker deretter om det er et skip som er truffet. Hvis et skip er truffet får koordinatet
verdien 3, og hvis skuddet bommet får det koordinatet den traff verdien 2. */
    public void shoot(int xCoord, int yCoord, Board board) throws IllegalArgumentException {
        List<List<Integer>> matrix = board.getMatrix();
        xCoord -= 1;
        yCoord = 10 - yCoord;
        try {
            if (isValidShot(xCoord, yCoord, board)) {
                int coordinate = getCoordinate(xCoord, yCoord, board);
                matrix.get(yCoord).set(xCoord, coordinate += 2);
                if (coordinate == 2) {
                    System.out.println(printMiss());
                } else if (coordinate == 3) {
                    updateShip(xCoord, yCoord);
                    totalHits += 1;
                }
            } 
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

// Oppdaterer verdien til tilen til 1. Denne funksjonen er her mest for å få ryddigere kode.
    private void place(int xCoord, int yCoord, int shipNumber) {
        List<List<Integer>> matrix = playerBoard.getMatrix();
        List<Integer> coordinates = Arrays.asList(xCoord, yCoord);
        matrix.get(yCoord).set(xCoord, 1);
        playerShips.get(shipNumber).getShipCoordinates().add(coordinates);
    }

// Henter ut verdien til koordinatet som sendes inn i funksjonen. Denne funksjonen eksisterer også mest for å få ryddigere kode.
    private int getCoordinate(int xCoord, int yCoord, Board board) {
        List<List<Integer>> matrix = board.getMatrix();
        return matrix.get(yCoord).get(xCoord);
    }


// Kalles bare dersom et skip er truffet. Den finner ut av hvilket skip som er truffet og hvor mange flere tiles skipet okkuperer.
    private void updateShip(int xCoord, int yCoord) {
        List<Integer> coordinates = Arrays.asList(xCoord, yCoord);
        for (Ship ship : enemyShips) {
            if (ship.getShipCoordinates().contains(coordinates)) {
                int hP = ship.getHitPoints();
                ship.getHitCoordinates().add(coordinates);
                ship.getShipCoordinates().remove(coordinates);
                ship.setHitPoints(hP -= 1);
                if (ship.getHitPoints() == 0) {
                    System.out.println(printSunk());
                } else {
                    System.out.println(printHit(hP));
                }

                break;
            }
        }
    }

/* Sjekker at plasseringen til skipet er gyldig. Dersom skipet havner utenfor brettet, eller har et koordinat som er likt som et annet skip
    er det en ugyldig plassering. */
    private boolean isValidPlacement(int startX, int startY, int shipNumber, String direction) {
        int xCoord = startX - 1; 
        int yCoord = 10 - startY;
        if (shipNumber < 0 || shipNumber > 4) {
            throw new IllegalArgumentException("This is not a ship you can place! Please input a number between 1 and 5");
        }
        if (!direction.equals("right") && !direction.equals("left") && !direction.equals("up") && !direction.equals("down")) {
            throw new IllegalArgumentException("Ship direction must be either right, left, up or down!");
        }
        if (!(startX > 0 && startX <= 10) || !(startY > 0 && startY <= 10)) {
            throw new IllegalArgumentException("That coordinate does not exist on the board!");
        }
        if (direction.equals("right")) {
            try {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, playerBoard) == 1) {
                        return false;
                    }
                    xCoord += 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                throw new IllegalArgumentException("The ship does not fit on the board with this rotation.");
            }
        } else if (direction.equals("left")) {
            try {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, playerBoard) == 1) return false;
                    xCoord -= 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                throw new IllegalArgumentException("The ship does not fit on the board with this rotation.");
            }
        } else if (direction.equals("down")) {
            try {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, playerBoard) == 1) {
                        return false;
                    }
                    yCoord += 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                throw new IllegalArgumentException("The ship does not fit on the board with this rotation.");
            }
        } else if (direction.equals("up")) {
            try {
                for (int i = 1; i <= playerShips.get(shipNumber).getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, playerBoard) == 1) {
                        return false;
                    }
                    yCoord -= 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                throw new IllegalArgumentException("The ship does not fit on the board with this rotation.");
                }
        }

        return false; 
    }

// Sjekker at skuddet er gyldig. Et skudd er ugyldig dersom det er utenfor brettet, eller om tilen som skal skytes på allerede har blitt skutt på.
    private boolean isValidShot(int xCoord, int yCoord, Board board) {
        if (xCoord < 0 || xCoord > 9 || yCoord < 0 || yCoord > 9) {
            throw new IllegalArgumentException("That shot is placed outside of the board!");
        } if (getCoordinate(xCoord, yCoord, board) == 2 || getCoordinate(xCoord, yCoord, board) == 3) {
            throw new IllegalArgumentException("This tile has already been shot at!");
        }
         return true;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayeBoard(Board board) {
        this.playerBoard = board;
    }

    public List<Ship> getPlayerShips() {
        return playerShips;
    }

    public void setEnemyShips(List<Ship> enemyShips) {
        this.enemyShips = enemyShips;
    }

    public int getTotalHits() {
        return totalHits;
    }


// Kalles på dersom et skudd bommer.
    public String printMiss() {
        return "Your shot missed.";
    }

// Kalles på dersom skuddet treffer et skip, men skipet fortsatt har flere koordinater som kan treffes.
    public String printHit(int hP) {
        return "You hit a ship! The ship has " + hP + " more points to hit.";
    }

// Kalles på dersom skuddet treffer et skip, og alle av skipets koordinater er truffet.
    public String printSunk() {
        return "You sunk a ship!";
    }

    public static void main(String[] args) {
    }
}
