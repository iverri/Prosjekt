package battleships;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class ShipFormationTest {

    Game game;
    ShipFormation shipFormation;

    @Test
    public void testConstructor() {
        game = new Game(new Player(), new Enemy());
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("*", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation(".", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation(",", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("/", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation(":", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("<", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("\\", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation(">", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("\"", game);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shipFormation = new ShipFormation("|", game);
        });

    }

    @Test
    public void testSaveLoad() {
        try {
            game = new Game(new Player(), new Enemy());
            shipFormation = new ShipFormation("test", game);
            assertThrows(IllegalArgumentException.class, () -> {
                shipFormation.save();
            });
            game.playerPlaceShip("2", "2", "1", "right");
            game.playerPlaceShip("4", "4", "2", "right");
            game.playerPlaceShip("10", "10", "3", "down");
            game.playerPlaceShip("1", "10", "4", "right");
            game.playerPlaceShip("10", "1", "5", "left");
            Board referenceBoard = new Board();
            Board emptyBoard = new Board();
            referenceBoard.setMatrix(game.getPlayer().getPlayerBoard().getMatrix());
            shipFormation.save();
            
            game = new Game(new Player(), new Enemy());
            assertFalse(game.getPlayer().getPlayerBoard().getMatrix().equals(referenceBoard.getMatrix()));
            assertTrue(game.getPlayer().getPlayerBoard().getMatrix().equals(emptyBoard.getMatrix()));
            for (Ship ship : game.getPlayer().getPlayerShips()) {
                assertTrue(ship.getShipCoordinates().isEmpty());
            }

            game = shipFormation.load();
            assertFalse(game.getPlayer().getPlayerBoard().getMatrix().equals(emptyBoard.getMatrix()));
            assertTrue(game.getPlayer().getPlayerBoard().getMatrix().equals(referenceBoard.getMatrix()));
            assertTrue(game.getPlayer().getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(1, 8)) && game.getPlayer().getPlayerShips().get(0).getShipCoordinates().contains(Arrays.asList(2, 8)));
            assertTrue(game.getPlayer().getPlayerShips().get(1).getShipCoordinates().contains(Arrays.asList(3, 6)) && game.getPlayer().getPlayerShips().get(1).getShipCoordinates().contains(Arrays.asList(4, 6)));
            assertTrue(game.getPlayer().getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(9, 0)) && game.getPlayer().getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(9, 1)) && game.getPlayer().getPlayerShips().get(2).getShipCoordinates().contains(Arrays.asList(9, 2)));
            assertTrue(game.getPlayer().getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(0, 0)) && game.getPlayer().getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(1, 0)) && game.getPlayer().getPlayerShips().get(3).getShipCoordinates().contains(Arrays.asList(2, 0)));
            assertTrue(game.getPlayer().getPlayerShips().get(4).getShipCoordinates().contains(Arrays.asList(9, 9)) && game.getPlayer().getPlayerShips().get(4).getShipCoordinates().contains(Arrays.asList(8, 9)) && game.getPlayer().getPlayerShips().get(4).getShipCoordinates().contains(Arrays.asList(7, 9)) && game.getPlayer().getPlayerShips().get(4).getShipCoordinates().contains(Arrays.asList(6, 9)));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
