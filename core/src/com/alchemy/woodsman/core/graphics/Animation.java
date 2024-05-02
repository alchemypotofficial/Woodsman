package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation extends Registerable {

    private TextureAsset texture;
    private float durations[];

    private int width;
    private int height;
    private int offset;

    public Animation(String id, TextureAsset texture, int width, int height, float durations[]) {
        super(id);

        this.texture = texture;
        this.width = width;
        this.height = height;
        this.durations = durations;

        offset = 0;
    }

    public Animation(String id, TextureAsset texture, int width, int height, int offset, float durations[]) {
        super(id);

        this.texture = texture;
        this.width = width;
        this.height = height;
        this.offset = offset;
        this.durations = durations;
    }

    public final Frame getFrame(int frame) {
        return new Frame(texture, new Box(frame * width, offset * height, width, height));
    }

    public float getDuration(int frame) {
        if (frame < durations.length) {
            return durations[frame];
        }

        return 0f;
    }

    public final int getTotalFrames() {
        int frames = texture.getWidth() / width;

        return frames;
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }
}
