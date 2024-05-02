package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Wireframe {
    public static final Color RED = new Color(255, 0, 0, 1);
    public static final Color GREEN = new Color(0, 255,0, 1);
    public static final Color BLUE = new Color(0, 0,255, 1);
    public static final Color YELLOW = new Color(255, 255,0, 1);
    public static final Color WHITE = new Color(255, 255,255, 1);
    public static final Color BLACK = new Color(0, 0,0, 1);

    private Box box;
    private Color color;

    public Wireframe(Box box, Color color) {
        this.box = box;
        this.color = color;
    }

    public final void setBox(Box box) {
        this.box = box;
    }

    public Vector2 getPosition() {
        return new Vector2(box.x, box.y);
    }

    public Box getBox() {
        return this.box;
    }

    public final Color getColor() {
        return this.color;
    }
}
