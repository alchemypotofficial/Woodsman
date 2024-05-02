package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.init.Tiers;

public class ItemTool extends Item {

    private Tier tier;

    public ItemTool(String id, String staticName, String texturePath, int maxAmount, String tier) {
        super(id, staticName, texturePath, maxAmount);

        this.tier = Tiers.TIERS.getEntry(tier);

        maxDurability = this.tier.getDurability();

        useTime = 0.75f;
    }

    public final Tier getTier() {
        return this.tier;
    }
}
