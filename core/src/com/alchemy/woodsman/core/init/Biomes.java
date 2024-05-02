package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.world.biomes.Biome;
import com.alchemy.woodsman.core.world.biomes.Forest;

public class Biomes {

    public static final Registry<Biome> BIOMES = new Registry<Biome>();

    public static final Biome BIOME_FOREST = new Forest("woodsman.forest");

    public static void register() {
        BIOMES.register(BIOME_FOREST);
    }
}