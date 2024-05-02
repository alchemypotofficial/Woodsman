package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.common.blockstates.BlockStateCampfire;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.menus.elements.ElementSlot;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MenuCampfire extends MenuContainer {

    protected Container campfireContainer;
    protected BlockStateCampfire campfireState;

    public MenuCampfire(String id) {
        super(id);
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        super.render(renderer, mousePosition);

        //* Render background.
        TextureAsset campfireBackground = Renderer.getTexture("sprites/menus/crafting/Menu_Campfire_Background.png");
        int textureWidth = campfireBackground.rawTexture.getWidth();
        renderer.addMenuViewable(new Viewable(campfireBackground, new Vector2(-textureWidth, -120), 20, 2f));

        //* Render inventory slots.
        Container inventory = player.getInventory();
        for (int index = 0; index < 8; index++) {
            addSlot(new ElementSlot(new Box((-textureWidth + 14) + (index * 34), -108, 32, 32), inventory, index, true ));
        }

        //* Render campfire cooking slots.
        for (int index = 0; index < 3; index++) {
            addSlot(new ElementSlot(new Box((-textureWidth + 120) + (index * 34), -18, 32, 32), campfireContainer, index, true));
        }

        //* Render fuel slot.
        addSlot(new ElementSlot(new Box((-textureWidth + 94) - 16, -34, 32, 32), campfireContainer, 3, true));

        if (campfireState != null) {
            if (campfireState.lit) {
                float fuelPercentage = (float)campfireState.fuel / (float)campfireState.initialFuel;

                TextureAsset campfireLit = Renderer.getTexture("sprites/menus/crafting/Menu_Campfire_Lit.png");
                renderer.addMenuViewable(new Viewable(campfireLit, new Box(0, 16 - MathUtils.round(16 * fuelPercentage), 16, MathUtils.round(16 * fuelPercentage)), new Vector2(-textureWidth + 78, 0), 21, 2f, Color.WHITE));
            }
        }
    }

    @Override
    public void quickMoveItem(ElementSlot slot) {
        Container slotContainer = slot.getContainer();
        Container container = campfireState.container;

        if (slot.getItemStack() != null) {
            if (slotContainer == player.getInventory()) {
                ItemStack leftoverStack = container.addItemStack(slot.getItemStack());
                player.getInventory().setItemStack(leftoverStack, slot.getSlotNumber());
            }
            else {
                ItemStack leftoverStack = player.getInventory().addItemStack(slot.getItemStack());
                slotContainer.setItemStack(leftoverStack, slot.getSlotNumber());
            }
        }
    }

    public final void setCampfireContainer(Container container) {
        campfireContainer = container;
    }

    public final void setCampfireState(BlockStateCampfire campfireState) {
        this.campfireState = campfireState;
    }
}
