package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.world.biomes.Biome;
import com.alchemy.woodsman.core.world.biomes.Forest;

public class Tags {

    public static final Registry<BlockTag> BLOCK_TAGS = new Registry<BlockTag>();

    public static final BlockTag DESTROYABLE_PICKAXE = new BlockTag("destroyable_pickaxe");
    public static final BlockTag DESTROYABLE_AXE = new BlockTag("destroyable_axe");
    public static final BlockTag DESTROYABLE_SHOVEL = new BlockTag("destroyable_axe");
    public static final BlockTag STONE = new BlockTag("stone");

    public static void register() {
        BLOCK_TAGS.register(DESTROYABLE_PICKAXE);
        BLOCK_TAGS.register(DESTROYABLE_AXE);
        BLOCK_TAGS.register(DESTROYABLE_SHOVEL);
        BLOCK_TAGS.register(STONE);
    }
}
