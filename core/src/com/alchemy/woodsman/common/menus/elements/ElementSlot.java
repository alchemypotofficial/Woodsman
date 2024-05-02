package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ElementSlot extends Element {

    private Container container;
    private int slotNumber;

    private boolean isSelecting;

    public ElementSlot(Box collider, Container container, int slotNumber, boolean isSelecting) {
        super(collider);

        this.container = container;
        this.slotNumber = slotNumber;
        this.isSelecting = isSelecting;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        TextureAsset selectedSlot = Renderer.getTexture("sprites/menus/Menu_Selected_Slot.png");

        Box collider = getCollider();
        ItemStack itemStack = getItemStack();

        if (itemStack != null) {
            Item item = itemStack.getItem();
            if (item != null) {
                renderer.addMenuViewable(new Viewable(item.getTexture(), new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y), layer, 2f));

                if (itemStack.getAmount() > 1) {
                    Vector2 position = new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y);

                    if (itemStack.getAmount() < 10) {
                        renderer.addMenuText(new Text(String.valueOf(itemStack.getAmount()), new Vector2(position.x + 20, position.y + 16), layer + 1, 1f, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                    else {
                        renderer.addMenuText(new Text(String.valueOf(itemStack.getAmount()), new Vector2(position.x + 14, position.y + 16), layer + 1, 1f, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                }
            }
        }

        if (isSelecting && isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
            renderer.addMenuViewable(new Viewable(selectedSlot, new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y), layer + 2, 2f));

            if (settings.isHitboxShown) {
                renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.GREEN));
            }
        }
        else if (settings.isHitboxShown) {
            renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.YELLOW));
        }
    }

    public final int getSlotNumber() {
        return this.slotNumber;
    }

    public final Container getContainer() {
        return this.container;
    }

    public final ItemStack getItemStack() {
        if (container != null) {
            return container.getItemStack(slotNumber);
        }
        else {
            Debug.logWarning("Container does not exist.");
            return null;
        }
    }
}
