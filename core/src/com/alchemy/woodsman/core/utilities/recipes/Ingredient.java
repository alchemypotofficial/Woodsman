package com.alchemy.woodsman.core.utilities.recipes;

import com.alchemy.woodsman.common.items.Item;

public class Ingredient {
    private Item item;
    private int amount;

    public Ingredient(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return this.item;
    }

    public int getAmount() {
        return this.amount;
    }
}
