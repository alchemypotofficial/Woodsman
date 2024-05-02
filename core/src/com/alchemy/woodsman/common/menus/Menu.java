package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.badlogic.gdx.math.Vector2;

public abstract class Menu extends Registerable {

    private boolean isVisible;

    public Menu(String id) {
        super(id);
    }

    public abstract void tick();

    public abstract void render(Renderer renderer, Vector2 mousePosition);

    public void onShow() {

    }

    public void onHide() {

    }

    public void onMouseLeftDown(Vector2 mousePosition) {

    }

    public void onMouseLeftUp(Vector2 mousePosition) {

    }

    public void onMouseRightDown(Vector2 mousePosition) {

    }

    public void onMouseRightUp(Vector2 mousePosition) {

    }

    public void onButtonDown(InputHandler.Button button, Vector2 mousePosition) {

    }

    public void onButtonUp(InputHandler.Button button, Vector2 mousePosition) {

    }

    public void onScroll(int direction) {

    }

    public void onType(char character) {

    }

    public final void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public final boolean isVisible() {
        return this.isVisible;
    }
}
