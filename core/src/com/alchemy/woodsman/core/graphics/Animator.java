package com.alchemy.woodsman.core.graphics;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Animator {

    private Animation animation;
    private int frame;
    private float frameTime;
    private boolean isLooping;

    public Animator() {
        animation = null;
        frame = 0;
        frameTime = 0;
        isLooping = false;
    }

    public final Frame animate() {
        if (animation != null) {
            frameTime += Game.getDeltaTime();

            if (frameTime >= animation.getDuration(frame)) {
                frame++;
                frameTime = 0;
            }

            if (frame >= animation.getTotalFrames()) {
                if (isLooping) {
                    frame = 0;
                }
                else {
                    animation = null;
                    frame = 0;

                    return null;
                }
            }

             return animation.getFrame(frame);
        }

        return null;
    }

    public final void setAnimation(Animation animation, boolean isLooping) {
        if (animation != null) {
            if (this.animation == null || this.animation.getID() != animation.getID()) {
                this.animation = animation;
                this.isLooping = isLooping;

                frame = 0;
                frameTime = 0;
            }
        }
    }

    public final Animation getAnimation() {
        return this.animation;
    }

    public final Frame getFrame() {
        if (animation != null) {
            return animation.getFrame(frame);
        }

        return null;
    }
}
