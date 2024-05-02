package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.EntityLiving;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

public class ItemShovel extends ItemTool {

    public ItemShovel(String id, String staticName, String texturePath, int maxAmount, String tier) {
        super(id, staticName, texturePath, maxAmount, tier);
    }

    @Override
    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {
        BlockPosition blockPosition = new BlockPosition(player.getPosition());
        if (player.getDirection() == EntityLiving.Direction.UP) {
            blockPosition.y += 1;
        }
        else if (player.getDirection() == EntityLiving.Direction.DOWN) {
            blockPosition.y -= 1;
        }
        else if (player.getDirection() == EntityLiving.Direction.LEFT) {
            blockPosition.x -= 1;
        }
        else if (player.getDirection() == EntityLiving.Direction.RIGHT) {
            blockPosition.x += 1;
        }

        player.useItem(itemStack.getItem());

        Block block = world.getBlock(blockPosition);
        if (block != null && block.hasBlockTag(Tags.DESTROYABLE_AXE)) {
            world.damageBlock(blockPosition, getTier().getDamage());
        }
    }
}
