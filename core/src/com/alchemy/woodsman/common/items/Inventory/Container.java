package com.alchemy.woodsman.common.items.Inventory;

import com.alchemy.woodsman.common.items.Item;

public class Container {
    private ItemStack itemStacks[];

    public Container(int slots) {
        itemStacks = new ItemStack[slots];
    }

    public final void update() {
        for (int index = 0; index < getSlots(); index++) {
            if (getItemStack(index) != null) {
                ItemStack itemStack = getItemStack(index);

                if (itemStack.getAmount() <= 0) {
                    itemStacks[index] = null;
                }
                else if (itemStack.durability <= 0 && itemStack.getItem().getMaxDurability() > 0) {
                    itemStacks[index] = null;
                }
            }
        }
    }

    public ItemStack addItemStack(ItemStack itemStack) {
        if (itemStack != null) {
            //* Check for same itemStacks that are not full.
            for (ItemStack containerStack : itemStacks) {
                if (containerStack != null) {
                    Item item = itemStack.getItem();
                    Item containerItem = containerStack.getItem();

                    if (containerItem == item && containerStack.getAmount() < containerItem.getMaxAmount()) {
                        int leftoverAmount = (containerStack.getAmount() + itemStack.getAmount()) - containerItem.getMaxAmount();

                        if (leftoverAmount > 0) {
                            containerStack.setAmount(containerStack.getMaxAmount());
                            itemStack.setAmount(leftoverAmount);
                        }
                        else {
                            containerStack.increase(itemStack.getAmount());
                            return null;
                        }
                    }
                }
            }

            //* Check for empty slots.
            for (int slot = 0; slot < getSlots(); slot++) {
                ItemStack containerStack = getItemStack(slot);

                //* If slot is empty set slot to itemStack.
                if (containerStack == null) {
                    setItemStack(itemStack, slot);
                    return null;
                }
            }
        }

        return itemStack;
    }

    public final void setItemStack(ItemStack itemStack, int slot) {
        itemStacks[slot] = itemStack;
    }

    public final ItemStack getItemStack(int slot) {
        return itemStacks[slot];
    }

    public final int getTotalItem(Item item) {
        int ingredientAmount = 0;

        for (int slot = 0; slot < getSlots(); slot++) {
            ItemStack itemStack = getItemStack(slot);

            if (itemStack != null && itemStack.getItem() == item) {
                ingredientAmount += itemStack.getAmount();
            }
        }

        return ingredientAmount;
    }

    public Item getItem(int slot) {
        if (itemStacks[slot] == null) {
            return null;
        }

        return itemStacks[slot].getItem();
    }

    public final int getSlots() {
        return itemStacks.length;
    }

    public final boolean hasEmptySlot() {
        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) {
                return true;
            }
        }

        return false;
    }

    public final boolean hasCombinableSlot(ItemStack otherStack) {
        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) {
                return true;
            }
            else if (itemStack.getItem() == otherStack.getItem() && itemStack.getAmount() < itemStack.getItem().getMaxAmount()) {
                return true;
            }
        }

        return false;
    }
}
