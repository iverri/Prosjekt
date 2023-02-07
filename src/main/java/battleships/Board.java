package battleships;

import java.util.List;
import java.util.ArrayList;

public class Board {

    
    private List<List<Integer>> matrix = new ArrayList<>();
    private List<Integer> row = new ArrayList<>();

    public Board() {
        for (int i = 0; i < 10; i ++) {
            for (int n = 0; n < 10; n ++) {
                this.row.add(0);
            }
            this.matrix.add(row);
            this.row = new ArrayList<>();
        }
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    public String toString() {
    String string = "";
    for (int i = 0; i < 10; i++) {
        for (int n = 0; n < 10; n ++) {
            string += "" + matrix.get(i).get(n) + "  ";
            }
        string += "\n";
        }

    return string;
    }

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());
    }
}
