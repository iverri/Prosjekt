package battleships;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class BattleshipsController {
    
    @FXML
    private GridPane playerBoard, enemyBoard, savedGameGridPane;

    @FXML
    private TextArea gameLog, rulesTextArea;

    @FXML
    private Button shootButton, placeButton, newGameButton, saveLoadButton, saveGameButton, readyButton, closeSavePane;

    @FXML
    private TextField playerNameInput, xInput, yInput, directionInput, shipNumberInput, saveGameName;

    @FXML
    private Label shipNumberLabel, directionLabel, gameOverLabel;

    @FXML
    private Pane gameOverPane, savedGamePane, rulesPane;

    private Game game;
    private PrintStream ps;
    private static String filepath = "./src/main/resources/savedGame/";

    @FXML
    public void initialize() {
        initializeRuleText();
        game = new Game(new Player(), new Enemy());
        ps = new PrintStream(new GameLog(gameLog));
        System.setOut(ps);
        System.setErr(ps);
        getShipFormationsFromFile();
        shootButton.setVisible(false);
        placeButton.setVisible(true);
        savedGamePane.setVisible(false);
    }

    @FXML
    private void updateGameLog(ActionEvent event) {
        System.setOut(ps);
        System.setErr(ps);
    }

    @FXML
    private void placeShip(ActionEvent event) throws IOException{
        String xCoord = xInput.getText();
        String yCoord = yInput.getText();
        String shipNumber = shipNumberInput.getText();
        String direction = directionInput.getText();
        game.playerPlaceShip(xCoord, yCoord, shipNumber, direction);
        try {
            updatePreparationsBoard();
            updateGameLog(event);
            if (game.getCanShoot()) {
                updateVisibility();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @FXML
    private void shoot(ActionEvent event) {
        String xCoord = xInput.getText();
        String yCoord = yInput.getText();
        try {
            game.playRound(xCoord, yCoord);
            updateGameBoard();
            updateGameLog(event);
            game.checkGameOver();
                if (game.getGameOver()) {
                    handleGameOver();
                }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @FXML
    private void updateVisibility() {
        shootButton.setVisible(true);
        placeButton.setVisible(false);
        shipNumberInput.setVisible(false);
        directionInput.setVisible(false);
        directionLabel.setVisible(false);
        shipNumberLabel.setVisible(false);
    }

    @FXML
    private void revertVisibility() {
        shootButton.setVisible(false);
        placeButton.setVisible(true);
        shipNumberInput.setVisible(true);
        directionInput.setVisible(true);
        directionLabel.setVisible(true);
        shipNumberLabel.setVisible(true);
        saveLoadButton.setVisible(true);
    }

    @FXML
    private void updatePreparationsBoard() {
        List<List<Integer>> matrix = game.getPlayer().getPlayerBoard().getMatrix();
        int yValueInteger = 0;
        for (List<Integer> yValue : matrix) {
            int xValueInteger = 0;
            for (int xValue : yValue) {
                if (xValue == 1) {
                    Pane shipPart = new Pane();
                    shipPart.setStyle("-fx-background-color: #696969;");
                    playerBoard.add(shipPart, xValueInteger, yValueInteger);
                }
                xValueInteger += 1;
            }
            yValueInteger += 1;
        }
    }

    @FXML
    private void updateGameBoard() {
        List<List<Integer>> enemyMatrix = game.getEnemy().getEnemyBoard().getMatrix();
        List<List<Integer>> playerMatrix = game.getPlayer().getPlayerBoard().getMatrix();
        int yValueIntegerEnemy = 0;
        for (List<Integer> yValue : enemyMatrix) {
            int xValueIntegerEnemy = 0;
            for (int xValue : yValue) {
                if (xValue == 2) {
                    Pane shipPart = new Pane();
                    shipPart.setStyle("-fx-background-color: #000080;");
                    enemyBoard.add(shipPart, xValueIntegerEnemy, yValueIntegerEnemy);
                } else if (xValue == 3) {
                    Pane shipPart = new Pane();
                    shipPart.setStyle("-fx-background-color: #B22222;");
                    enemyBoard.add(shipPart, xValueIntegerEnemy, yValueIntegerEnemy);
                }
                xValueIntegerEnemy += 1;
            }
            yValueIntegerEnemy += 1;
        }

        int yValueIntegerPlayer = 0;
        for (List<Integer> yValue : playerMatrix) {
            int xValueIntegerPlayer = 0;
            for (int xValue : yValue) {
                if (xValue == 2) {
                    Pane shipPart = new Pane();
                    shipPart.setStyle("-fx-background-color: #000080;");
                    playerBoard.add(shipPart, xValueIntegerPlayer, yValueIntegerPlayer);
                } else if (xValue == 3) {
                    Pane shipPart = new Pane();
                    shipPart.setStyle("-fx-background-color: #B22222;");
                    playerBoard.add(shipPart, xValueIntegerPlayer, yValueIntegerPlayer);
                }
                xValueIntegerPlayer += 1;
            }
            yValueIntegerPlayer += 1;
        }
    }

    @FXML
    private void clearBoard(GridPane board) {
        Node node = board.getChildren().get(0);
        board.getChildren().clear();
        board.getChildren().add(0, node);
    }

    @FXML
    private void handleGameOver() {
        shipNumberInput.editableProperty().set(false);
        xInput.editableProperty().set(false);
        yInput.editableProperty().set(false);
        directionInput.editableProperty().set(false);
        gameOverPane.visibleProperty().set(true);
        if (game.getPlayer().getTotalHits() == 14) {
            gameOverLabel.setText("You Win!");
            gameOverLabel.setLayoutX(153);
            gameOverLabel.setLayoutY(70);
        } else {
            gameOverLabel.setText("You Lose");
            gameOverLabel.setLayoutX(150);
            gameOverLabel.setLayoutY(70);
        }
    }

    @FXML
    private void startNewGame(ActionEvent event) {
        gameOverPane.visibleProperty().set(false);
        game = new Game(new Player(), new Enemy());
        clearBoard(enemyBoard);
        clearBoard(playerBoard);
        revertVisibility();
        shipNumberInput.editableProperty().set(true);
        xInput.editableProperty().set(true);
        yInput.editableProperty().set(true);
        directionInput.editableProperty().set(true);
        ps = new PrintStream(new GameLog(gameLog));
        System.setOut(ps);
        System.setErr(ps);
    }

    @FXML
    private void showSaveLoadWindow(ActionEvent event) {
        if (game.getEnemy().getTotalHits() == 0) {
            savedGamePane.setVisible(true);
        } else {
            System.out.println("You have been hit and can therefore not save the formation or load a new one.");
        }
    }

    @FXML
    private void closeSaveGameWindow(ActionEvent event) {
        savedGamePane.setVisible(false);
    }

    @FXML
    private void saveFormation(ActionEvent event) throws IOException {
        try {
            String formationName = saveGameName.getText();
            ShipFormation formation = new ShipFormation(formationName, game);
            formation.save();
            savedGamePane.setVisible(false);
            getShipFormationsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getLocalizedMessage());
        }    
    }

    @FXML
    private void loadFormation(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        ShipFormation selectedFormation = new ShipFormation(button.getText(), new Game(new Player(), new Enemy()));
        Game selectedGame = selectedFormation.load();
        this.game = selectedGame;
        savedGamePane.setVisible(false);
        clearBoard(playerBoard);
        updatePreparationsBoard();
        if (game.allShipsPlaced()) {
            updateVisibility();
            game.setCanShoot();
        }
    }

    private void getShipFormationsFromFile() {
        File dir = new File(filepath);
        String[] formations = dir.list();
        List<String> formationNames = new ArrayList<>();
        Arrays.stream(formations)
        .map((str)-> str.split("\\.")[0]).
        forEach((str) -> formationNames.add(str));
        if (formationNames.size() > 6) {
            IntStream.range(0, 6).
            forEach((i) -> savedGameGridPane.add(createFormationButton(new ShipFormation(formationNames.get(i), new Game(new Player(), new Enemy()))), 0, i));
        } else {
            IntStream.range(0, formationNames.size()).
            forEach((i) -> savedGameGridPane.add(createFormationButton(new ShipFormation(formationNames.get(i), new Game(new Player(), new Enemy()))), 0, i));   
        }
    }

    @FXML
    private Button createFormationButton(ShipFormation formation) {
        Button button = new Button(formation.getName());
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center");
        button.setOnAction((event) -> {
            try {
                loadFormation(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    @FXML
    private void readyButtonPressed(ActionEvent event) {
        rulesPane.setVisible(false);
    }

    @FXML
    private void initializeRuleText() {
        rulesTextArea.setText("""
                Welcome to Battleships!

                The rules are simple. Place your ships on the board, and the try to find and sink your enemy's ships.
                You each have 5 ships who differ in length. 
                To place a ship write down the ship's number (1-5), the desired starting-coordinate (x, y), and desired direction before pressing the place-button.
                You can save your ships placement for use in a later game.
                Once all ships are placed you can then shoot at the enemy. Write down the coordinate you wish to aim at (x, y between 1 and 10), and then press the shoot-button.
                If you miss the boeard or shoot somewhere you already have shot, then the enemy gets a free shot at your board. 
                The game ends when one side's ships have all been sunk.
                Press the \"I'm Ready!\"-button to start the game.
                Good luck!
                """);
    }

    public class GameLog extends OutputStream {
        private TextArea gameLog;

        public GameLog(TextArea gameLog) {
            this.gameLog = gameLog;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> gameLog.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }

    
}
