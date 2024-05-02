package com.alchemy.woodsman.core.screens;

import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.badlogic.gdx.math.Vector2;

public abstract class Screen {

    protected Renderer renderer;

    public Screen(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void start();

    public abstract void tick();

    public abstract void physics();

    public abstract void render(Renderer renderer);

    public void onMouseLeftDown(Vector2 position) {

    }

    public void onMouseRightDown(Vector2 position) {

    }

    public void onButtonDown(InputHandler.Button button) {

    }

    public void onButtonUp(InputHandler.Button button) {

    }

    public void onType(char character) {

    }

    public void onScroll(int direction) {

    }
}
