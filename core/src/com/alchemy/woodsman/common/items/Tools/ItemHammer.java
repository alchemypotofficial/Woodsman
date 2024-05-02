package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ItemHammer extends ItemTool {

    public ItemHammer(String id, String staticName, String texturePath, int maxAmount, String tier) {
        super(id, staticName, texturePath, maxAmount, tier);

        useTime = 0.75f;
    }

    @Override
    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {
        BlockPosition blockPosition = new BlockPosition(mouseWorldPosition);

        Random random = new Random(Game.getSystemTime());
        int soundIndex = random.nextInt(2);

        Block block = world.getBlock(blockPosition);
        if (block != null) {
            if (!block.hasCeiling() && world.hasCeiling(blockPosition)) {
                player.useItem(itemStack.getItem());

                world.setCeiling(false, blockPosition);

                SoundAsset hammerSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
                SoundHandler.playSound(hammerSound, Game.getSettings().effectsVolume, false);
            }
        }
        else if (world.hasCeiling(blockPosition)) {
            player.useItem(itemStack.getItem());

            world.setCeiling(false, blockPosition);

            SoundAsset hammerSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
            SoundHandler.playSound(hammerSound, Game.getSettings().effectsVolume, false);
        }
    }

    @Override
    public void onSecondary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {
        BlockPosition blockPosition = new BlockPosition(mouseWorldPosition);

        Block block = world.getBlock(blockPosition);
        if (block != null) {
            if (!block.hasCeiling() && !world.hasCeiling(blockPosition)) {
                player.useItem(itemStack.getItem());

                world.setCeiling(true, blockPosition);

                SoundAsset hammerSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
                SoundHandler.playSound(hammerSound, Game.getSettings().effectsVolume, false);
            }
        }
        else if (!world.hasCeiling(blockPosition)) {
            player.useItem(itemStack.getItem());

            world.setCeiling(true, blockPosition);

            SoundAsset hammerSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
            SoundHandler.playSound(hammerSound, Game.getSettings().effectsVolume, false);
        }
    }
}
