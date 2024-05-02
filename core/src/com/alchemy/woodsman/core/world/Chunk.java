package com.alchemy.woodsman.core.world;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blockstates.BlockState;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.init.Biomes;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.Noise;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.world.biomes.Biome;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Chunk {

    public final World world;
    public final BlockPosition position;
    public Biome biome = Biomes.BIOME_FOREST;
    private Floor floors[];
    private Block blocks[];
    private boolean ceilings[];
    private BlockState blockStates[];

    private float lightLevels[];

    public final int chunkSize;

    public Chunk(World world, int chunkSize, BlockPosition position) {
        this.world = world;
        this.chunkSize = chunkSize;
        this.position = position;

        floors = new Floor[256];
        blocks = new Block[256];
        ceilings = new boolean[256];
        blockStates = new BlockState[256];
        lightLevels = new float[256];
    }

    public void tick(World world) {
        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                Block block = blocks[x + (y * chunkSize)];

                if (block != null) {
                    block.tick(world, getWorldBlockPosition(new BlockPosition(x, y)));
                }
            }
        }
    }

    public void render(Renderer renderer, float renderX, float renderY, World world, EntityPlayer player) {
        Settings settings = Game.getSettings();

        int chunkOffsetX = position.x * chunkSize;
        int chunkOffsetY = position.y * chunkSize;

        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                if (floors[x + (y * chunkSize)] != null) {
                    Floor floor = floors[x + (y * chunkSize)];

                    float worldX = x + renderX;
                    float worldY = y + renderY;

                    float lightLevel = getLightLevel(x, y);
                    BlockPosition blockPosition = new BlockPosition(x + chunkOffsetX, y + chunkOffsetY);

                    Color floorColor = new Color(lightLevel / 15, lightLevel/ 15, lightLevel/ 15, 1f);

                    floor.render(renderer, world, new Vector2(worldX, worldY), blockPosition, floorColor);
                }
            }
        }

        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                if (blocks[x + (y * chunkSize)] != null) {
                    Block block = blocks[x + (y * chunkSize)];

                    float worldX = x + renderX;
                    float worldY = y + renderY;

                    float lightLevel = getLightLevel(x, y);

                    Box playerCollider = player.getCollider();
                    Box blockOccluder = new Box(block.getOccluder().x + worldX, block.getOccluder().y + worldY, block.getOccluder().width, block.getOccluder().height);

                    BlockPosition blockPosition = new BlockPosition(worldX, worldY);

                    //* Render block with light and occlusion.
                    if (block.isOccludable() && blockOccluder.collides(playerCollider)) {
                        Color occludedBlockColor = new Color(lightLevel / 15, lightLevel/ 15, lightLevel/ 15, 0.25f);

                        block.render(renderer, world, blockPosition, occludedBlockColor);
                    }
                    else {
                        Color blockColor = new Color(lightLevel / 15, lightLevel/ 15, lightLevel/ 15, 1f);

                        block.render(renderer, world, blockPosition, blockColor);
                    }
                }
            }
        }

        if (settings.isChunkBorderShown) {
            renderer.addWorldWireframe(new Wireframe(new Box(renderX, renderY, 16, 16), Wireframe.YELLOW));
        }
    }

    public void updateLighting(World world) {
        float skyLightLevel = 0f;

        if (world.getTime() < 6000) {
            skyLightLevel = 2f;
        }
        else if (world.getTime() > 5999 && world.getTime() < 7000) {
            float dayCompletion = ((float)world.getTime() - 5999) / (6999 - 5999);

            skyLightLevel = 2f + (11f * dayCompletion);
        }
        else if (world.getTime() > 6999 && world.getTime() < 18000) {
            skyLightLevel = 13f;
        }
        else if (world.getTime() > 17999 && world.getTime() < 19000) {
            float dayCompletion = ((float)world.getTime() - 17999) / (18999 - 17999);

            skyLightLevel = 13f - (11f * dayCompletion);
        }
        else if (world.getTime() > 18999 && world.getTime() < 24000) {
            skyLightLevel = 2f;
        }
        else {
            skyLightLevel = 0f;
        }

        for (int y = 0; y < chunkSize; y++) {
            int worldPosY = y + ((int)position.y * 16);

            for (int x = 0; x < chunkSize; x++) {
                int worldPosX = x + ((int)position.x * 16);

                float lightLevel = 0f;

                float blockLightLevel = 0f;
                Block block = blocks[x + (y * chunkSize)];
                if (block != null ) {
                    blockLightLevel = block.getLightLevel(world, new BlockPosition(worldPosX, worldPosY));
                }

                boolean ceiling = getCeiling(x, y);

                float upLightLevel = world.getLightLevel(worldPosX, worldPosY + 1);
                float downLightLevel = world.getLightLevel(worldPosX , worldPosY - 1);
                float leftLightLevel = world.getLightLevel(worldPosX - 1, worldPosY);
                float rightLightLevel = world.getLightLevel(worldPosX + 1, worldPosY);

                Block upBlock = world.getBlock(worldPosX, worldPosY + 1);
                Block downBlock = world.getBlock(worldPosX , worldPosY - 1);
                Block leftBlock = world.getBlock(worldPosX - 1, worldPosY);
                Block rightBlock = world.getBlock(worldPosX + 1, worldPosY);

                if (lightLevel < blockLightLevel) { lightLevel = blockLightLevel; }
                if (lightLevel < skyLightLevel && ceiling == false) { lightLevel = skyLightLevel; }
                if (lightLevel < upLightLevel - 1 && (upBlock == null || upBlock.getLightLevel(world, new BlockPosition(worldPosX, worldPosY + 1)) > 0)) { lightLevel = upLightLevel - 1; }
                if (lightLevel < downLightLevel - 1 && (downBlock == null || downBlock.getLightLevel(world, new BlockPosition(worldPosX, worldPosY - 1)) > 0)) { lightLevel = downLightLevel - 1; }
                if (lightLevel < leftLightLevel - 1 && (leftBlock == null || leftBlock.getLightLevel(world, new BlockPosition(worldPosX - 1, worldPosY)) > 0)) { lightLevel = leftLightLevel - 1; }
                if (lightLevel < rightLightLevel - 1 && (rightBlock == null || rightBlock.getLightLevel(world, new BlockPosition(worldPosX + 1, worldPosY)) > 0)) { lightLevel = rightLightLevel - 1; }

                lightLevels[x + (y * chunkSize)] = lightLevel;
            }
        }

        for (int y = chunkSize - 1; y > 0; y--) {
            int worldPosY = y + ((int)position.y * 16);

            for (int x = chunkSize - 1; x > 0; x--) {
                int worldPosX = x + ((int) position.x * 16);

                float lightLevel = 0f;

                float blockLightLevel = 0f;
                Block block = blocks[x + (y * chunkSize)];
                if (block != null) {
                    blockLightLevel = block.getLightLevel(world, new BlockPosition(worldPosX, worldPosY));
                }

                boolean ceiling = getCeiling(x, y);

                float upLightLevel = world.getLightLevel(worldPosX, worldPosY + 1);
                float downLightLevel = world.getLightLevel(worldPosX , worldPosY - 1);
                float leftLightLevel = world.getLightLevel(worldPosX - 1, worldPosY);
                float rightLightLevel = world.getLightLevel(worldPosX + 1, worldPosY);

                Block upBlock = world.getBlock(worldPosX, worldPosY + 1);
                Block downBlock = world.getBlock(worldPosX , worldPosY - 1);
                Block leftBlock = world.getBlock(worldPosX - 1, worldPosY);
                Block rightBlock = world.getBlock(worldPosX + 1, worldPosY);

                if (lightLevel < blockLightLevel) { lightLevel = blockLightLevel; }
                if (lightLevel < skyLightLevel && ceiling == false) { lightLevel = skyLightLevel; }
                if (lightLevel < upLightLevel - 1 && (upBlock == null || upBlock.getLightLevel(world, new BlockPosition(worldPosX, worldPosY + 1)) > 0)) { lightLevel = upLightLevel - 1; }
                if (lightLevel < downLightLevel - 1 && (downBlock == null || downBlock.getLightLevel(world, new BlockPosition(worldPosX, worldPosY - 1)) > 0)) { lightLevel = downLightLevel - 1; }
                if (lightLevel < leftLightLevel - 1 && (leftBlock == null || leftBlock.getLightLevel(world, new BlockPosition(worldPosX - 1, worldPosY)) > 0)) { lightLevel = leftLightLevel - 1; }
                if (lightLevel < rightLightLevel - 1 && (rightBlock == null || rightBlock.getLightLevel(world, new BlockPosition(worldPosX + 1, worldPosY)) > 0)) { lightLevel = rightLightLevel - 1; }

                lightLevels[x + (y * chunkSize)] = lightLevel;
            }
        }
    }

    public void generateFloor() {
        biome.generateFloor(world, this);
    }

    public void generateMountain() {
        biome.generateMountain(world, this);
    }

    public void generateDecor() {
        biome.generateDecor(world, this);
    }

    public void setFloor(Floor floor, int x, int y) {
        if (floor != null) {
            if (x < 0 || y < 0) {
                Debug.logError("Cannot set chunk's floor at negative position x or y.");
                return;
            }
            else if (x > chunkSize || y > chunkSize) {
                Debug.logError("Cannot set chunk's floor at out of bounds position x or y.");
                return;
            }

            floors[x + (y * chunkSize)] = floor;
        }
    }

    public void setBlock(Block block, int chunkBlockPosX, int chunkBlockPosY) {
        blocks[chunkBlockPosX + (chunkBlockPosY * chunkSize)] = block;

        if (block != null) {
            if (block.createBlockState() == null) {
                Debug.logError("Block \"" + block.getID() + "\"'s blockstate cannot be null.");
            }

            blockStates[chunkBlockPosX + (chunkBlockPosY * chunkSize)] = block.createBlockState();
        }
    }

    public void setCeiling(boolean ceiling, int chunkBlockPosX, int chunkBlockPosY) {
        ceilings[chunkBlockPosX + (chunkBlockPosY * chunkSize)] = ceiling;
    }

    public void setBlockState(BlockState blockState, int chunkBlockPosX, int chunkBlockPosY) {
        blockStates[chunkBlockPosX + (chunkBlockPosY * chunkSize)] = blockState;
    }

    public final BlockPosition getWorldBlockPosition(BlockPosition blockPosition) {
        return new BlockPosition(blockPosition.x + (position.x * chunkSize), blockPosition.y + (position.y * chunkSize));
    }

    public Floor getFloor(int x, int y) {
        if (x < 0 || y < 0) {
            Debug.logError("Cannot get chunk's floor at negative position x or y.");
            return null;
        }
        else if (x > chunkSize || y > chunkSize) {
            Debug.logError("Cannot get chunk's floor at out of bounds position x or y.");
            return null;
        }

        return floors[x + (y * chunkSize)];
    }

    public Block getBlock(int chunkBlockPosX, int chunkBlockPosY) {
        return blocks[chunkBlockPosX + (chunkBlockPosY * chunkSize)];
    }

    public boolean getCeiling(int chunkBlockPosX, int chunkBlockPosY) {
        return ceilings[chunkBlockPosX + (chunkBlockPosY * chunkSize)];
    }

    public BlockState getBlockState(int chunkBlockPosX, int chunkBlockPosY) {
        return blockStates[chunkBlockPosX + (chunkBlockPosY * chunkSize)];
    }

    public float getLightLevel(int chunkLightPosX, int chunkLightPosY) {
        return lightLevels[chunkLightPosX + (chunkLightPosY * chunkSize)];
    }

    public Biome getBiome() {
        return this.biome;
    }

    public final int getChunkSize() {
        return this.chunkSize;
    }

    public final BlockPosition getPosition() {
        return this.position;
    }
}
