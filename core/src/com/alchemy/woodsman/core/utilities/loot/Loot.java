package com.alchemy.woodsman.core.utilities.loot;

import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.graphics.data.Registerable;

import java.util.ArrayList;

public class Loot extends Registerable {

    private ArrayList<LootItem> lootItems = new ArrayList<>();

    public Loot(String id) {
        super(id);
    }

    public final void addLootItem(LootItem lootItem) {
        lootItems.add(lootItem);
    }

    public final ArrayList<LootItem> getLootItems() {
        return new ArrayList<>(lootItems);
    }
}
