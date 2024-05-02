package com.alchemy.woodsman.core.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Text extends Renderable {
    private String string;
    private Color behindColor;

    private boolean isWrapped;
    private int width;

    public Text(String string, Vector2 position, int layer) {
        super(position, layer);

        this.string = string;
        this.isWrapped = false;
        this.width = 100;
    }

    public Text(String string, Vector2 position, int layer, float scale, Color color) {
        super(position, layer, scale, color);

        this.string = string;
        this.isWrapped = false;
        this.width = 100;
    }

    public Text(String string, Vector2 position, int layer, float scale, Color color, Color behindColor) {
        super(position, layer, scale, color);

        this.string = string;
        this.isWrapped = false;
        this.width = 100;
        this.behindColor = behindColor;
    }

    public Text(String string, Vector2 position, int layer, float scale, Color color, boolean isWrapped, int width) {
        super(position, layer, scale, color);

        this.string = string;
        this.isWrapped = isWrapped;
        this.width = width;
    }

    public final void setString(String string) {
        this.string = string;
    }

    public final String getString() {
        return this.string;
    }

    public final Color getBehindColor() {
        return this.behindColor;
    }

    public final boolean getIsWrapped() {
        return this.isWrapped;
    }

    public final int getWidth() {
        return this.width;
    }
}
