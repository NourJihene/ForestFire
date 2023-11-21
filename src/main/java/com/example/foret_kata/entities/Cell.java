package com.example.foret_kata.entities;

import com.example.foret_kata.model.Position;
import com.example.foret_kata.model.CellState;

public class Cell {
    private CellState state;
    private final Position position;


    @Override
    public String toString() {
        return "Cell{" +
                "state=" + state +
                ", position=" + position +
                '}';
    }

    public Cell(Position position) {
        this.state = CellState.NOT_AFFECTED;
        this.position = position;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Position getPosition() {
        return position;
    }

}