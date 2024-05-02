package com.alchemy.woodsman.core.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Renderable implements Comparable<Renderable> {

    protected Vector2 position;
    protected int layer;
    protected Color color;
    protected float scale;
    protected float offset;

    public Renderable(Vector2 position, int layer) {
        this.position = position;
        this.layer = layer;

        scale = 1f;
        color = Renderer.WHITE;
        offset = 0;
    }

    public Renderable(Vector2 position, int layer, float scale) {
        this.position = position;
        this.layer = layer;
        this.scale = scale;

        color = Renderer.WHITE;
        offset = 0;
    }

    public Renderable(Vector2 position, int layer, Color color) {
        this.position = position;
        this.layer = layer;

        if (color != null) {
            this.color = color;
        }
        else {
            this.color = Renderer.WHITE;
        }

        scale = 1f;
        offset = 0;
    }

    public Renderable(Vector2 position, int layer, float scale, Color color) {
        this.position = position;
        this.layer = layer;
        this.scale = scale;

        if (color != null) {
            this.color = color;
        }
        else {
            this.color = Renderer.WHITE;
        }
    }

    @Override
    public int compareTo(Renderable otherRenderable) {
        if (layer < otherRenderable.layer || (layer == otherRenderable.layer && (position.y + offset) > (otherRenderable.position.y + otherRenderable.getOffset()))) {
            return -1;
        }
        else if (layer == otherRenderable.layer && (position.y + offset) == (otherRenderable.position.y + otherRenderable.getOffset())) {
            return 0;
        }

        return 1;
    }

    public final void setPosition(Vector2 position) {
        this.position = position;
    }

    public final void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public final void setLayer(int layer) {
        this.layer = layer;
    }

    public final void setColor(Color color) {
        this.color = color;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public final void setOffset(int offset) {
        this.offset = offset;
    }

    public final Vector2 getPosition() {
        return this.position;
    }

    public final int getLayer() {
        return this.layer;
    }

    public final Color getColor() {
        return this.color;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getOffset() {
        return this.offset;
    }
}
