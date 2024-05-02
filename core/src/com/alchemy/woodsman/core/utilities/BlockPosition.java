package com.alchemy.woodsman.core.utilities;

import com.badlogic.gdx.math.Vector2;

public class BlockPosition {

    public int x;
    public int y;

    public BlockPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BlockPosition(float x, float y) {
        this.x = (int)Math.floor(x);
        this.y = (int)Math.floor(y);
    }

    public BlockPosition(Vector2 worldPosition) {
        this.x = (int)Math.floor(worldPosition.x);
        this.y = (int)Math.floor(worldPosition.y);
    }

    public final Vector2 toWorldPosition() {
        return new Vector2(x, y);
    }
}
