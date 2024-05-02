package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.items.Tools.Tier;
import com.alchemy.woodsman.core.graphics.data.Registry;

public class Tiers {

    public static final Registry<Tier> TIERS = new Registry<Tier>();

    public static final Tier TIER_FLINT = new Tier("woodsman.flint", 3, 125, 3);
    public static final Tier TIER_COPPER = new Tier("woodsman.copper", 5, 600, 3);

    public static void register() {
        TIERS.register(TIER_FLINT);
        TIERS.register(TIER_COPPER);
    }
}
