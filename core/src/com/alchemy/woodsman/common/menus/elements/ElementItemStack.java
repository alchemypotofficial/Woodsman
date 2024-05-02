package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ElementItemStack extends Element {

    private ItemStack itemStack;
    private float scale;

    public ElementItemStack(Box collider, ItemStack itemStack) {
        super(collider);

        this.itemStack = itemStack;

        scale = 1f;
    }

    public ElementItemStack(Box collider, ItemStack itemStack, float scale) {
        super(collider);

        this.itemStack = itemStack;
        this.scale = scale;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Box collider = getCollider();
        if (itemStack != null) {
            Item item = itemStack.getItem();
            if (item != null) {
                renderer.addMenuViewable(new Viewable(item.getTexture(), new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y), layer, scale + 1f));

                if (itemStack.getAmount() > 1) {
                    Vector2 position = collider.getBottomLeft();

                    if (itemStack.getAmount() < 10) {
                        renderer.addMenuText(new Text(String.valueOf(itemStack.getAmount()), new Vector2(position.x + 20, position.y + 16), layer + 1, scale, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                    else {
                        renderer.addMenuText(new Text(String.valueOf(itemStack.getAmount()), new Vector2(position.x + 14, position.y + 16), layer + 1, scale, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                }
            }
        }
    }

    public final void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public final ItemStack getItemStack() {
        return this.itemStack;
    }
}
