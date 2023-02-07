package battleships;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipTest {
    
    Ship ship;
    Player player;

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            ship = new Ship(5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ship = new Ship(-1);
        });
        
        ship = new Ship(4);
        assertTrue(ship.getSize() == 4);
        assertTrue(ship.getHitPoints() == 4);
    }

    @Test
    public void testShipCoordsToString() {
        player = new Player();
        player.placeShip(1, 1, 0, "right");
        assertTrue(player.getPlayerShips().get(0).shipCoordsToString().equals("[1, 1]  [2, 1]  "));
    }

}
