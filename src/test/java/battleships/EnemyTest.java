package battleships;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private Player player;
    private Enemy enemy;
    
    @BeforeEach
    public void setupGame() {
        enemy = new Enemy();
        player = new Player();
        player.placeShip(2, 2, 0, "right");
        player.placeShip(4, 4, 1, "right");
        player.placeShip(10, 10, 2, "down");
        player.placeShip(1, 10, 3, "right");
        player.placeShip(10, 1, 4, "left");
    }

    @Test
    public void testShot() {    
        
        IntStream.range(0, 100).
        forEach((i) -> enemy.shoot(player.getPlayerBoard()));

        Integer shotTiles = 0;

        List<List<Integer>> matrix = player.getPlayerBoard().getMatrix();
        for (List<Integer> y : matrix) {
            for (Integer x : y) {
                if (x == 2 || x == 3) {
                    shotTiles += 1;
                }
            }
        }
        assertEquals(100, shotTiles);
        
    }

    @Test
    public void testPlaceEnemyShips() {
        enemy.placeEnemyShips();
        List<List<Integer>> matrix = enemy.getEnemyBoard().getMatrix();
        List<Ship> enemyShips = enemy.getEnemyShips();
        List<List<Integer>> shipPlacements = new ArrayList<>();
        int shipTileCount = 0;
        int yInt = 0;
        for (List<Integer> y : matrix) {
            int xInt = 0;
            for (Integer x : y) {
                if (x == 1) {
                    shipTileCount += 1;
                    shipPlacements.add(Arrays.asList(xInt, yInt));
                }
                xInt += 1;
            }
            yInt += 1;
        }
        assertEquals(14, shipTileCount);
        
        for (Ship ship : enemyShips) {
            for (List<Integer> coordinate : ship.getShipCoordinates()) {
                assertTrue(shipPlacements.contains(coordinate));
            }
        }
    }
}
