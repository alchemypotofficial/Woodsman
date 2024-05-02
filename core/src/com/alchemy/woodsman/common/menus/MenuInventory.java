package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.menus.elements.*;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Recipes;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.recipes.Ingredient;
import com.alchemy.woodsman.core.utilities.recipes.Recipe;
import com.alchemy.woodsman.core.utilities.recipes.RecipeCrafting;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MenuInventory extends MenuContainer {

    protected final ElementArea inventoryArea = new ElementArea(new Box(-135, -110, 270, 32));
    private enum InventoryTab { EQUIPMENT, CRAFTING };

    private InventoryTab tab;
    private ElementButton equipmentButton;
    private ElementButton craftingButton;

    private ElementCrafting craftingSlots[];

    public MenuInventory(String id) {
        super(id);

        tab = InventoryTab.EQUIPMENT;

        craftingSlots = new ElementCrafting[12];
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        super.render(renderer, mousePosition);

        //* Render inventory.
        TextureAsset inventoryBackground = Renderer.getTexture("sprites/menus/Menu_Inventory_Background.png");
        int textureWidth = inventoryBackground.rawTexture.getWidth();
        renderer.addMenuViewable(new Viewable(inventoryBackground, new Vector2(-textureWidth, -120), 20, 2f));

        //* Render slots.
        Container inventory = player.getInventory();
        for (int index = 0; index < 8; index++) {
            addSlot(new ElementSlot(new Box((-textureWidth + 14) + (index * 34), -108, 32, 32), inventory, index, true));
        }

        //* Render tab buttons.
        TextureAsset inventoryTab = Renderer.getTexture("sprites/menus/Menu_Inventory_Tab.png");

        equipmentButton = new ElementButton(new Box(-164f, 130f, inventoryTab.getWidth() * 2,  inventoryTab.getHeight() * 2), inventoryTab);
        equipmentButton.render(renderer, 21, mousePosition);

        craftingButton = new ElementButton(new Box(-110f, 130f, inventoryTab.getWidth() * 2,  inventoryTab.getHeight() * 2), inventoryTab);
        craftingButton.render(renderer, 21, mousePosition);

        //* Render equipment tab.
        if (tab == InventoryTab.EQUIPMENT) {
            //* Render equipment.
            TextureAsset equipmentBackground = Renderer.getTexture("sprites/menus/Menu_Equipment_Background.png");
            textureWidth = equipmentBackground.getWidth();
            renderer.addMenuViewable(new Viewable(equipmentBackground, new Vector2(-textureWidth, -54), 20, 2f));

            //* Render world stats.
            renderer.addMenuText(new Text(player.getName(), new Vector2(0, 70), 21));

            //* Render player equipment.
            Container equipment = player.getEquipment();

            addSlot(new ElementSlot(new Box((-textureWidth) + 116, 40, 32, 32), equipment, 0, true ));
            addSlot(new ElementSlot(new Box((-textureWidth) + 116, 74, 32, 32), equipment, 1, true ));
            addSlot(new ElementSlot(new Box((-textureWidth) + 22, 40, 32, 32), equipment, 2, true ));
            addSlot(new ElementSlot(new Box((-textureWidth) + 22, 74, 32, 32), equipment, 3, true ));

            addSlot(new ElementSlot(new Box((-textureWidth) + 22, -2, 32, 32), equipment, 4, true ));

            //* Render player character.
            TextureAsset playerTexture = Renderer.getTexture("sprites/entities/player/Player_Idle.png");
            renderer.addMenuViewable(new Viewable(playerTexture, new Box(0, 48, 48, 48), new Vector2(-132, 8), 24, 2f, null));
        }
        else {
            //* Render crafting.
            TextureAsset craftingBackground = Renderer.getTexture("sprites/menus/Menu_Crafting_Background.png");
            textureWidth = craftingBackground.getWidth();

            renderer.addMenuViewable(new Viewable(craftingBackground, new Vector2(-textureWidth, -54), 20, 2f));

            ArrayList<Recipe> recipes = Recipes.CRAFTING.getRecipes();
            int recipeCount = recipes.size();
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 8; x++) {
                    int index = x + (y * 8);

                    if (index < recipeCount) {
                        Recipe recipe = recipes.get(index);
                        if (recipe instanceof RecipeCrafting craftingRecipe) {

                            craftingSlots[index] = new ElementCrafting(new Box(-151 + (x * 32), 76 + (y * -32), 32, 32), craftingRecipe);
                            craftingSlots[index].render(renderer, 30, mousePosition);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onMouseLeftDown(Vector2 mousePosition) {
        super.onMouseLeftDown(mousePosition);

        if (equipmentButton != null) {
            if (equipmentButton.isHovered(mousePosition)) {
                tab = InventoryTab.EQUIPMENT;

                SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
                SoundHandler.playSound(sound, Game.getSettings().effectsVolume, false);
            }
        }

        if (craftingButton != null) {            if (craftingButton.isHovered(mousePosition)) {
                tab = InventoryTab.CRAFTING;

                SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
                SoundHandler.playSound(sound, Game.getSettings().effectsVolume, false);
            }
        }

        //* If player clicks on crafting slot.
        for (ElementCrafting craftingSlot : craftingSlots) {
            if (craftingSlot != null && craftingSlot.isHovered(mousePosition)) {
                RecipeCrafting recipe = craftingSlot.getRecipe();
                ArrayList<Ingredient> ingredients = recipe.getIngredients();

                Container inventory = player.getInventory();

                //* Check if player has ingredients.
                boolean hasIngredients = true;
                for (Ingredient ingredient : ingredients) {
                    if (ingredient != null) {
                        int totalItem = inventory.getTotalItem(ingredient.getItem());
                        if (totalItem < ingredient.getAmount()) {
                            hasIngredients = false;
                        }
                    }
                }

                //* If player has ingredients, craft result.
                if (hasIngredients) {
                    for (Ingredient ingredient : ingredients) {
                        if (ingredient != null) {
                            int ingredientAmount = ingredient.getAmount();

                            //* Check all inventory slots for ingredient and reduce them.
                            for (int slot = 0; slot < inventory.getSlots(); slot++) {
                                ItemStack itemStack = inventory.getItemStack(slot);

                                //* Reduce all ingredients.
                                if (itemStack.getItem() == ingredient.getItem()) {
                                    int stackAmount = itemStack.getAmount();

                                    if (stackAmount >= ingredientAmount) {
                                        itemStack.decrease(ingredientAmount);
                                        ingredientAmount = 0;
                                        break;
                                    }
                                    else {
                                        itemStack.setAmount(0);
                                        ingredientAmount -= stackAmount;
                                    }
                                }
                            }
                        }
                    }

                    heldItemStack = recipe.getResult();
                }
            }
        }
    }
}
