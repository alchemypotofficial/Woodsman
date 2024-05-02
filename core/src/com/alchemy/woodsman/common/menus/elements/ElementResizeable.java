package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ElementResizeable extends Element {
    private TextureAsset texture;

    public ElementResizeable(Box collider, TextureAsset textureAsset) {
        super(collider);

        this.texture = textureAsset;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Box collider = getCollider();

        float transformWidth = collider.width / 4;
        float transformHeight = collider.height / 4;

        float offsetWidth = collider.width / 2;
        float offsetHeight = collider.height / 2;

        //* Bottom of resizeable.
        renderer.addMenuViewable(new Viewable(texture, new Box(0, texture.getHeight() - transformHeight, transformWidth, transformHeight), new Vector2(collider.x, collider.y), layer, 2f, Color.WHITE));
        renderer.addMenuViewable(new Viewable(texture, new Box(texture.getWidth() - transformWidth, texture.getHeight() - transformHeight, transformWidth, transformHeight), new Vector2(collider.x + offsetWidth, collider.y), layer, 2f, Color.WHITE));

        //* Top of resizeable.
        renderer.addMenuViewable(new Viewable(texture, new Box(0, 0, transformWidth, transformHeight), new Vector2(collider.x, collider.y + offsetHeight), layer, 2f, Color.WHITE));
        renderer.addMenuViewable(new Viewable(texture, new Box(texture.getWidth() - transformWidth, 0, transformWidth, transformHeight), new Vector2(collider.x + offsetWidth, collider.y + offsetHeight), layer, 2f, Color.WHITE));
    }
}
