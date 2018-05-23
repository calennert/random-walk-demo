package com.github.calennert.rwdemo;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller class for main.fxml.
 *
 * @author calennert (Chris Lennert)
 */
public final class MainController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private FlowPane buttonPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Pane canvasPane;

    private CanvasParams canvasParams;

    @FXML
    private ComboBox<GridSize> cboGridSize;

    private SimpleBooleanProperty graphCompleted = new SimpleBooleanProperty(false);

    private Grid grid;

    private boolean interruptTask;

    @FXML
    private BorderPane rootPane;

    @FXML
    private GridPane settingsPane;

    @FXML
    private Slider sliderInterval;

    private Point testPoint = null;

    @FXML
    private TextField txtInterval;

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    private void createBaseGrid(GraphicsContext gc) {
        gc.setFill(new Color(0.9, 0.9, 0.9, 1));

        for (int x = 0; x < this.grid.getWidth(); x++) {
            for (int y = 0; y < this.grid.getHeight(); y++) {
                Point p = new Point(x, y);
                Point ulPoint = this.canvasParams.gridPointToCanvasElementUL(p);
                gc.fillRoundRect(ulPoint.getX(), ulPoint.getY(), this.canvasParams.getElementSize(),
                        this.canvasParams.getElementSize(), this.canvasParams.getElementArcSize(),
                        this.canvasParams.getElementArcSize());
            }
        }
    }

    private void drawGridPoints(GraphicsContext gc) {
        gc.setFill(Color.DARKRED);

        for (final Point gp : this.grid.getPoints()) {
            Point ulPoint = this.canvasParams.gridPointToCanvasElementUL(gp);
            gc.fillRoundRect(ulPoint.getX(), ulPoint.getY(), this.canvasParams.getElementSize(),
                    this.canvasParams.getElementSize(), this.canvasParams.getElementArcSize(),
                    this.canvasParams.getElementArcSize());
        }
    }

    private void drawTestPoint(GraphicsContext gc) {
        if (this.testPoint != null) {
            gc.setFill(Color.GREEN);
            Point ulPoint = this.canvasParams.gridPointToCanvasElementUL(this.testPoint);
            gc.fillRoundRect(ulPoint.getX(), ulPoint.getY(), this.canvasParams.getElementSize(),
                    this.canvasParams.getElementSize(), this.canvasParams.getElementArcSize(),
                    this.canvasParams.getElementArcSize());
        }
    }

    @FXML
    private void handleBtnExitOnAction(ActionEvent event) {
        this.interruptTask = true;
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleBtnResetOnAction(ActionEvent event) {
        this.grid.reset();
        this.grid.addPoint(this.grid.centerPoint());
        this.testPoint = null;
        this.graphCompleted.set(false);
        renderCanvas();
    }

    @FXML
    private void handleBtnStartOnAction(ActionEvent event) {
        this.cboGridSize.setDisable(true);
        this.btnStart.setDisable(true);
        this.btnStop.setDisable(false);
        this.btnReset.setDisable(true);

        this.interruptTask = false;

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                while (true) {
                    if (MainController.this.testPoint == null) {
                        MainController.this.testPoint = MainController.this.grid.randomPerimeterPoint();
                    }

                    renderCanvas();

                    MainController.this.testPoint = PointUtil.randomNeighbor(MainController.this.testPoint,
                            MainController.this.grid);

                    Point lastAdded = null;
                    if (MainController.this.grid.hasAdjacentTo(MainController.this.testPoint)) {
                        MainController.this.grid.addPoint(MainController.this.testPoint);
                        lastAdded = MainController.this.testPoint;
                        MainController.this.testPoint = null;
                    }

                    try {
                        Thread.sleep((long) MainController.this.sliderInterval.getValue());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (MainController.this.interruptTask) {
                        break;
                    }
                    else if (MainController.this.grid.isPerimeterPoint(lastAdded)) {
                        MainController.this.graphCompleted.set(true);
                        break;
                    }
                }

                return null;
            }
        };

        task.setOnSucceeded(e -> {
            this.cboGridSize.setDisable(false);
            this.btnStop.setDisable(true);
            this.btnReset.setDisable(false);

            this.btnStart.setDisable(this.graphCompleted.get());
        });

        new Thread(task).start();
    }

    @FXML
    private void handleBtnStopOnAction(ActionEvent event) {
        this.interruptTask = true;
        this.testPoint = null;
        renderCanvas();
    }

    @FXML
    private void initialize() {
        this.graphCompleted.addListener((observable, oldValue, newValue) -> {
            this.btnStart.setDisable(newValue);
        });

        this.sliderInterval.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.txtInterval.textProperty().set(Integer.toString(newValue.intValue()));
        });
        this.txtInterval.textProperty().set(Integer.toString(this.sliderInterval.valueProperty().intValue()));

        this.txtInterval.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer sliderValue = Integer.decode(this.txtInterval.textProperty().get());
            if (sliderValue > this.sliderInterval.getMax()) {
                sliderValue = (int) this.sliderInterval.getMax();
                this.txtInterval.textProperty().set(Integer.toString(sliderValue));
            }
            else if (sliderValue < this.sliderInterval.getMin()) {
                sliderValue = (int) this.sliderInterval.getMin();
                this.txtInterval.textProperty().set(Integer.toString(sliderValue));
            }
            this.sliderInterval.valueProperty().setValue(sliderValue);
        });

        this.cboGridSize.getItems().setAll(GridSize.orderedValues());
        this.cboGridSize.valueProperty().set(GridSize.Default);
        this.grid = GridSize.Default.getGrid();
        this.canvasParams = GridSize.Default.getCanvasParams();

        this.cboGridSize.setOnAction((event) -> {
            GridSize selectedGridSize = this.cboGridSize.getSelectionModel().getSelectedItem();
            this.grid = selectedGridSize.getGrid();
            this.canvasParams = selectedGridSize.getCanvasParams();
            this.grid.addPoint(this.grid.centerPoint());
            resizeCanvasAndStage();
            renderCanvas();
        });

        this.grid.addPoint(this.grid.centerPoint());

        resizeCanvasAndStage();
        renderCanvas();
    }

    private void renderCanvas() {
        Platform.runLater(() -> {
            GraphicsContext gc = this.canvas.getGraphicsContext2D();
            clearCanvas(gc);
            createBaseGrid(gc);
            drawGridPoints(gc);
            drawTestPoint(gc);
        });
    }

    private void resizeCanvasAndStage() {
        Point canvasSize = this.canvasParams.getCanvasSize(this.grid);
        this.canvas.setWidth(canvasSize.getX());
        this.canvas.setHeight(canvasSize.getY());

        Platform.runLater(() -> {
            Stage stage = (Stage) this.canvas.getScene().getWindow();
            stage.setWidth(canvasSize.getX());
            stage.setHeight(this.settingsPane.getHeight() + canvasSize.getY() + this.buttonPane.getHeight());
            stage.centerOnScreen();
        });
    }
}
