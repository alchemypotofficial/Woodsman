package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import javax.swing.text.View;

public class Viewable extends Renderable {

    private TextureAsset texture;
    private Box region;

    public Viewable(TextureAsset texture, Vector2 position, int layer) {
        super(position, layer);

        this.texture = texture;

        region = new Box(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Viewable(TextureAsset texture, Vector2 position, int layer, float scale) {
        super(position, layer, scale);

        this.texture = texture;

        region = new Box(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Viewable(TextureAsset texture, Vector2 position, int layer, Color color) {
        super(position, layer, color);

        this.texture = texture;

        region = new Box(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Viewable(TextureAsset texture, Vector2 position, int layer, float scale, Color color) {
        super(position, layer, scale, color);

        this.texture = texture;

        region = new Box(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Viewable(TextureAsset texture, Box region, Vector2 position, int layer, Color color) {
        super(position, layer, color);

        this.texture = texture;
        this.region = region;
    }

    public Viewable(TextureAsset texture, Box region, Vector2 position, int layer, float scale, Color color) {
        super(position, layer, scale, color);

        this.texture = texture;
        this.region = region;
    }

    public Viewable(Frame frame, Vector2 position, int layer, float scale, Color color) {
        super(position, layer, scale, color);

        this.texture = frame.getTexture();
        this.region = frame.getRegion();
    }

    public Viewable(Frame frame, Vector2 position, int layer, float scale, Color color, float offset) {
        super(position, layer, scale, color);

        this.texture = frame.getTexture();
        this.region = frame.getRegion();
        this.offset = offset;
    }

    public final void setTexture(TextureAsset texture) {
        this.texture = texture;
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }

    public final Box getRegion() {
        return this.region;
    }


}
