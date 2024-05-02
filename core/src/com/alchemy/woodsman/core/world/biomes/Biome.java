package com.alchemy.woodsman.core.world.biomes;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.Noise;
import com.alchemy.woodsman.core.world.Chunk;
import com.alchemy.woodsman.core.world.World;

public abstract class Biome extends Registerable {

    public Biome(String id) {
        super(id);
    }

    public abstract void generateFloor(World world, Chunk chunk);

    public abstract void generateDecor(World world, Chunk chunk);

    public abstract void generateMountain(World world, Chunk chunk);

    public SoundAsset getAmbience(Chunk chunk) {
        return null;
    }
}
