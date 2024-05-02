package com.alchemy.woodsman.core.utilities.recipes.types;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.recipes.RecipeCampfire;
import com.badlogic.gdx.utils.JsonValue;

public class RecipeTypeCampfire extends RecipeTypeCrafting {

    public RecipeTypeCampfire(String id, String path) {
        super(id, path);
    }

    @Override
    public void loadRecipe(JsonValue jsonBase) {

        //* Check if recipe has recipeType ID.
        if (!jsonBase.has("id")) {
            Debug.logError("Recipe does not have an id in \"" + jsonBase.name() + "\".");
            return;
        }

        String idValue = jsonBase.getString("id");

        //* Check if recipe has recipeType ID.
        if (idValue == "") {
            Debug.logError("Recipe does not have an id in \"" + jsonBase.name() + "\".");
            return;
        }

        //* Create new campfire recipe.
        RecipeCampfire recipe = new RecipeCampfire(idValue);

        JsonValue ingredientValues = jsonBase.get("ingredients");

        for (JsonValue ingredientValue : ingredientValues) {
            if (!ingredientValue.has("item")) {
                Debug.logError("Ingredient has no item in \"" + jsonBase.name() + "\".");
                return;
            }
            else if (!ingredientValue.has("amount")) {
                Debug.logError("Ingredient has no amount in \"" + jsonBase.name() + "\".");
                return;
            }

            String itemValue = ingredientValue.getString("item");
            int amountValue = ingredientValue.getInt("amount");

            if (Items.ITEMS.hasEntry(itemValue) && amountValue > 0) {
                Item item = Items.ITEMS.getEntry(itemValue);
                int amount = amountValue;
                recipe.addIngredient(new ItemStack(item, amount));
            }
        }

        recipes.add(recipe);
    }
}
