package com.alchemy.woodsman.core.world.biomes;

import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Blocks;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.Noise;
import com.alchemy.woodsman.core.world.Chunk;
import com.alchemy.woodsman.core.world.World;

import java.util.Random;

public class Forest extends Biome {

    public Forest(String id) {
        super(id);
    }

    @Override
    public void generateFloor(World world, Chunk chunk) {
        int chunkSize = chunk.getChunkSize();
        BlockPosition chunkPosition = chunk.getPosition();

        int worldBlockSize = world.getWorldSize() * chunkSize;
        int chunkOffsetX = chunkPosition.x * chunkSize;
        int chunkOffsetY = chunkPosition.y * chunkSize;

        Noise noise = new Noise(world.getSeed());

        //* Generate floor of chunk.
        for (int y = 0; y < chunk.chunkSize; y++) {
            for (int x = 0; x < chunk.chunkSize; x++) {
                float noiseValue = noise.sampleTileable(x + chunkOffsetX, y + chunkOffsetY, worldBlockSize, 16f, 2, 4f, 0.25f);
                float surfaceHeight = 100 + noiseValue * 20;

                if (surfaceHeight > 100) {
                    chunk.setFloor(Floors.FLOOR_GRASS, x, y);
                }
                else if (surfaceHeight > 99) {
                    chunk.setFloor(Floors.FLOOR_SAND, x, y);
                }
                else {
                    chunk.setFloor(Floors.FLOOR_WATER, x, y);
                }
            }
        }

        //* Add clay deposits.
        for (int y = 0; y < chunk.chunkSize; y++) {
            for (int x = 0; x < chunk.chunkSize; x++) {
                float noiseValue = noise.sampleTileable(x + chunkOffsetX, y + chunkOffsetY, worldBlockSize, 16f, 8, 6f, 1f);
                float surfaceHeight = 100 + noiseValue * 20;

                Floor floor = chunk.getFloor(x, y);

                if (surfaceHeight > 105 && floor == Floors.FLOOR_GRASS) {
                    chunk.setFloor(Floors.FLOOR_CLAY, x, y);
                }
            }
        }
    }

    @Override
    public void generateMountain(World world, Chunk chunk) {
        int chunkSize = chunk.getChunkSize();
        BlockPosition chunkPosition = chunk.getPosition();

        int worldBlockSize = world.getWorldSize() * chunkSize;
        int chunkOffsetX = chunkPosition.x * chunkSize;
        int chunkOffsetY = chunkPosition.y * chunkSize;

        Noise noise = new Noise(world.getSeed());

        //* Generate mountain of chunk.
        for (int y = 0; y < chunk.chunkSize; y++) {
            for (int x = 0; x < chunk.chunkSize; x++) {
                float noiseValue = noise.sampleTileable(x + chunkOffsetX, y + chunkOffsetY, worldBlockSize, 16f, 3, 4f, 0.25f);
                float surfaceHeight = 100 + noiseValue * 20;

                Floor floor = chunk.getFloor(x, y);

                if (surfaceHeight > 104 && (floor == Floors.FLOOR_GRASS || floor == Floors.FLOOR_CLAY || floor == Floors.FLOOR_FRESH_WATER)) {
                    chunk.setBlock(Blocks.BLOCK_CHERT_STONE, x, y);
                    chunk.setFloor(Floors.FLOOR_STONE, x, y);
                    chunk.setCeiling(true, x, y);
                }
            }
        }
    }

