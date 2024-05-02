package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.screens.Screen;

public class ScreenHandler {

    private Screen screen;

    public ScreenHandler() {
        screen = null;
    }

    public final void tick() {
        if (screen != null) {
            screen.tick();
        }
    }

    public final void render(Renderer renderer) {
        if (screen != null) {
            screen.render(renderer);
        }
    }

    public final void setScreen(Screen screen) {
        this.screen = screen;

        screen.start();
    }

    public final Screen getScreen() {
        return this.screen;
    }
}
