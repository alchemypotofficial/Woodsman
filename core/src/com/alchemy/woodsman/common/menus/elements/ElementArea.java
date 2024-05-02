package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class ElementArea extends Element {

    public ElementArea(Box collider) {
        super(collider);
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        if (settings.isHitboxShown) {
            if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
                renderer.addMenuWireframe(new Wireframe(new Box(getCollider().x, getCollider().y, getCollider().width, getCollider().height), Wireframe.GREEN));
            }
            else {
                renderer.addMenuWireframe(new Wireframe(new Box(getCollider().x, getCollider().y, getCollider().width, getCollider().height), Wireframe.YELLOW));
            }
        }
    }
}
