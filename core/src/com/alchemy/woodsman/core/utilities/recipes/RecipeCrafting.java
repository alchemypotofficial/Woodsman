package com.alchemy.woodsman.core.utilities.recipes;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;

import java.util.ArrayList;

public class RecipeCrafting extends Recipe {

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ItemStack result;

    public RecipeCrafting(String id) {
        super(id);
    }

    public final void addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            ingredients.add(ingredient);
        }
    }

    public final void setResult(ItemStack result) {
        this.result = result;
    }

    public final ArrayList<Ingredient> getIngredients() {
        return new ArrayList<Ingredient>(ingredients);
    }

    public final ItemStack getResult() {
        return this.result;
    }
}
