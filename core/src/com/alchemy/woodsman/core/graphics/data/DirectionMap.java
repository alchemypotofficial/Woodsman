package com.alchemy.woodsman.core.graphics.data;

public class DirectionMap {

    private boolean[][] directions;

    public DirectionMap(boolean[][] directions) {
        this.directions = directions;
    }

    public final boolean[][] getDirections() {
        return this.directions;
    }

    public final boolean getTop() {
        return directions[1][2];
    }

    public final boolean getBottom() {
        return directions[1][0];
    }

    public final boolean getLeft() {
        return directions[0][1];
    }

    public final boolean getRight() {
        return directions[2][1];
    }

    public final boolean getTopLeft() {
        return directions[0][2];
    }

    public final boolean getTopRight() {
        return directions[2][2];
    }

    public final boolean getBottomLeft() {
        return directions[0][0];
    }

    public final boolean getBottomRight() {
        return directions[2][0];
    }
}
