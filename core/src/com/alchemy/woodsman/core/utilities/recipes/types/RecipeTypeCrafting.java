package com.alchemy.woodsman.core.utilities.recipes.types;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.recipes.Ingredient;
import com.alchemy.woodsman.core.utilities.recipes.RecipeCrafting;
import com.badlogic.gdx.utils.JsonValue;

public class RecipeTypeCrafting extends RecipeType {

    public RecipeTypeCrafting(String id, String path) {
        super(id, path);
    }

    @Override
    public void loadRecipe(JsonValue jsonBase) {

        if (!jsonBase.has("id")) {
            Debug.logError("Recipe does not have an id in \"" + jsonBase.name() + "\".");
            return;
        }

        String idValue = jsonBase.getString("id");

        if (idValue == "") {
            Debug.logError("Recipe does not have an id in \"" + jsonBase.name() + "\".");
            return;
        }

        //* Create new crafting recipe.
        RecipeCrafting recipe = new RecipeCrafting(idValue);

        JsonValue ingredientsValue = jsonBase.get("ingredients");

        for (JsonValue ingredientValue : ingredientsValue) {
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

            if (Items.ITEMS.hasEntry(itemValue)) {
                if (amountValue > 0) {
                    Item item = Items.ITEMS.getEntry(itemValue);
                    int amount = amountValue;
                    recipe.addIngredient(new Ingredient(item, amount));
                }
                else {
                    Debug.logError("Amount in ingredient is not greater than '0' in \"" + jsonBase.name() + "\".");
                    return;
                }
            }
            else {
                Debug.logError("Item in ingredient does not exist in \"" + jsonBase.name() + "\".");
                return;
            }
        }

        JsonValue resultValue = jsonBase.get("result");

        if (!resultValue.has("item")) {
            Debug.logError("Result has no item in \"" + jsonBase.name() + "\".");
            return;
        }
        else if (!resultValue.has("amount")) {
            Debug.logError("Result has no amount in \"" + jsonBase.name() + "\".");
            return;
        }

        String itemValue = resultValue.getString("item");
        int amountValue = resultValue.getInt("amount");

        Item item = Items.ITEMS.getEntry(itemValue);
        int amount = amountValue;

        recipe.setResult(new ItemStack(item, amount));

        Debug.logAlert("Loaded recipe \"" + idValue + "\".");
        recipes.add(recipe);
    }
}
