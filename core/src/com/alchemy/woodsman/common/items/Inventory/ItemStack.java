package com.alchemy.woodsman.common.items.Inventory;

import com.alchemy.woodsman.common.items.Item;

public class ItemStack {

    private Item item;
    private int amount;
    public int durability;

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;

        if (this.amount > item.getMaxAmount()) {
            this.amount = item.getMaxAmount();
        }
        else if (this.amount < 0) {
            this.amount = 0;
        }

        durability = item.getMaxDurability();
    }

    public ItemStack(ItemStack itemStack) {
        item = itemStack.getItem();
        amount = itemStack.getAmount();

        if (this.amount > item.getMaxAmount()) {
            this.amount = item.getMaxAmount();
        }
        else if (this.amount < 0) {
            this.amount = 0;
        }

        durability = item.getMaxDurability();
    }

    public final void decrease(int decrease) {
        amount -= decrease;

        if (amount < 0) {
            amount = 0;
        }
    }

    public final void increase(int increase) {
        amount += increase;

        if (amount > item.getMaxAmount()) {
            amount = item.getMaxAmount();
        }
    }

    public final void damage(int damage) {
        durability -= damage;

        if (durability < 0) {
            durability = 0;
        }
    }

    public final ItemStack split() {
        ItemStack newItemStack = new ItemStack(item, Math.round((float)amount / 2));
        amount = (int)Math.floor((float)amount / 2);

        return newItemStack;
    }

    public final void setItem(Item item) {
        this.item = item;
    }

    public final void setAmount(int amount) {
        if (amount < 0) {
            amount = 0;
        }
        else if (amount > item.getMaxAmount()) {
            amount = item.getMaxAmount();
        }
        else {
            this.amount = amount;
        }
    }

    public final Item getItem() {
        return this.item;
    }

    public final int getAmount() {
        return this.amount;
    }

    public final int getMaxAmount() {
        if (item != null) {
            return item.getMaxAmount();
        }

        return 0;
    }
}
