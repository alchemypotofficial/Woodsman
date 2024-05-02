package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.utilities.physics.Box;

public class BlockFlower extends Block {

    public BlockFlower(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);
    }

    @Override
    public boolean isValidFloor(Floor floor) {
        if (floor == Floors.FLOOR_GRASS) {
            return true;
        }

        return false;
    }
}
