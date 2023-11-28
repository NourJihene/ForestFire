package com.example.foret_kata.UI;

import com.example.foret_kata.entities.Cell;
import com.example.foret_kata.model.enumeration.CellState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellShape extends Rectangle {

    private Cell cell;
    public CellShape(double width, Color borderColor, double borderWidth, Cell cell) {
        super(width, width);
        setStroke(borderColor);
        setStrokeWidth(borderWidth);
        setCell(cell);
        setFill(getColorForCellState(cell.getState()));
    }


    public void setCell(Cell cell) {
        this.cell = cell;
    }

    private Color getColorForCellState(CellState state) {
        return switch (state) {
            case ON_FIRE -> Color.RED;
            case ASH -> Color.BLACK;
            default -> Color.GREEN;
        };
    }
    public void updateColor(){
        setFill(getColorForCellState(cell.getState()));
    }
}