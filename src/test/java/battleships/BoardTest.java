package battleships;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardTest {
    
    Board board;
    Player player;

    @Test
    public void testConstructor() {
        board = new Board();

        List<Integer> row = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<List<Integer>> referenceMatrix = new ArrayList<>();
        for (List<Integer> boardRow : board.getMatrix()) {
            referenceMatrix.add(row);
        }

        assertEquals(referenceMatrix, board.getMatrix());
    }

    @Test
    public void testToString() {
        board = new Board();
        player = new Player();
        String boardString = "0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n";
        assertEquals(boardString, board.toString());
        player.shoot(1, 10, board);
        boardString = "2  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n";
        assertEquals(boardString, board.toString());
        player.placeShip(1, 10, 0, "right");
        boardString = "1  1  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n0  0  0  0  0  0  0  0  0  0  \n";
        assertEquals(boardString, player.getPlayerBoard().toString());
    }
}
