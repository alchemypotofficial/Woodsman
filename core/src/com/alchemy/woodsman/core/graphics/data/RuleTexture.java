package com.alchemy.woodsman.core.graphics.data;

import com.alchemy.woodsman.core.graphics.Frame;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RuleTexture {

    private TextureAsset texture;
    private int width;
    private int height;

    public RuleTexture(TextureAsset texture, int width, int height) {
        this.texture = texture;
        this.height = height;
        this.width = width;
    }

    public final Frame getFrame(DirectionMap directionMap, int offset) {
        int textureOffset = offset * 3;

        boolean top = directionMap.getTop();
        boolean bottom = directionMap.getBottom();
        boolean left = directionMap.getLeft();
        boolean right = directionMap.getRight();

        if (texture != null) {
            Frame frame;

            if (top == true && bottom == true && left == true && right == true) {
                frame = new Frame(texture, new Box(width, height, width, height));
            }
            else if (top == false && bottom == true && left == true && right == true) {
                frame = new Frame(texture, new Box(width, 0, width, height));
            }
            else if (top == true && bottom == false && left == true && right == true) {
                frame = new Frame(texture, new Box(width, 2 * height, width, height));
            }
            else if (top == true && bottom == true && left == false && right == true) {
                frame = new Frame(texture, new Box(0, height, width, height));
            }
            else if (top == true && bottom == true && left == true && right == false) {
                frame = new Frame(texture, new Box(2 * width, height, width, height));
            }
            else if (top == false && bottom == true && left == true && right == false) {
                frame = new Frame(texture, new Box(2 * width, 0, width, height));
            }
            else if (top == false && bottom == true && left == false && right == true) {
                frame = new Frame(texture, new Box(0, 0, width, height));
            }
            else if (top == true && bottom == false && left == false && right == true) {
                frame = new Frame(texture, new Box(0, 2 * width, width, height));
            }
            else if (top == true && bottom == false && left == true && right == false) {
                frame = new Frame(texture, new Box(2 * width, 2 * width, width, height));
            }
            else {
                frame = new Frame(texture, new Box(width, height, width, height));
            }

            return frame;
        }

        return null;
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }
}
