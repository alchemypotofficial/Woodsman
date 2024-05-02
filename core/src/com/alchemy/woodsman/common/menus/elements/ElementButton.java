package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ElementButton extends Element {
    private TextureAsset texture;
    private String name;

    public ElementButton(Box collider, TextureAsset texture) {
        super(collider);

        this.texture = texture;
    }

    public ElementButton(Box collider, TextureAsset texture, String name) {
        super(collider);

        this.texture = texture;
        this.name = name;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Box collider = getCollider();

        if (texture != null) {
            if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
                //* Draw button hovered.
                ElementResizeable resizeable = new ElementResizeable(collider.add(0, 6, 0, 0), texture);
                resizeable.render(renderer, layer, mousePosition);

                //* Draw button name.
                if (name != null) {
                    int exitTextOffset = MathUtils.round(renderer.getStringWidth(name) / 2);
                    Text exitText = new Text(name, new Vector2(getCollider().getCenter().x - exitTextOffset, getCollider().getCenter().y + 12), 101);
                    renderer.addMenuText(exitText);
                }
            }
            else {
                //* Draw button not hovered.
                ElementResizeable resizeable = new ElementResizeable(collider, texture);
                resizeable.render(renderer, layer, mousePosition);

                //* Draw button name.
                if (name != null) {
                    int exitTextOffset = MathUtils.round(renderer.getStringWidth(name) / 2);
                    Text exitText = new Text(name, new Vector2(getCollider().getCenter().x - exitTextOffset, getCollider().getCenter().y + 6), layer + 1);
                    renderer.addMenuText(exitText);
                }
            }
        }

        if (Game.getSettings().isHitboxShown) {
            if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
                renderer.addMenuWireframe(new Wireframe(collider, Wireframe.GREEN));
            }
            else {
                renderer.addMenuWireframe(new Wireframe(collider, Wireframe.YELLOW));
            }
        }
    }

    public final void setTexture(TextureAsset texture) {
        this.texture = texture;
    }
}
