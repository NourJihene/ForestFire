package com.example.foret_kata.entities;
import com.example.foret_kata.model.Position;
import com.example.foret_kata.model.enumeration.CellState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Forest {
    private int height;
    private int width;

    private Cell[][] cells;

    public Forest(int height, int width, Position[] positions) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be positive integers.");
        }
        this.height = height;
        this.width = width;
        this.cells = this.initializeCells();
        setFireOn(positions);
        System.out.println("Initialization done: "+ this.toString());
    }

    private Cell[][] initializeCells(){
        Cell[][] newCells = new Cell[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                newCells[i][j] = new Cell(new Position(i,j));
            }
        }
        return newCells;
    }

    @Override
    public String toString() {
        return "Forest{" +
                "height=" + height +
                ", width=" + width +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }

    public int getHeight() {
        return height;
    }


    public int getWidth() {
        return width;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }
//    public void setFireOn(Position position){
//        int row = position.getRow();
//        int column = position.getColumn();
//        if (isValidPosition(row, column)) {
//            cells[row][column].setState(CellState.ON_FIRE);
//            System.out.println("Fire set on cell: " + cells[row][column].toString());
//        } else {
//            throw new IllegalArgumentException("Invalid coordinates for setting the fire.");
//        }
//    }
    public void setFireOn(Position[] positions) {
        for (Position position : positions) {
            int row = position.getRow();
            int column = position.getColumn();
            if (isValidPosition(row, column)) {
                cells[row][column].setState(CellState.ON_FIRE);
                System.out.println("Fire set on cell: " + cells[row][column].toString());
            } else {
                throw new IllegalArgumentException("Invalid coordinates for setting the fire at position: (" + row + ", " + column + ")");
            }
        }
    }
//    public List<Cell> findNotAffectedCells(){
//        List<Cell> notAffectedCells = new ArrayList<>();
//            for(int i = 0; i < height; i++){
//                for(int j =0; j < width; j++){
//                    Cell currentCell = cells[i][j];
//                    if(currentCell.getState() == CellState.NOT_AFFECTED){
//                        System.out.println("(searching) Cell not affected: " + currentCell.toString());
//                        notAffectedCells.add(currentCell);
//                    }
//                }
//            }
//        if (notAffectedCells.isEmpty()) {
//            System.out.println("All cells are effected, simulation complete.");
//        }
//        return notAffectedCells;
//    }
    public List<Cell> findCellsOnFire() {
        List<Cell> cellsOnFire = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell currentCell = this.cells[i][j];
                if (currentCell.getState() == CellState.ON_FIRE) {
                    System.out.println("(searching) Cell on fire: " + currentCell.toString());
                    cellsOnFire.add(currentCell);
                }
            }
        }
        if (cellsOnFire.isEmpty()) {
            System.out.println("No cell on fire.");
        }
        return cellsOnFire;
    }
    public void nextStep(double probability){
        List<Cell> cellsOnFire = findCellsOnFire();
        if(cellsOnFire.isEmpty()){
            System.out.println("No cell on fire.");
            return;
        }
        for(Cell cell : cellsOnFire){
            cell.setState(CellState.ASH);
            propagateFire(cell.getPosition(), probability);
        }
    }
    private void propagateFire(Position position, double probability) {
        int row = position.getRow();
        int column = position.getColumn();
        Position[] neighbors = {
                new Position(row - 1, column),
                new Position (row + 1, column),
                new Position (row, column - 1),
                new Position (row, column + 1)
        };
        for (Position neighbor : neighbors) {
            if (isValidPosition(neighbor.getRow(), neighbor.getColumn())) {
                Cell neighborCell = cells[neighbor.getRow()][neighbor.getColumn()];
                if (neighborCell.getState() == CellState.NOT_AFFECTED && Math.random() < probability) {
                    neighborCell.setState(CellState.ON_FIRE);
                    System.out.println("Fire propagated to cell: " + neighborCell.toString());
                }

            }
        }
    }

        }