    @Override
    public void generateDecor(World world, Chunk chunk) {
        int chunkSize = chunk.getChunkSize();
        BlockPosition chunkPosition = chunk.getPosition();

        int chunkOffsetX = chunkPosition.x * chunkSize;
        int chunkOffsetY = chunkPosition.y * chunkSize;

        int treeSeed = (chunkPosition.x * 10000 + chunkPosition.y) * 2000;
        Random treeRand = new Random(treeSeed);

        //* Place treeAmount of trees in chunk.
        int treeAmount = treeRand.nextInt(4, 7);
        for (int i = 0; i < treeAmount; i++) {
            int blockPosX = treeRand.nextInt(chunkSize);
            int blockPosY = treeRand.nextInt(chunkSize);

            //* Check if position is valid for placement.
            boolean canPlace = true;
            for (int y = -3; y < 4; y++) {
                for (int x = -3; x < 4; x++) {
                    BlockPosition blockPosition = new BlockPosition(x + blockPosX + chunkOffsetX, y + blockPosY + chunkOffsetY);
                    Floor floor = world.getFloor(blockPosition);
                    Block block = world.getBlock(blockPosition);

                    if ((floor != Floors.FLOOR_GRASS && floor != Floors.FLOOR_CLAY) || block != null) {
                        canPlace = false;
                        break;
                    }
                }

                if (canPlace == false) { break; }
            }

            //* Place tree decorations.
            if (canPlace) {
                chunk.setBlock(Blocks.BLOCK_OAK_TREE, blockPosX, blockPosY);
            }
        }

        int stickSeed = (chunkPosition.x * 10000 + chunkPosition.y) * 3000;
        Random stickRand = new Random(stickSeed);

        //* Place stickAmount of sticks in chunk.
        int stickAmount = stickRand.nextInt(5, 10);
        for (int i = 0; i < stickAmount; i++) {
            int blockPosX = stickRand.nextInt(chunkSize);
            int blockPosY = stickRand.nextInt(chunkSize);

            //* Check if position is valid for placement.
            boolean canPlace = true;
            for (int y = -3; y < 4; y++) {
                for (int x = -3; x < 4; x++) {
                    BlockPosition blockPosition = new BlockPosition(x + blockPosX + chunkOffsetX, y + blockPosY + chunkOffsetY);
                    Floor floor = world.getFloor(blockPosition);
                    Block block = world.getBlock(blockPosition);

                    if (floor != Floors.FLOOR_GRASS || block != null) {
                        canPlace = false;
                        break;
                    }
                }

                if (canPlace == false) { break; }
            }

            //* Place stick decorations.
            if (canPlace) {
                chunk.setBlock(Blocks.BLOCK_STICK, blockPosX, blockPosY);
            }
        }

        int rocksSeed = (chunkPosition.x * 10000 + chunkPosition.y) * 4000;
        Random rocksRand = new Random(rocksSeed);

        //* Place rocksAmount of stones in chunk.
        int rocksAmount = rocksRand.nextInt(6, 10);
        for (int i = 0; i < rocksAmount; i++) {
            int blockPosX = stickRand.nextInt(chunkSize);
            int blockPosY = stickRand.nextInt(chunkSize);

            //* Check if position is valid for placement.
            boolean canPlace = true;
            for (int y = -3; y < 4; y++) {
                for (int x = -3; x < 4; x++) {
                    BlockPosition blockPosition = new BlockPosition(x + blockPosX + chunkOffsetX, y + blockPosY + chunkOffsetY);
                    Floor floor = world.getFloor(blockPosition);
                    Block block = world.getBlock(blockPosition);

                    if (floor != Floors.FLOOR_GRASS || block != null) {
                        canPlace = false;
                        break;
                    }
                }

                if (canPlace == false) { break; }
            }

            //* Place rocks decorations.
            if (canPlace) {
                chunk.setBlock(Blocks.BLOCK_CHERT_ROCKS, blockPosX, blockPosY);
            }
        }

        placeRandom(world, chunk, Blocks.BLOCK_ROSE, 1, 1, 0.25f, 2);
        placeRandom(world, chunk, Blocks.BLOCK_DAYLILY, 1, 1, 0.25f, 3);
    }

    private void placeRandom(World world, Chunk chunk, Block block, int min, int max, float chance, int seedOffset) {
        int chunkSize = chunk.getChunkSize();
        BlockPosition chunkPosition = chunk.getPosition();

        int chunkOffsetX = chunkPosition.x * chunkSize;
        int chunkOffsetY = chunkPosition.y * chunkSize;

        int seed = (chunkPosition.x * 10000 + chunkPosition.y) * (4000 * seedOffset);
        Random random = new Random(seed);

        //* Try to place block in chunk "tries" times.
        int tries = random.nextInt(min, max + 1);
        for (int i = 0; i < tries; i++) {
            float placeChance = random.nextFloat();

            //* Check if the place chance succeeded.
            if (placeChance < chance) {
                int blockPosX = random.nextInt(chunkSize);
                int blockPosY = random.nextInt(chunkSize);
                BlockPosition blockPosition = new BlockPosition(blockPosX + chunkOffsetX, blockPosY + chunkOffsetY);

                Floor floor = world.getFloor(blockPosition);
                Block replacedBlock = world.getBlock(blockPosition);

                //* Place block if the floor is valid for placement and block can be replaced.
                if (replacedBlock != null && replacedBlock.canReplace() && block.isValidFloor(floor)) {
                    chunk.setBlock(block, blockPosX, blockPosY);
                }
                else if (replacedBlock == null && block.isValidFloor(floor)) {
                    chunk.setBlock(block, blockPosX, blockPosY);
                }
            }
        }
    }

    @Override
    public SoundAsset getAmbience(Chunk chunk) {
        return SoundHandler.getSound("sounds/ambience/Forest_Wind.wav");
    }
}
