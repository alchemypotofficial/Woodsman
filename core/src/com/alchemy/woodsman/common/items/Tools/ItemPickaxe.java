package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.EntityLiving;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class ItemPickaxe extends ItemTool {

    public ItemPickaxe(String id, String staticName, String texturePath, int maxAmount, String tier) {
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

        Random random = new Random(Game.getSystemTime());
        int soundIndex = random.nextInt(2);

        Block block = world.getBlock(blockPosition);
        if (block != null && block.hasBlockTag(Tags.DESTROYABLE_PICKAXE)) {
            world.damageBlock(blockPosition, getTier().getDamage());

            if (soundIndex == 1) {
                SoundAsset pickaxeSound = SoundHandler.getSound("sounds/items/Axe_Chop_2.wav");
                SoundHandler.playSound(pickaxeSound, Game.getSettings().effectsVolume, false);
            }
            else {
                SoundAsset pickaxeSound = SoundHandler.getSound("sounds/items/Axe_Chop_1.wav");
                SoundHandler.playSound(pickaxeSound, Game.getSettings().effectsVolume, false);
            }
        }
        else {
            if (soundIndex == 1) {
                SoundAsset pickaxeSound = SoundHandler.getSound("sounds/items/Tool_Thud_1.wav");
                SoundHandler.playSound(pickaxeSound, Game.getSettings().effectsVolume, false);
            }
            else {
                SoundAsset pickaxeSound = SoundHandler.getSound("sounds/items/Tool_Thud_2.wav");
                SoundHandler.playSound(pickaxeSound, Game.getSettings().effectsVolume, false);
            }
        }
    }

    @Override
    public TextureAsset getUseAnimation() {
        return Renderer.getTexture("sprites/entities/player/Player_Swing_Tool.png");
    }
}