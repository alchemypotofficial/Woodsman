package com.alchemy.woodsman.core.utilities.recipes;

import com.alchemy.woodsman.common.items.Inventory.ItemStack;

import java.util.ArrayList;

public class RecipeCampfire extends Recipe {

    private ArrayList<ItemStack> ingredients = new ArrayList<>();

    public RecipeCampfire(String id) {
        super(id);
    }

    public final void addIngredient(ItemStack ingredient) {
        if (ingredient != null) {
            ingredients.add(ingredient);
        }
    }

    public final ArrayList<ItemStack> getIngredients() {
        return new ArrayList<ItemStack>(ingredients);
    }
}
