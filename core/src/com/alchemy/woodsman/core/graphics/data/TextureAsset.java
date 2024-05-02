package com.alchemy.woodsman.core.graphics.data;

import com.alchemy.woodsman.core.graphics.Frame;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

public class TextureAsset extends Registerable {
    public Texture rawTexture;

    public TextureAsset(String id, Texture rawTexture) {
        super(id);

        this.rawTexture = rawTexture;
    }

    public final int getWidth() {
        return rawTexture.getWidth();
    }

    public final int getHeight() {
        return rawTexture.getHeight();
    }

    public final boolean isEmpty() {
        if (rawTexture == null) {
            return true;
        }
        else if (getID() == "assets/sprites/system/System_Null.png") {
            return true;
        }

        return false;
    }
}
