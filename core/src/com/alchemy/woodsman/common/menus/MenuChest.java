package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.menus.elements.ElementSlot;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class MenuChest extends MenuContainer {

    protected Container container;

    public MenuChest(String id) {
        super(id);
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        super.render(renderer, mousePosition);

        //* Render background.
        TextureAsset chestBackground = Renderer.getTexture("sprites/menus/Menu_Chest_Background.png");
        int textureWidth = chestBackground.rawTexture.getWidth();
        renderer.addMenuViewable(new Viewable(chestBackground, new Vector2(-textureWidth, -120), 20, 2f));

        //* Render inventory slots.
        Container inventory = player.getInventory();
        for (int index = 0; index < 8; index++) {
            addSlot(new ElementSlot(new Box((-textureWidth + 14) + (index * 34), -108, 32, 32), inventory, index, true ));
        }

        //* Render chest container slots.
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlot(new ElementSlot(new Box((-textureWidth + 98) + (x * 34), 34 - (y * 34), 32, 32), container, x + (y * 3), true));
            }
        }
    }

    @Override
    public void quickMoveItem(ElementSlot slot) {
        Container slotContainer = slot.getContainer();

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

    public final void setContainer(Container container) {
        this.container = container;
    }
}
