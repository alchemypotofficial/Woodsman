package com.alchemy.woodsman.common.floors;

import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Floor extends Registerable {

    protected TextureAsset mainTexture;

    private ArrayList<BlockTag> blockTags;

    public Floor(String id, String texturePath) {
        super(id);

        mainTexture = Renderer.getTexture(texturePath);
    }

    public void render(Renderer renderer, World world, Vector2 displayPosition, BlockPosition blockPosition, Color color) {
        if (mainTexture != null) {
            renderer.addWorldViewable(new Viewable(mainTexture, displayPosition, 0, color));
        }
    }

    public final void setMainTexture(TextureAsset texture) {
        this.mainTexture = texture;
    }

    public final TextureAsset getMainTexture() {
        return this.mainTexture;
    }

    public final boolean hasBlockTag(BlockTag blockTag) {
        for (BlockTag tag : blockTags) {
            if (tag == blockTag) {
                return true;
            }
        }

        return false;
    }
}