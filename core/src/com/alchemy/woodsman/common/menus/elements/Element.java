package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class Element {

    private Box collider;

    public Element(Box collider) {
        this.collider = collider;
    }

    public void tick() {

    }

    public void render(Renderer renderer, int layer, Vector2 mousePosition) {

    }

    public final boolean isHovered(Vector2 position) {
        if (collider.isPointWithin(position)) {
            return true;
        }

        return false;
    }

    public final Box getCollider() {
        return this.collider;
    }
}
