package com.alchemy.woodsman.common.items;

import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.init.Blocks;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ItemFloor extends Item {

    private String floorID;

    public ItemFloor(String id, String staticName, String texturePath, int maxAmount, String floorID) {
        super(id, staticName, texturePath, maxAmount);

        this.floorID = floorID;
    }

    @Override
    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {
        BlockPosition floorPosition = new BlockPosition(mouseWorldPosition.x, mouseWorldPosition.y);
        Floor floor = Floors.FLOORS.getEntry(floorID);

        if (floor != null) {
            double playerDistanceFrom = player.getDistanceFrom(new Vector2(floorPosition.x, floorPosition.y));
            if (playerDistanceFrom <= player.getReach()) {
                world.setFloor(floor, floorPosition);

                itemStack.decrease(1);
            }
        }
    }
}
