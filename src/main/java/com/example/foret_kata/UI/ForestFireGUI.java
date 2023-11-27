package com.example.foret_kata.UI;
import com.example.foret_kata.util.PositionParser;
import com.example.foret_kata.util.PropertiesReader;
import javafx.animation.AnimationTimer;
import com.example.foret_kata.entities.Forest;
import com.example.foret_kata.model.Position;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ForestFireGUI extends Application {
    private final int CELL_SIZE = 30;
    private final double CELL_BORDER_WIDTH = 1.5;
    private String fileDirectory;
    private double propagation_probability;

    private Forest forest;
    private GridPane gridPane;
    private boolean isSimulationRunning = true;
    private static Position[] positions;

    public static void main(String[] args) {
        launch(args);
    }

    public void initializeForestProperties(){
        fileDirectory = "application.properties";
        InputStream stream = PropertiesReader.loadFile(fileDirectory);
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int height = Integer.parseInt(properties.getProperty("height"));
        int width = Integer.parseInt(properties.getProperty("width"));
        String positionsString = properties.getProperty("positions");
        positionsString = positionsString.replaceAll("[{}]", "");
        positions = PositionParser.parse(positionsString);
        propagation_probability = Double.parseDouble(properties.getProperty("propagation_probability"));
        forest = new Forest(height, width, positions);
    }
    @Override
    public void start(Stage stage)  {
        initializeForestProperties();
        gridPane = createGridPane();

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        stage.setTitle("Forest Fire Simulation");
        stage.show();
        startSimulationLoop(gridPane);
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        scene.setOnKeyPressed(event -> {
            if (keyCombination.match(event)) {
                stage.close();
            }
        });
    }
    private void updateGUI() {
        for (int i = 0; i < forest.getHeight(); i++) {
            for (int j = 0; j < forest.getWidth(); j++) {
                CellShape cellRectangle = (CellShape) this.gridPane.getChildren().get(i * forest.getWidth() + j);
                cellRectangle.updateColor();
            }
        }
    }
    private void runSimulation(double propagationProbability) {
        if (!forest.findCellsOnFire().isEmpty()) {
            forest.nextStep(propagationProbability);
            updateGUI();
        } else{
            isSimulationRunning = !isSimulationRunning;
        }
    }
    private void startSimulationLoop(GridPane gridPane) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isSimulationRunning) {
                    runSimulation(propagation_probability);
                }
                stop();
            }
        };

        EventHandler<KeyEvent> keyEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                timer.start();
            }
        };
        Scene scene = gridPane.getScene();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
    }


    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < forest.getHeight(); i++) {
            for (int j = 0; j < forest.getWidth(); j++) {
                CellShape cellRectangle = new CellShape(CELL_SIZE, Color.GRAY, CELL_BORDER_WIDTH, forest.getCells()[i][j]);
                gridPane.add(cellRectangle, j, i);
            }
        }
        return gridPane;
    }

}

