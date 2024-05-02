package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.graphics.data.TextureAsset;

public class Gesture {

    private String name;
    private TextureAsset texture;

    public Gesture (String name, TextureAsset texture) {
        this.name = name;
        this.texture = texture;
    }

    public final String getName() {
        return this.name;
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }
}
