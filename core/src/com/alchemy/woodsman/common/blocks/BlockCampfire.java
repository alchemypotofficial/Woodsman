package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blockstates.BlockState;
import com.alchemy.woodsman.common.blockstates.BlockStateCampfire;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class BlockCampfire extends Block {
    public BlockCampfire(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        addBlockTags(new BlockTag[]{ Tags.DESTROYABLE_AXE, Tags.DESTROYABLE_PICKAXE });
    }

    @Override
    public void tick(World world, BlockPosition blockPosition) {
        super.tick(world, blockPosition);

        //* Cook items inside campfire using recipes.
        BlockState blockState = world.getBlockState(blockPosition);
        if (blockState instanceof BlockStateCampfire campfireState) {
            Container campfireContainer = campfireState.container;
            campfireContainer.update();

            //* If fuel is greater than 0, reduce fuel tick.
            if (campfireState.fuel > 0) {
                campfireState.fuel -= 1;

                //* If fuel becomes 0, try to add more fuel.
                if (campfireState.fuel <= 0) {
                    Item fuelSlotItem = campfireContainer.getItem(3);

                    if (fuelSlotItem == Items.ITEM_STICK) {
                        campfireState.fuel = 600;
                        campfireState.initialFuel = 600;
                        campfireState.lit = true;

                        campfireContainer.getItemStack(3).decrease(1);
                    }
                    else if (fuelSlotItem == Items.ITEM_OAK_LOG) {
                        campfireState.fuel = 1800;
                        campfireState.initialFuel = 1800;
                        campfireState.lit = true;

                        campfireContainer.getItemStack(3).decrease(1);
                    }
                    else {
                        campfireState.fuel = 0;
                        campfireState.initialFuel = 0;
                        campfireState.lit = false;
                    }
                }
            }
        }
    }

    @Override
    public void render(Renderer renderer, World world, BlockPosition blockPosition, Color color) {
        Settings settings = Game.getSettings();

        BlockState blockState = world.getBlockState(blockPosition);
        if (blockState instanceof BlockStateCampfire campfireState) {
            if (campfireState.lit) {
                TextureAsset litTexture = Renderer.getTexture("sprites/blocks/Block_Campfire_Lit.png");
                Frame frame = Renderer.registerAnimation(new Animation("campfire_lit", litTexture, 16, 32, new float[]{ 0.125f, 0.125f, 0.125f }));

                if (frame != null) {
                    renderer.addWorldViewable(new Viewable(frame, blockPosition.toWorldPosition(), 15, 1f, color));
                }
            }
            else {
                TextureAsset texture = Renderer.getTexture("sprites/blocks/Block_Campfire.png");
                renderer.addWorldViewable(new Viewable(texture, blockPosition.toWorldPosition(), 15, color));
            }
        }
        else {
            TextureAsset mainTexture = getMainTexture();
            if (mainTexture != null) {
                renderer.addWorldViewable(new Viewable(mainTexture, blockPosition.toWorldPosition(), 15, color));
            }
        }

        if (settings.isHitboxShown && isCollidable()) {
            Box propCollider = new Box(getCollider().x + blockPosition.x, getCollider().y + blockPosition.y, getCollider().width, getCollider().height);

            renderer.addWorldWireframe(new Wireframe(propCollider, Wireframe.RED));
        }
    }

    @Override
    public boolean onInteract(World world, EntityPlayer player, BlockPosition blockPosition) {
        BlockState blockState = world.getBlockState(blockPosition);
        if(blockState instanceof  BlockStateCampfire campfireState) {
            Container campfireContainer = campfireState.container;
            ItemStack selectedItem = player.getSelectedItemStack();
            if (selectedItem != null && selectedItem.getItem() == Items.ITEM_FIRE_PLOW) {
                Item fuelSlotItem = campfireContainer.getItem(3);

                //* Light campfire with fuel.
                if (fuelSlotItem == Items.ITEM_STICK && !campfireState.lit) {
                    campfireState.fuel = 600;
                    campfireState.initialFuel = 600;
                    campfireState.lit = true;

                    campfireContainer.getItemStack(3).decrease(1);

                    SoundAsset lightSound = SoundHandler.getSound("sounds/items/Use_Fire_Plow.wav");
                    SoundHandler.playSound(lightSound);

                    player.useItem(selectedItem.getItem());
                }
                else if (fuelSlotItem == Items.ITEM_OAK_LOG && !campfireState.lit) {
                    campfireState.fuel = 1800;
                    campfireState.initialFuel = 1800;
                    campfireState.lit = true;

                    campfireContainer.getItemStack(3).decrease(1);

                    SoundAsset lightSound = SoundHandler.getSound("sounds/items/Use_Fire_Plow.wav");
                    SoundHandler.playSound(lightSound);

                    player.useItem(selectedItem.getItem());
                }
            }
            else if (selectedItem != null && (selectedItem.getItem() == Items.ITEM_STICK || selectedItem.getItem() == Items.ITEM_OAK_LOG)) {
                ItemStack fuelSlotItemStack = campfireContainer.getItemStack(3);

                if (fuelSlotItemStack == null) {
                    campfireContainer.setItemStack(new ItemStack(selectedItem.getItem(), 1), 3);
                    selectedItem.decrease(1);
                }
                else if (selectedItem.getItem() == fuelSlotItemStack.getItem() && fuelSlotItemStack.getAmount() < fuelSlotItemStack.getMaxAmount()) {
                    fuelSlotItemStack.increase(1);
                    selectedItem.decrease(1);
                }
                else {
                    Menus.MENU_CAMPFIRE.setCampfireContainer(campfireState.container);
                    Menus.MENU_CAMPFIRE.setCampfireState(campfireState);
                    Menus.MENU_CAMPFIRE.setPlayer(player);

                    player.openMenu();

                    MenuHandler.focus(Menus.MENU_CAMPFIRE);
                }
            }
            else {
                Menus.MENU_CAMPFIRE.setCampfireContainer(campfireState.container);
                Menus.MENU_CAMPFIRE.setCampfireState(campfireState);
                Menus.MENU_CAMPFIRE.setPlayer(player);

                player.openMenu();

                MenuHandler.focus(Menus.MENU_CAMPFIRE);
            }
        }

        return false;
    }

    @Override
    public void onBlockBroken(World world, BlockPosition blockPosition) {
        Vector2 dropPosition = new Vector2(blockPosition.x + 0.5f, blockPosition.y + 0.5f);
        BlockState blockState = world.getBlockState(blockPosition);

        if (blockState instanceof BlockStateCampfire campfireBlockState) {
            Container container = campfireBlockState.container;

            for (int slot = 0; slot < container.getSlots(); slot++) {
                ItemStack itemStack = container.getItemStack(slot);

                if (itemStack != null) {
                    world.dropItem(itemStack, dropPosition, new Vector2(0, 0));
                }
            }
        }
    }

    @Override
    public BlockState createBlockState() {
        return new BlockStateCampfire(getMaxHealth(), new Container(4));
    }

    @Override
    public int getLightLevel(World world, BlockPosition blockPosition) {
        BlockState blockState = world.getBlockState(blockPosition);
        if(blockState instanceof  BlockStateCampfire campfireState) {
            if (campfireState.lit) {
                return 12;
            }
        }

        return 0;
    }

    @Override
    public Gesture getGesture() {
        return new Gesture("Open Campfire", Renderer.getTexture("sprites/gestures/Gesture_Container.png"));
    }
}
