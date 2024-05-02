package com.alchemy.woodsman.common.items;

import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.core.init.Blocks;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.world.World;

import java.util.ArrayList;

public class ItemSapling extends ItemBlock {

    public ItemSapling(String id, String staticName, String texturePath, int maxAmount, String blockId) {
        super(id, staticName, texturePath, maxAmount, blockId);
    }

    @Override
    public boolean canPlace(World world, BlockPosition blockPosition) {
        for (int y = -2; y < 3; y++) {
            for (int x = -2; x < 3; x++) {
                BlockPosition otherBlockPosition = new BlockPosition(x + blockPosition.x, y + blockPosition.y);

                Block block = world.getBlock(otherBlockPosition);
                if (block != null && !block.canReplace()) {
                    return false;
                }
            }
        }

        Floor floor = world.getFloor(blockPosition);
        if (floor != Floors.FLOOR_GRASS) {
            return false;
        }

        return super.canPlace(world, blockPosition);
    }
}
