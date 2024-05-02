package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;

public class BlockStone extends Block {

    public BlockStone(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        addBlockTags(new BlockTag[]{ Tags.STONE });
    }
}
