package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;

public class BlockTallGrass extends Block {

    public BlockTallGrass(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        setOccludable(true);
        setOccluder(new Box(0f, 0.5f, 1, 1));
    }
}
