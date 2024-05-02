package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;

public class Frame {

    private TextureAsset texture;
    private Box region;

    public Frame(TextureAsset texture, Box region) {
        this.texture = texture;
        this.region = region;
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }

    public final Box getRegion() {
        return this.region;
    }

    public final int getOffset() {
        return (int)(region.x / region.width);
    }
}
