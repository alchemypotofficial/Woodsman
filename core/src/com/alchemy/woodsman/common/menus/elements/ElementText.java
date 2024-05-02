package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class ElementText extends Element {

    private Text text;

    public ElementText(Box collider, Text text) {
        super(collider);

        this.text = text;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        if (text != null) {
            renderer.addMenuText(text);
        }

        if (settings.isHitboxShown) {
            if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
                renderer.addMenuWireframe(new Wireframe(new Box(getCollider().x, getCollider().y, getCollider().width, getCollider().height), Wireframe.GREEN));
            }
            else {
                renderer.addMenuWireframe(new Wireframe(new Box(getCollider().x, getCollider().y, getCollider().width, getCollider().height), Wireframe.YELLOW));
            }
        }
    }

    public final void setText(Text text) {
        this.text = text;
    }
}
