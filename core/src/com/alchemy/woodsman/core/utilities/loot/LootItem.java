package com.alchemy.woodsman.core.utilities.loot;

import com.alchemy.woodsman.common.items.Item;

public class LootItem {

    public Item item;
    public int min;
    public int max;

    public LootItem(Item item, int min, int max) {
        this.item = item;
        this.min = min;
        this.max = max;
    }
}
