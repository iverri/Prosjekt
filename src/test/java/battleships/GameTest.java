package battleships;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameTest {
    
    Game game;
    Player player;
    Enemy enemy;

    @Test
    public void testConstructor() {
        player = new Player();
        enemy = new Enemy();
        game = new Game(player, enemy);
        assertTrue(player.getPlayerShips().size() == 5 && enemy.getEnemyShips().size() == 5);
        assertTrue(enemy.getTotalHits() == 0 && player.getTotalHits() == 0);

        Board board = new Board();
        assertTrue(player.getPlayerBoard().getMatrix().equals(board.getMatrix()) && enemy.getEnemyBoard().getMatrix().equals(board.getMatrix()));
        assertFalse(game.getGameOver());
        assertFalse(game.getCanShoot());
    }

    @Test
    public void testPlayerPlaceShip() {
        player = new Player();
        game = new Game(player, new Enemy());
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(0) == 0 && player.getPlayerBoard().getMatrix().get(0).get(1) == 0);
        game.playerPlaceShip("1", "10", "1", "right");
        assertTrue(player.getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(0, 0)) && player.getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(1, 0)));
        assertTrue(player.getPlayerBoard().getMatrix().get(0).get(0) == 1 && player.getPlayerBoard().getMatrix().get(0).get(1) == 1);
        assertThrows(IllegalArgumentException.class, () -> {
            game.playerPlaceShip("abc", "zdv", "hæ", "right");
        });
    }

    @Test
    public void testEnemyPlaceShips() {
        enemy = new Enemy();
        game = new Game(new Player(), enemy);
        game.enemyPlaceShips();
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

    @Test
    public void testPlayRound() {
        player = new Player();
        enemy = new Enemy();
        game = new Game(player, enemy);
        Board referenceBoard = new Board();
        game.playRound("1", "2");
        assertTrue(player.getPlayerBoard().getMatrix().equals(referenceBoard.getMatrix()) && enemy.getEnemyBoard().getMatrix().equals(referenceBoard.getMatrix()));
        
        player.placeShip(2, 2, 0, "right");
        player.placeShip(4, 4, 1, "right");
        player.placeShip(10, 10, 2, "down");
        player.placeShip(1, 10, 3, "right");
        player.placeShip(10, 1, 4, "left");
        game.setCanShoot();
        assertTrue(game.getCanShoot());
        game.playRound("1", "2");
        assertTrue(enemy.getEnemyBoard().getMatrix().get(8).get(0) == 2 || enemy.getEnemyBoard().getMatrix().get(8).get(0) == 3);
        int isShotCount = 0;
        for (List<Integer> y : player.getPlayerBoard().getMatrix()) {
            for (Integer x : y) {
                if (x == 2 || x == 3) {
                    isShotCount += 1;
                }
            }
        }
        assertTrue(isShotCount == 1);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playRound("a", "b");
        });
    }

    @Test
    public void testAllShipsPlaced() {
        player = new Player();
        game = new Game(player, new Enemy());
        assertFalse(game.allShipsPlaced());
        player.placeShip(2, 2, 0, "right");
        assertFalse(game.allShipsPlaced());
        player.placeShip(4, 4, 1, "right");
        assertFalse(game.allShipsPlaced());
        player.placeShip(10, 10, 2, "down");
        assertFalse(game.allShipsPlaced());
        player.placeShip(1, 10, 3, "right");
        assertFalse(game.allShipsPlaced());
        player.placeShip(10, 1, 4, "left");
        assertTrue(game.allShipsPlaced());
    }

    @Test
    public void testSetCanShoot() {
        player = new Player();
        game = new Game(player, new Enemy());
        assertFalse(game.getCanShoot());
        player.placeShip(2, 2, 0, "right");
        game.setCanShoot();
        assertFalse(game.getCanShoot());
        player.placeShip(4, 4, 1, "right");
        game.setCanShoot();
        assertFalse(game.getCanShoot());
        player.placeShip(10, 10, 2, "down");
        game.setCanShoot();
        assertFalse(game.getCanShoot());
        player.placeShip(1, 10, 3, "right");
        game.setCanShoot();
        assertFalse(game.getCanShoot());
        player.placeShip(10, 1, 4, "left");
        game.setCanShoot();
        assertTrue(game.getCanShoot());
    }

    @Test
    public void testCheckGameOver() {
        player = new Player();
        enemy = new Enemy();
        game = new Game(player, enemy);
        assertFalse(game.getGameOver());
        player.placeShip(2, 2, 0, "right");
        player.placeShip(4, 4, 1, "right");
        player.placeShip(10, 10, 2, "down");
        player.placeShip(1, 10, 3, "right");
        player.placeShip(10, 1, 4, "left");
        for (int i = 0; i < 100; i++) {
            game.playRound("1", "2");
            game.checkGameOver();
            if (game.getGameOver()) {
                break;
            }
        }
    }
}
