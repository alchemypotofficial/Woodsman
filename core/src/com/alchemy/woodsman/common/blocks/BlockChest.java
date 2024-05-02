package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.common.blockstates.BlockState;
import com.alchemy.woodsman.common.blockstates.BlockStateContainer;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.graphics.Gesture;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class BlockChest extends Block {

    public BlockChest(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        addBlockTags(new BlockTag[]{ Tags.DESTROYABLE_AXE, Tags.DESTROYABLE_PICKAXE });
    }

    @Override
    public boolean onInteract(World world, EntityPlayer player, BlockPosition blockPosition) {
        BlockState blockState = world.getBlockState(blockPosition);
        if(blockState instanceof  BlockStateContainer blockStateContainer) {
            Menus.MENU_CHEST.setContainer(blockStateContainer.container);
            Menus.MENU_CHEST.setPlayer(player);

            player.openMenu();

            MenuHandler.focus(Menus.MENU_CHEST);
        }

        return false;
    }

    @Override
    public void onBlockBroken(World world, BlockPosition blockPosition) {
        Vector2 dropPosition = new Vector2(blockPosition.x + 0.5f, blockPosition.y + 0.5f);
        BlockState blockState = world.getBlockState(blockPosition);

        if (blockState instanceof BlockStateContainer chestBlockState) {
            Container container = chestBlockState.container;

            for (int slot = 0; slot < container.getSlots(); slot++) {
                ItemStack itemStack = container.getItemStack(slot);

                if (itemStack != null) {
                    Random random = new Random();
                    int randomX = random.nextInt(-1, 1);
                    int randomY = random.nextInt(-1, 1);

                    world.dropItem(itemStack, dropPosition, new Vector2(randomX * 40, randomY * 40));
                }
            }
        }
    }

    @Override
    public BlockState createBlockState() {
        return new BlockStateContainer(getMaxHealth(), new Container(9));
    }

    @Override
    public Gesture getGesture() {
        return new Gesture("Open Chest", Renderer.getTexture("assets/sprites/gestures/Gesture_Container.png"));
    }
}
