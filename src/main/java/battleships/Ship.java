package battleships;

import java.util.*;

public class Ship {
    
    private int size;
    private List<List<Integer>> shipCoordinates = new ArrayList<>();
    private List<List<Integer>> hitCoordinates = new ArrayList<>();
    private int hitPoints;

    public Ship(int size) {
        if (isValidSize(size)) {
            this.size = size;
            this.hitPoints = size;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Integer>> getShipCoordinates() {
        return shipCoordinates;
    }

    public void setShipCoordinates(List<List<Integer>> shipCoordinates) {
        this.shipCoordinates = shipCoordinates;
    }

    public List<List<Integer>> getHitCoordinates() {
        return hitCoordinates;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    private boolean isValidSize(int size) {
        if (size >= 2 || size <= 4) {
            return true;
        }

        return false;
    }

    public String shipCoordsToString() {
        String string = "";
        for (List<Integer> coordinate : shipCoordinates) {
            string += "[" + (coordinate.get(0) + 1) + ", " + ( -coordinate.get(1) + 10) + "]  ";
        }
        return string;
    }
}
