package battleships;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Enemy {
    

    private List<Ship> enemyShips = Arrays.asList(new Ship(2), new Ship(2), new Ship(3), new Ship(3), new Ship(4));
    private List<Ship> playerShips = new ArrayList<>();
    private Board enemyBoard = new Board();
    private HashMap<Integer, List<Integer>> coordinates = new HashMap<>();
    private int totalHits = 0;

    public Enemy() {
        for (int i = 0; i < 10; i ++) {
            List<Integer> xvalues = new ArrayList<>();
            Collections.addAll(xvalues, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
            coordinates.put(i, xvalues);
        }
    }

    public void placeEnemyShips() {
        List<String> directions = Arrays.asList("up", "down", "left", "right");
        Random random = new Random();
        for (Ship ship : enemyShips) {
            int xCoord = random.nextInt(10);
            int yCoord = random.nextInt(10);
            int directionIndex = random.nextInt(4);
            while (!isValidPlacement(xCoord, yCoord, ship, directions.get(directionIndex))) {
                directionIndex = random.nextInt(4);
                xCoord = random.nextInt(10);
                yCoord = random.nextInt(10);
            }
            placeShip(xCoord, yCoord, ship, directions.get(directionIndex));
        }
    }

    public void shoot(Board board) {
        List<List<Integer>> matrix = board.getMatrix();
        Random random = new Random();
        int xCoord = random.nextInt(10);
        int yCoord = random.nextInt(10);
        int coordinate = getCoordinate(xCoord, yCoord, board);
        while (!isValidShot(xCoord, yCoord)) {
            xCoord = random.nextInt(10);
            yCoord = random.nextInt(10);
            coordinate = getCoordinate(xCoord, yCoord, board);
        } 

        matrix.get(yCoord).set(xCoord, coordinate += 2);

        if (coordinate == 2) {
            System.out.println(printMiss());
        } else if (coordinate == 3) {
            updateShip(xCoord, yCoord);
            totalHits += 1;
        }
    }

    public void placeShip(int xCoord, int yCoord, Ship ship, String direction) {
        if (direction.equals("right")) {
                for (int i = 1; i <= ship.getSize(); i++) {
                        place(xCoord, yCoord, ship);
                    xCoord += 1;
                }           
        } else if (direction.equals("left")) {           
                for (int i = 1; i <= ship.getSize(); i++) {
                        place(xCoord, yCoord, ship);
                    xCoord -= 1;
                }
        } else if (direction.equals("down")) {
                for (int i = 1; i <= ship.getSize(); i++) {
                        place(xCoord, yCoord, ship);
                    yCoord -= 1;
                }
        } else if (direction.equals("up")) {
                for (int i = 1; i <= ship.getSize(); i++) {
                        place(xCoord, yCoord, ship);
                    yCoord += 1;
                }
        }
    }

    private boolean isValidPlacement(int xCoord, int yCoord, Ship ship, String direction) {
        if (direction.equals("right")) {
            try {
                for (int i = 1; i <= ship.getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, enemyBoard) == 1) {
                        return false;
                    }
                    xCoord += 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                return false;
            }
        } else if (direction.equals("left")) {
            try {
                for (int i = 1; i <= ship.getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, enemyBoard) == 1) {
                        return false;
                    }
                    xCoord -= 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                return false;
            }
        } else if (direction.equals("down")) {
            try {
                for (int i = 1; i <= ship.getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, enemyBoard) == 1) {
                        return false;
                    }
                    yCoord -= 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                return false;
            }
        } else if (direction.equals("up")) {
            try {
                for (int i = 1; i <= ship.getSize(); i++) {
                    if (getCoordinate(xCoord, yCoord, enemyBoard) == 1) {
                        return false;
                    }
                    yCoord += 1;
                }
            return true;
            } catch (Exception IndexOutOfBoundsException) {
                return false;
                }
        }

        return false; 
    }

    private boolean isValidShot(Integer xCoord, Integer yCoord) {
        if (coordinates.containsKey(yCoord) && coordinates.get(yCoord).contains(xCoord)) {
            coordinates.get(yCoord).remove(xCoord);
            if (coordinates.get(yCoord).isEmpty()) {
                coordinates.remove(yCoord);
            }
            return true;
        }

        return false;
    }

    private int getCoordinate(int xCoord, int yCoord, Board board) {
        List<List<Integer>> matrix = board.getMatrix();
        return matrix.get(yCoord).get(xCoord);
    }

    private void place(int xCoord, int yCoord, Ship ship) {
        List<List<Integer>> matrix = enemyBoard.getMatrix();
        List<Integer> coordinates = Arrays.asList(xCoord, yCoord);
        matrix.get(yCoord).set(xCoord, 1);
        ship.getShipCoordinates().add(coordinates);
    }

    public void updateShip(int xCoord, int yCoord) {
        List<Integer> coordinates = Arrays.asList(xCoord, yCoord);
        for (Ship ship : playerShips) {
            if (ship.getShipCoordinates().contains(coordinates)) {
                int hP = ship.getHitPoints();
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


    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public List<Ship> getEnemyShips() {
        return enemyShips;
    }

    public void setPlayerShips(List<Ship> playerShips) {
        this.playerShips = playerShips;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public String printMiss() {
        return "The shot from the enemy missed.";
    }

    public String printHit(int hP) {
        return "The enemy hit a ship! The ship has " + hP + " more points to hit.";
    }

    public String printSunk() {
        return "The enemy sunk a ship!";
    }
        
}
