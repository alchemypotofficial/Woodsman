package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.common.menus.elements.ElementSlot;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.handlers.LangHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MenuHotbar extends Menu {

    protected EntityPlayer player;

    protected int lastSelectedSlot;

    protected float hoverTimer;

    protected ArrayList<ElementSlot> menuSlots = new ArrayList<>();

    public MenuHotbar(String id) {
        super(id);
    }

    public void tick() {

    }

    public void render(Renderer renderer, Vector2 mousePosition) {
        TextureAsset hotbarBackground = Renderer.getTexture("sprites/menus/Menu_Inventory_Background.png");
        TextureAsset hotbarSelectedSlot = Renderer.getTexture("sprites/menus/Menu_Selected_Slot.png");
        int textureWidth = hotbarBackground.rawTexture.getWidth();

        renderer.addMenuViewable(new Viewable(hotbarBackground, new Vector2(-textureWidth, -240), 20, 2f));

        if (player != null) {
            Container inventory = player.getInventory();
            if (inventory != null) {
                menuSlots.clear();
                for (int index = 0; index < 8; index++) {
                    ElementSlot slot = new ElementSlot(new Box((-textureWidth + 14) + (index * 34), -228, 32, 32), inventory, index, false);

                    menuSlots.add(slot);
                    slot.render(renderer, 21, mousePosition);
                }
            }

            renderer.addMenuViewable(new Viewable(hotbarSelectedSlot, new Vector2((-textureWidth + 14) + (player.getSelectedSlot() * 34), -228), 30, 2f));

            Item item = inventory.getItem(player.getSelectedSlot());
            if (item != null) {
                if (hoverTimer < 3.5f) {
                    String itemLocalName = LangHandler.getLocalName(item.getStaticName());

                    renderer.addMenuText(new Text(itemLocalName, new Vector2(-(renderer.getStringWidth(itemLocalName) / 2), -155), 20));
                    hoverTimer += Gdx.graphics.getDeltaTime();
                }

                if (lastSelectedSlot != player.getSelectedSlot()) {
                    hoverTimer = 0f;
                }
            }
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
