package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.blocks.*;
import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.utilities.physics.Box;

public class Blocks {

    public static final Registry<Block> BLOCKS = new Registry<Block>();

    public static final BlockStone BLOCK_STONE = new BlockStone("woodsman.stone", 1, new Box(0.25f, 0.25f, 0.5f, 0.5f), true, false, "sprites/blocks/Block_Stone.png");
    public static final BlockTallGrass BLOCK_TALL_GRASS = new BlockTallGrass("woodsman.tall_grass", 1, Box.ONE, false, false, "sprites/blocks/Block_Tall_Grass.png");
    public static final BlockTree BLOCK_OAK_TREE = new BlockTree("woodsman.oak_tree", 12, Box.ONE, true, false, "sprites/blocks/Block_Oak_Tree.png");
    public static final BlockWall BLOCK_CHERT_STONE = new BlockWall("woodsman.chert_stone", 1, Box.ONE, true, true, "sprites/blocks/Block_Chert_Wall.png");
    public static final BlockCampfire BLOCK_CAMPFIRE = new BlockCampfire("woodsman.campfire", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), true, false, "sprites/blocks/Block_Campfire.png");
    public static final BlockChest BLOCK_CHEST = new BlockChest("woodsman.chest", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), true, false, "sprites/blocks/Block_Chest.png");

    public static final BlockDecor BLOCK_CHERT_ROCKS = new BlockDecor("woodsman.chert_rocks", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), false, false, "sprites/blocks/Block_Chert_Rocks.png");
    public static final BlockDecor BLOCK_STICK = new BlockDecor("woodsman.stick", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), false, false, "sprites/blocks/Block_Stick.png");

    public static final BlockFlower BLOCK_ROSE = new BlockFlower("woodsman.rose", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), false, false, "sprites/blocks/Block_Rose.png");

    public static final BlockFlower BLOCK_DAYLILY = new BlockFlower("woodsman.daylily", 1, new Box(0.125f, 0.125f, 0.75f, 0.75f), false, false, "sprites/blocks/Block_Daylily.png");

    public static void register() {
        BLOCKS.register(BLOCK_CHERT_ROCKS);
        BLOCKS.register(BLOCK_STICK);

        BLOCKS.register(BLOCK_TALL_GRASS);
        BLOCKS.register(BLOCK_OAK_TREE);
        BLOCKS.register(BLOCK_CHERT_STONE);

        BLOCKS.register(BLOCK_CAMPFIRE);
        BLOCKS.register(BLOCK_CHEST);

        BLOCKS.register(BLOCK_ROSE);
        BLOCKS.register(BLOCK_DAYLILY);
    }
}
