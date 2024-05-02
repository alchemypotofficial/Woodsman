package com.alchemy.woodsman.common.items;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Blocks;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ItemBlock extends Item {

    private String blockId;

    public ItemBlock(String id, String staticName, String texturePath, int maxAmount, String blockId) {
        super(id, staticName, texturePath, maxAmount);

        this.blockId = blockId;
    }

    @Override
    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {
        BlockPosition blockPosition = new BlockPosition(mouseWorldPosition.x, mouseWorldPosition.y);
        Block block = Blocks.BLOCKS.getEntry(blockId);

        if (block != null) {
            double playerDistanceFrom = player.getDistanceFrom(new Vector2(blockPosition.x, blockPosition.y));
            if (playerDistanceFrom <= player.getReach() && canPlace(world, blockPosition)) {
                world.setBlock(block, blockPosition);
                block.onBlockPlaced(world, blockPosition);

                SoundAsset axeSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
                SoundHandler.playSound(axeSound, Game.getSettings().effectsVolume, false);

                itemStack.decrease(1);
            }
        }
    }

    @Override
    public void renderHovered(Renderer renderer, World world, EntityPlayer player, Vector2 mousePosition) {
        BlockPosition blockPosition = renderer.toBlockPosition(mousePosition);

        if (player != null) {
            ItemStack itemStack = player.getSelectedItemStack();
            double playerDistanceFrom = player.getDistanceFrom(new Vector2(blockPosition.x, blockPosition.y));

            //* Render block when item is hovered at blockPosition.
            if (itemStack != null) {
                Block block = Blocks.BLOCKS.getEntry(blockId);
                if (block != null) {

                    if (playerDistanceFrom <= player.getReach() && canPlace(world, blockPosition)) {
                        block.render(renderer, world, blockPosition, new Color(Renderer.GREEN.r, Renderer.GREEN.g, Renderer.GREEN.b, 0.5f));
                    }
                    else {
                        block.render(renderer, world, blockPosition, new Color(Renderer.RED.r, Renderer.RED.g, Renderer.RED.b, 0.5f));
                    }
                }
            }
        }
    }

    public boolean canPlace(World world, BlockPosition blockPosition) {
        Block block = Blocks.BLOCKS.getEntry(blockId);
        Block replacedBlock = world.getBlock(blockPosition);

        //* Check if block is placeable at position.
        if (block != null) {
            //* Check if any entity fills the position.
            ArrayList<Entity> entities = world.getEntities();
            for (Entity entity : entities) {
                if (entity.getCollider().collides(block.getCollider().translate(blockPosition.x, blockPosition.y))) {
                    return false;
                }
            }

            if (replacedBlock != null && !replacedBlock.canReplace()) {
                return false;
            }

            return true;
        }

        return false;
    }
}
