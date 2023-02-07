package battleships;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShipFormation implements Saver{

    private String name;
    private Game game;
    private String filePath = "./src/main/resources/savedGame/";

    public ShipFormation(String name, Game game) {
        if (name.equals("") || name.contains("*") || name.contains(".") || name.contains(",") || name.contains("/") || name.contains(":") || name.contains("<") || name.contains("\\") || name.contains(">") || name.contains("\"") || name.contains("|")) {
            throw new IllegalArgumentException("This is not a legal team name!");
        } else {
            this.name = name;
            this.game = game;
        }

    }
    
    public void save() throws IOException{
        if (!game.allShipsPlaced()) {
            throw new IllegalArgumentException("You must place all your ships before saving!");
        } else {
            try (FileWriter fileWriter = new FileWriter(filePath + getName() + ".txt")) {
                List<Ship> playerShips = game.getPlayer().getPlayerShips();
                String playerShipCoords = "";
                for (Ship ship : playerShips) {
                    List<List<Integer>> coordinates = ship.getShipCoordinates();
                    for (List<Integer> coordinate: coordinates) {
                        for (Integer value : coordinate) {
                            playerShipCoords += value;
                        }
                        playerShipCoords += " ";
                    }
                    playerShipCoords += "\n";
                }
                fileWriter.write(playerShipCoords);
                fileWriter.close();
            } catch (IOException e) {

            }
        }
    }

    public Game load() throws IOException{
        try (Scanner scanner = new Scanner(new File(filePath + getName() + ".txt"))) {
            List<String> fileContent = new ArrayList<>();
            while (scanner.hasNextLine()) {
                fileContent.add(scanner.nextLine());
            }
            Player player = new Player();
            int shipIndex = 0;
            for (String line : fileContent) {
                String[] coordinates = line.split(" ");
                List<List<Integer>> shipCoordinates = new ArrayList<>();
                for (String coordinate : coordinates) {
                    String[] values = coordinate.split("");
                    List<Integer> shipCoordinate = new ArrayList<>();
                    for (String value : values) {
                        shipCoordinate.add(Integer.parseInt(value));
                    }
                    shipCoordinates.add(shipCoordinate);
                }
                player.getPlayerShips().get(shipIndex).setShipCoordinates(shipCoordinates);
                shipIndex += 1;
            }

            List<List<Integer>> matrix = player.getPlayerBoard().getMatrix();
            for (Ship ship : player.getPlayerShips()) {
                List<List<Integer>> shipCoordinates = ship.getShipCoordinates();
                for (List<Integer> coordinates : shipCoordinates) {
                    matrix.get(coordinates.get(1)).set(coordinates.get(0), 1);
                }
            }
            player.getPlayerBoard().setMatrix(matrix);
            Game game = new Game(player, new Enemy());
            return game;
        } 
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
