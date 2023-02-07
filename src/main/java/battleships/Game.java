package battleships;

public class Game {
    
    private Player player;
    private Enemy enemy;
    private boolean canShoot = false;
    private boolean gameOver = false;

    public Game(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void playerPlaceShip(String xCoord, String yCoord, String shipNumber, String direction) {
        try {
            int xInt = Integer.parseInt(xCoord);
            int yInt = Integer.parseInt(yCoord);
            int shipNumberInt = Integer.parseInt(shipNumber) - 1;
            try {
                player.placeShip(xInt, yInt, shipNumberInt, direction);
                setCanShoot();
            } catch (IllegalArgumentException e) {
                String error = e.getLocalizedMessage();
                System.out.println(error);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ship Number must be a number between 1 and 5.\nX and Y must be numbers between 1 and 10.\nDirection must be either up, down, left or right.");
        }
    }

    public void enemyPlaceShips() {
        enemy.placeEnemyShips();
    }

    public void playerShoot(int xCoord, int yCoord) {
        Board board = enemy.getEnemyBoard();
        player.shoot(xCoord, yCoord, board);
    }

    public void enemyShoot() {
        Board board = player.getPlayerBoard();
        enemy.shoot(board);
    }

    public void playRound(String xCoord, String yCoord) {
        if (canShoot && !gameOver) {
            try {
                int xInt = Integer.parseInt(xCoord);
                int yInt = Integer.parseInt(yCoord);
                playerShoot(xInt, yInt);
                enemyShoot();
                checkGameOver();
            } catch (Exception NumberFormatException) {
                throw new IllegalArgumentException("X and Y must be numbers between 1 and 10.");
            }
        }
    }

    public boolean allShipsPlaced() {
        for (Ship ship : player.getPlayerShips()) {
            if (ship.getShipCoordinates().size() == 0 && ship.getHitCoordinates().size() == 0) {
                return false;
            }
        }
        return true;
    }

    public void checkGameOver() {
        if (player.getTotalHits() == 14 || enemy.getTotalHits() == 14) {
            gameOver = true;
        }
    }

    public void setCanShoot() {
        if (allShipsPlaced()) {
            System.out.println("The enemy placed their ships.\nReady to start game!");
            enemyPlaceShips();
            player.setEnemyShips(enemy.getEnemyShips());
            enemy.setPlayerShips(player.getPlayerShips());
            this.canShoot = true;
        } else {
            this.canShoot = false;
            System.out.println("Place remaining ships to start game.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public boolean getCanShoot() {
        return canShoot;
    }

    public static void main(String[] args) {
        Game game = new Game(new Player(), new Enemy());
        game.getPlayer().placeShip(2, 2, 0, "right");
        game.getPlayer().placeShip(4, 4, 1, "right");
        game.getPlayer().placeShip(10, 10, 2, "down");
        game.getPlayer().placeShip(1, 10, 3, "right");
        game.getPlayer().placeShip(10, 1, 4, "left");
        game.allShipsPlaced();
        Board board = game.getPlayer().getPlayerBoard();
        for (int i = 0; i < 100; i++) {
            game.enemyShoot();
        }
        System.out.println(board);
    }
}
