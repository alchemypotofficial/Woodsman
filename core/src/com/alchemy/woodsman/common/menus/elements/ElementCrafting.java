package com.alchemy.woodsman.common.menus.elements;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.recipes.Ingredient;
import com.alchemy.woodsman.core.utilities.recipes.RecipeCrafting;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ElementCrafting extends Element {

    private RecipeCrafting recipe;

    public ElementCrafting(Box collider, RecipeCrafting recipe) {
        super(collider);

        this.recipe = recipe;
    }

    @Override
    public void render(Renderer renderer, int layer, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        TextureAsset selectedSlot = Renderer.getTexture("sprites/menus/Menu_Selected_Slot.png");

        Box collider = getCollider();
        ItemStack result = getResult();
        ArrayList<Ingredient> ingredients = getIngredients();

        if (result != null) {
            Item resultItem = result.getItem();
            if (resultItem != null) {
                renderer.addMenuViewable(new Viewable(resultItem.getTexture(), new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y), layer, 2f));

                if (result.getAmount() > 1) {
                    Vector2 position = new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y);

                    if (result.getAmount() < 10) {
                        renderer.addMenuText(new Text(String.valueOf(result.getAmount()), new Vector2(position.x + 20, position.y + 16), layer + 1, 1f, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                    else {
                        renderer.addMenuText(new Text(String.valueOf(result.getAmount()), new Vector2(position.x + 14, position.y + 16), layer + 1, 1f, new Color(9 / 255, 10 / 255, 20 / 255, 1)));
                    }
                }
            }
        }

        if (isHovered(renderer.toMenuPosition(InputHandler.getMousePosition()))) {
            //* If slot is hovered, show selection.
            renderer.addMenuViewable(new Viewable(selectedSlot, new Vector2(collider.getBottomLeft().x, collider.getBottomLeft().y), layer + 2, 2f));

            //* If slot is hovered, show tooltip.
            ElementResizeable namePlate = new ElementResizeable(new Box(mousePosition.x, mousePosition.y, 160, 48), Renderer.getTexture("sprites/menus/system/Menu_Border.png"));
            ElementResizeable ingredientsPlate = new ElementResizeable(new Box(mousePosition.x, mousePosition.y - 118, 160, 128), Renderer.getTexture("sprites/menus/system/Menu_Border.png"));

            namePlate.render(renderer, 50, mousePosition);
            ingredientsPlate.render(renderer, 50, mousePosition);

            renderer.addMenuText(new Text(getResult().getItem().getName(), namePlate.getCollider().getTopLeft().add(20, -20), 51, 1f, Renderer.WHITE));
            renderer.addMenuText(new Text("Ingredients:", new Vector2(namePlate.getCollider().x + 20, namePlate.getCollider().y - 10), 51, 1f, Renderer.WHITE));

            int index = 0;
            for (Ingredient ingredient : ingredients) {
                ElementItemStack elementItem = new ElementItemStack(new Box(mousePosition.x + 16, mousePosition.y - (index * 28) - 64, 16, 16), new ItemStack(ingredient.getItem(), ingredient.getAmount()), 1f);
                elementItem.render(renderer, 51, mousePosition);

                renderer.addMenuText(new Text(ingredient.getItem().getName(), new Vector2(elementItem.getCollider().x + 34, elementItem.getCollider().y + 20), 51, 1f, Renderer.WHITE));

                index++;
            }

            if (settings.isHitboxShown) {
                renderer.addMenuWireframe(new Wireframe(ingredientsPlate.getCollider(), Wireframe.GREEN));

                renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.GREEN));
            }
        }
        else if (settings.isHitboxShown) {
            renderer.addMenuWireframe(new Wireframe(getCollider(), Wireframe.YELLOW));
        }
    }

    public final ArrayList<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    public final ItemStack getResult() {
        return recipe.getResult();
    }

    public final RecipeCrafting getRecipe() {
        return this.recipe;
    }
}
