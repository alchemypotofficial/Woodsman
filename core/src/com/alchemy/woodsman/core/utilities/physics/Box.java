package com.alchemy.woodsman.core.utilities.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Box {

    public static Box ZERO = new Box(0, 0, 0, 0);
    public static Box ONE = new Box(0, 0, 1f, 1f);

    public float x;
    public float y;
    public float width;
    public float height;

    public Box() {
        this.x = ZERO.x;
        this.y = ZERO.y;
        this.width = ZERO.width;
        this.height = ZERO.height;
    }

    public Box(Box box) {
        this.x = box.x;
        this.y = box.y;
        this.width = box.width;
        this.height = box.height;
    }

    public Box(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public final void scale(float factor) {
        x *= factor;
        y *= factor;
        width *= factor;
        height *= factor;
    }

    public final Box add(float x, float y, float width, float height) {
        return new Box(this.x + x, this.y + y, this.width + width, this.height + height);
    }

    public final boolean collides(Box otherBox) {
        if (new Rectangle(x, y, width, height).overlaps(new Rectangle(otherBox.x, otherBox.y, otherBox.width, otherBox.height))) {
            return true;
        }

        return false;
    }

    public final boolean isPointWithin(float x, float y) {
        if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height) {
            return true;
        }

        return false;
    }

    public final boolean isPointWithin(Vector2 position) {
        return isPointWithin(position.x, position.y);
    }

    public final Box translate(float x, float y) {
        return new Box(this.x + x, this.y + y, width, height);
    }

    public final Box translate(Vector2 position) {
        return new Box(x + position.x, y + position.y, width, height);
    }

    public final Vector2 getBottomLeft() {
        return new Vector2(x, y);
    }

    public final Vector2 getBottomRight() {
        return new Vector2(x + width, y);
    }

    public final Vector2 getTopLeft() {
        return new Vector2(x, y + height);
    }

    public final Vector2 getTopRight() {
        return new Vector2(x + width, y + height);
    }

    public final Vector2 getCenter() {
        return new Vector2(x + (width / 2), y + (height / 2));
    }
}
