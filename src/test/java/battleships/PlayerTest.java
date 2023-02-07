package battleships;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;


public class PlayerTest {
    
Player player;
Enemy enemy;
Game game;

    @BeforeEach
    public void setupGame() {
        enemy = new Enemy();
        player = new Player();
        game = new Game(player, enemy);
        enemy.placeShip(1, 1, enemy.getEnemyShips().get(0), "right");
        player.setEnemyShips(enemy.getEnemyShips());
    }

    @Test
    public void testPlaceShip() {
        assertTrue(player.getPlayerBoard().getMatrix().get(9).get(0) == 0 && player.getPlayerBoard().getMatrix().get(9).get(1) == 0);
        player.placeShip(1, 1, 0, "right");
        assertTrue(player.getPlayerBoard().getMatrix().get(9).get(0) == 1 && player.getPlayerBoard().getMatrix().get(9).get(1) == 1);
        assertTrue(player.getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(0, 9)) && player.getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(1, 9)));
        
        assertTrue(player.getPlayerBoard().getMatrix().get(9).get(9) == 0 && player.getPlayerBoard().getMatrix().get(8).get(9) == 0);
        player.placeShip(10, 1, 1, "up");
        assertTrue(player.getPlayerBoard().getMatrix().get(9).get(9) == 1 && player.getPlayerBoard().getMatrix().get(8).get(9) == 1);
        assertTrue(player.getPlayerShips().get(1).getShipCoordinates().contains(Arrays.asList(9, 9)) && player.getPlayerShips().get(1).getShipCoordinates().contains(Arrays.asList(9, 8)));
        
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(9) == 0 && player.getPlayerBoard().getMatrix().get(0).get(8) == 0 && player.getPlayerBoard().getMatrix().get(0).get(7) == 0);
        player.placeShip(10, 10, 2, "left");
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(9) == 1 && player.getPlayerBoard().getMatrix().get(0).get(8) == 1 && player.getPlayerBoard().getMatrix().get(0).get(7) == 1);
        assertTrue(player.getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(9, 0)) && player.getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(8, 0)) && player.getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(7, 0)));
        
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(0) == 0 && player.getPlayerBoard().getMatrix().get(1).get(0) == 0 && player.getPlayerBoard().getMatrix().get(2).get(0) == 0);
        player.placeShip(1, 10, 3, "down");
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(0) == 1 && player.getPlayerBoard().getMatrix().get(1).get(0) == 1 && player.getPlayerBoard().getMatrix().get(2).get(0) == 1);
        assertTrue(player.getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(0, 0)) && player.getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(0, 1)) && player.getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(0, 2)));

        assertThrows(IllegalArgumentException.class, ()-> {
            player.placeShip(5, 1, 4, "down");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(1, 1, 4, "right");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(5, 1, 0, "right");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(11, 1, 4, "right");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(1, 11, 4, "right");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(5, 1, 6, "up");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.placeShip(5, 1, 4, "balls");
        });
    }

    @Test
    public void testShoot() {
        player.shoot(2, 9, enemy.getEnemyBoard());
        assertTrue(enemy.getEnemyBoard().getMatrix().get(1).get(1) == 3);
        assertTrue(enemy.getEnemyShips().get(0).getHitPoints() == 1);
        assertFalse(enemy.getEnemyShips().get(0).getShipCoordinates().contains(Arrays.asList(1, 1)));
        assertTrue(enemy.getEnemyShips().get(0).getHitCoordinates().contains(Arrays.asList(1, 1)));
        assertTrue(player.getTotalHits() == 1);
        player.shoot(3, 9, enemy.getEnemyBoard());
        assertTrue(enemy.getEnemyBoard().getMatrix().get(1).get(2) == 3);
        assertTrue(enemy.getEnemyShips().get(0).getHitPoints() == 0);
        assertTrue(enemy.getEnemyShips().get(0).getShipCoordinates().isEmpty());
        assertTrue(enemy.getEnemyShips().get(0).getHitCoordinates().contains(Arrays.asList(1, 1)) && enemy.getEnemyShips().get(0).getHitCoordinates().contains(Arrays.asList(2, 1)));
        assertTrue(player.getTotalHits() == 2);
        player.shoot(10, 10, enemy.getEnemyBoard());
        assertTrue(enemy.getEnemyBoard().getMatrix().get(0).get(9) == 2);
        assertTrue(player.getTotalHits() == 2);
    }
}
