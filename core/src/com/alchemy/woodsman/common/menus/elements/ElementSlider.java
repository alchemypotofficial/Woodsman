package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ElementSlider extends Element {
    private float value;
    private int length;
    private boolean vertical;
    private boolean dragging;

    private ElementResizeable slideButton;

    public ElementSlider(Box collider, float value, int length, boolean vertical) {
        super(collider);

        this.value = value;
        this.length = length;
        this.vertical = vertical;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        if (slideButton != null && dragging) {
            float mouseOffsetX = mousePosition.x - getCollider().x;

            float sliderPosition = MathUtils.clamp(getCollider().x + mouseOffsetX, getCollider().x, getCollider().x + length);
            value = MathUtils.clamp(sliderPosition / (getCollider().x + length), 0f, 1f);

            Debug.logAlert("Dragging slider.");
        }

        Box buttonCollider = new Box(getCollider().x + (length * value) - 16, getCollider().y, 32, 32);
        slideButton = new ElementResizeable(buttonCollider, Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"));
        slideButton.render(renderer, 50, mousePosition);

        if (Game.getSettings().isHitboxShown) {
            if (isButtonHovered(mousePosition)) {
                renderer.addMenuWireframe(new Wireframe(slideButton.getCollider(), Wireframe.GREEN));
            }
            else {
                renderer.addMenuWireframe(new Wireframe(slideButton.getCollider(), Wireframe.YELLOW));
            }

            if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
                renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.GREEN));
            }
            else {
                renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.YELLOW));
            }
        }
    }

    public final void startDrag() {
        dragging = true;
    }

    public final void stopDrag() {
        dragging = false;
    }

    public final boolean isDragging() {
        return this.dragging;
    }

    public final boolean isButtonHovered(Vector2 position) {
        if (slideButton != null && slideButton.isHovered(position)) {
            return true;
        }

        return false;
    }

    public final float getValue() {
        return this.value;
    }
}
