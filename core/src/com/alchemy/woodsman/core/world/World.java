package com.alchemy.woodsman.core.world;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blockstates.BlockState;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityItem;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.handlers.LootHandler;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.sound.SoundInstance;
import com.alchemy.woodsman.core.utilities.*;
import com.alchemy.woodsman.core.utilities.loot.Loot;
import com.alchemy.woodsman.core.utilities.loot.LootItem;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.physics.PhysicsWorld;
import com.alchemy.woodsman.core.world.biomes.Biome;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private Random random;
    private Noise noise;
    private long seed;

    private int worldSize;
    private int chunkSize;

    private int time;

    private Camera camera;

    private EntityPlayer player;

    private Chunk chunks[];
    private ArrayList<Entity> entities = new ArrayList<>();

    private PhysicsWorld physicsWorld;

    private SoundInstance ambience;

    public World(int worldSize, int chunkSize) {
        this.worldSize = worldSize;
        this.chunkSize = chunkSize;

        seed = Game.getSystemTime();

        random = new Random(seed);

        Debug.logAlert("World Seed: " + seed);

        noise = new Noise((int)seed);

        time = 8000;

        chunks = new Chunk[worldSize *  worldSize];

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                chunks[x + (y * worldSize)] = new Chunk(this, chunkSize, new BlockPosition(x - (worldSize / 2), y - (worldSize / 2)));
            }
        }

        generateChunks();

        physicsWorld = new PhysicsWorld(this);

        camera = new Camera(new Vector2(0f, 0f), 0.5f);

        int worldBlockSize = worldSize * chunkSize;

        BlockPosition spawnPoint;
        int spawnRand = 2000;
        while (true) {
            Random random = new Random(spawnRand);
            spawnRand += 500;

            int spawnPointX = random.nextInt(worldBlockSize);
            int spawnPointY = random.nextInt(worldBlockSize);
            spawnPoint = new BlockPosition(spawnPointX, spawnPointY);

            Floor floor = getFloor(spawnPoint);
            Block block = getBlock(spawnPoint);

            if (floor != Floors.FLOOR_WATER && block == null) {
                break;
            }
        }

        player = new EntityPlayer(this, new Vector2(spawnPoint.x + 0.5f, spawnPoint.y + 0.5f));

        player.getInventory().addItemStack(new ItemStack(Items.ITEM_FLINT_PICKAXE, 1));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_FLINT_AXE, 1));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_FLINT_HAMMER, 1));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_CAMPFIRE, 1));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_CHEST, 1));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_STICK, 16));
        player.getInventory().addItemStack(new ItemStack(Items.ITEM_FIRE_PLOW, 1));

        spawnEntity(player);

        Menus.MENU_HEALTH_BAR.setPlayer(player);
        MenuHandler.show(Menus.MENU_HEALTH_BAR);

        Menus.MENU_HOTBAR.setPlayer(player);
        MenuHandler.focus(Menus.MENU_HOTBAR);
    }

    public void tick() {
        ArrayList<Entity> tickEntities = new ArrayList<>(entities);
        entities.clear();
        for (Entity entity : tickEntities) {
            if (!entity.isDestroyed()) {
                entities.add(entity);
            }
        }

        for (Entity entity : entities) {
            if (!entity.isDestroyed()) {
                entity.tick();
                entity.tickPhysics(physicsWorld);
            }
        }

        for (Chunk chunk : chunks) {
            chunk.tick(this);
        }

        if (ambience == null) {
            Chunk playerChunk = getChunk(player.getPosition().x, player.getPosition().y);
            Biome playerBiome = playerChunk.getBiome();

            ambience = SoundHandler.playSound(playerBiome.getAmbience(playerChunk), Game.getSettings().musicVolume, true);
        }
        else {
            ambience.setVolume(Game.getSettings().musicVolume);
        }

        time += 1;
        if (time > 23999) { time = 0; }
    }

    public void physics() {
        physicsWorld.tickPhysics();
    }

    public void render(Renderer renderer) {
        Settings settings = Game.getSettings();

        if (player != null) {
            camera.setPosition(player.getPosition().x * 16, player.getPosition().y * 16);
            camera.update();
        }

        renderer.setCamera(camera);

        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;

        //* If worldSize is too small, reduce renderDistance.
        int renderDistance = 3;
        if (renderDistance > worldSize) {
            renderDistance = worldSize / 2;
        }

        for (int chunkOffsetY = -renderDistance; chunkOffsetY <= renderDistance; chunkOffsetY++) {
            for (int chunkOffsetX = -renderDistance; chunkOffsetX <= renderDistance; chunkOffsetX++) {
                float renderPosX = (int)Math.floor(playerX / chunkSize);
                float renderPosY = (int)Math.floor(playerY / chunkSize);

                renderPosX += chunkOffsetX;
                renderPosY += chunkOffsetY;

                renderPosX *= chunkSize;
                renderPosY *= chunkSize;

                Chunk chunk = getChunk(playerX + (chunkOffsetX * chunkSize), playerY + (chunkOffsetY * chunkSize));
                chunk.updateLighting(this);
                chunk.render(renderer, renderPosX, renderPosY, this, player);
            }
        }

        for (Entity entity : entities) {
            float lightLevel = getLightLevel(entity.getPosition().x, entity.getPosition().y);

            entity.render(renderer, this, new Color(lightLevel / 15, lightLevel / 15, lightLevel / 15, 1f));
        }

        Vector2 mouseWorldPosition = renderer.toWorldPosition(InputHandler.getMousePosition());

        if (settings.isInDebugMode) {
            renderer.addWorldWireframe(new Wireframe(new Box((float)Math.floor(mouseWorldPosition.x),  (float)Math.floor(mouseWorldPosition.y), 1, 1), Wireframe.WHITE));
        }
    }

    private void generateChunks() {
        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Chunk chunk = chunks[x + (y * worldSize)];
                chunk.generateFloor();
            }
        }

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Chunk chunk = chunks[x + (y * worldSize)];
                chunk.generateMountain();
            }
        }

        for (int y = 0; y < worldSize; y++) {
            for (int x = 0; x < worldSize; x++) {
                Chunk chunk = chunks[x + (y * worldSize)];
                chunk.generateDecor();
            }
        }
    }

    public Entity spawnEntity(Entity entity) {
        entities.add(entity);

        return entity;
    }

    public void dropItem(ItemStack itemStack, Vector2 worldPosition, Vector2 force) {
        EntityItem entityItem = new EntityItem(this, new Vector2(worldPosition), itemStack, 1f, force);
        spawnEntity(entityItem);
    }

    public void fillFloor(Floor floor, int x1, int y1, int x2, int y2) {
        for (int y = y1; y < y2; y++) {
            for (int x = x1; x < x2; x++) {
                setFloor(floor, x, y);
            }
        }
    }

    public final void damageBlock(BlockPosition blockPosition, int damage) {
        BlockState blockState = getBlockState(blockPosition.x, blockPosition.y);

        if (blockState != null) {
            blockState.damage(damage);

            if (blockState.health <= 0) {
                destroyBlock(blockPosition);
            }
        }
    }

    public final void destroyBlock(BlockPosition blockPosition) {
        Block block = getBlock(blockPosition);

        setBlock(null, blockPosition);

        if (block != null) {
            block.onBlockBroken(this, blockPosition);

            Random random = new Random();
            Loot loot = LootHandler.getLoot(block.getID());

            if (loot != null) {
                ArrayList<LootItem> lootItems = loot.getLootItems();
                for (LootItem lootItem : lootItems) {
                    if (lootItem != null) {
                        if (lootItem.min != lootItem.max) {
                            int amount = random.nextInt((lootItem.max - lootItem.min) + 1) + lootItem.min;

                            if (amount > 0) {
                                ItemStack itemStack = new ItemStack(lootItem.item, amount);

                                Vector2 worldPosition = blockPosition.toWorldPosition();

                                dropItem(itemStack, new Vector2(worldPosition.x + 0.5f, worldPosition.y + 0.5f), new Vector2(0, 0));
                            }
                        }
                        else {
                            ItemStack itemStack = new ItemStack(lootItem.item, lootItem.max);

                            Vector2 worldPosition = blockPosition.toWorldPosition();

                            dropItem(itemStack, new Vector2(worldPosition.x + 0.5f, worldPosition.y + 0.5f), new Vector2(0, 0));
                        }
                    }
                }
            }
        }
    }
    public final void setTime(int time) {
        if (time >= 0 && time < 24000) {
            this.time = time;
            return;
        }

        Debug.logWarning("Could not set time outside of 23999 ticks.");
    }

    public final void setBlock(Block block, int blockPosX, int blockPosY) {
        setBlock(block, new BlockPosition(blockPosX, blockPosY));
    }

    public final void setBlock(Block block, BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition);

            chunk.setBlock(block, chunkBlockPosition.x, chunkBlockPosition.y);

            if (block != null) {
                if (block.hasCeiling()) {
                    chunk.setCeiling(true, chunkBlockPosition.x, chunkBlockPosition.y);
                }
            }

            chunk.updateLighting(this);
        }
    }

    public void setFloor(Floor floor, int blockPosX, int blockPosY) {
        setFloor(floor, new BlockPosition(blockPosX, blockPosY));
    }

    public void setFloor(Floor floor, BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition);

            chunk.setFloor(floor, chunkBlockPosition.x, chunkBlockPosition.y);
        }
    }

    public final void setCeiling(boolean ceiling, int blockPosX, int blockPosY) {
        setCeiling(ceiling, new BlockPosition(blockPosX, blockPosY));
    }

    public final void setCeiling(boolean hasCeiling, BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition.x, blockPosition.y);

            chunk.setCeiling(hasCeiling, chunkBlockPosition.x, chunkBlockPosition.y);
            chunk.updateLighting(this);
        }
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public int getWorldSize() {
        return this.worldSize;
    }

    public int getTime() {
        return this.time;
    }

    public Chunk getChunk(float worldPosX, float worldPosY) {
        int minWorldSize = -(worldSize / 2);
        int maxWorldSize = (worldSize / 2) - 1;

        int chunkPosX = (int)Math.floor(worldPosX / chunkSize);
        int chunkPosY = (int)Math.floor(worldPosY / chunkSize);

        chunkPosX = UsefulMath.wrap(chunkPosX, minWorldSize, maxWorldSize);
        chunkPosY = UsefulMath.wrap(chunkPosY, minWorldSize, maxWorldSize);

        chunkPosX += worldSize / 2;
        chunkPosY += worldSize / 2;

        int index = chunkPosX + (chunkPosY * worldSize);

        return chunks[index];
    }

    public final Floor getFloor(int worldPosX, int worldPosY) {
        return getFloor(new BlockPosition(worldPosX, worldPosY));
    }

    public final Floor getFloor(BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition.x, blockPosition.y);

            return chunk.getFloor(chunkBlockPosition.x, chunkBlockPosition.y);
        }

        return null;
    }

    public final Block getBlock(int worldPosX, int worldPosY) {
        return getBlock(new BlockPosition(worldPosX, worldPosY));
    }

    public final Block getBlock(BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition.x, blockPosition.y);

            return chunk.getBlock(chunkBlockPosition.x, chunkBlockPosition.y);
        }

        return null;
    }

    public final BlockState getBlockState(int worldPosX, int worldPosY) {
        Chunk chunk = getChunk(worldPosX, worldPosY);

        if (chunk != null) {
            BlockPosition blockPosition = getChunkRelativePosition(worldPosX, worldPosY);

            return chunk.getBlockState(blockPosition.x, blockPosition.y);
        }

        return null;
    }

    public BlockState getBlockState(BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition);

            return chunk.getBlockState(chunkBlockPosition.x, chunkBlockPosition.y);
        }

        return null;
    }

    public boolean getBlockCollision(Box collider, BlockPosition blockPosition) {
        Block block = getBlock(blockPosition);

        if (block != null) {
            if (block.isCollidable()) {
                Box boundingBox = block.getCollider();
                Box blockCollider = new Box(boundingBox.x + blockPosition.x, boundingBox.y + blockPosition.y, boundingBox.width, boundingBox.height);

                if (blockCollider != null || (blockCollider.x != collider.x)) {
                    if (collider.collides(blockCollider)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public float getLightLevel(float worldPosX, float worldPosY) {
        Chunk chunk = getChunk(worldPosX, worldPosY);

        if (chunk != null) {
            BlockPosition lightPosition = getChunkRelativePosition(worldPosX, worldPosY);

            return chunk.getLightLevel(lightPosition.x, lightPosition.y);
        }

        return 0;
    }

    public BlockPosition getChunkRelativePosition(float worldPosX, float worldPosY) {
        //* Wrap position between zero and chunkSize.
        int chunkPosX = (int)Math.floor(worldPosX) % chunkSize;
        int chunkPosY = (int)Math.floor(worldPosY) % chunkSize;

        if (chunkPosX < 0) { chunkPosX += chunkSize; }
        if (chunkPosY < 0) { chunkPosY += chunkSize; }

        return new BlockPosition(chunkPosX, chunkPosY);
    }

    public BlockPosition getChunkRelativePosition(BlockPosition blockPosition) {
        //* Wrap position between zero and chunkSize.
        int chunkPosX = blockPosition.x % chunkSize;
        int chunkPosY = blockPosition.y % chunkSize;

        if (chunkPosX < 0) { chunkPosX += chunkSize; }
        if (chunkPosY < 0) { chunkPosY += chunkSize; }

        return new BlockPosition(chunkPosX, chunkPosY);
    }

    public final Entity getNearestEntity(Vector2 position, float radius) {
        Entity nearestEntity = null;
        float smallestDistance = 100f;

        ArrayList<Entity> entities = getEntities();
        for (Entity entity : entities) {
            float distance = UsefulMath.distance(new Vector2(entity.getPosition()), new Vector2(position));

            if (distance < radius && distance < smallestDistance) {
                smallestDistance = distance;
                nearestEntity = entity;

                Debug.logNormal("Entity", "Distance: " + distance);
            }
        }

        return nearestEntity;
    }

    public final float getDistance(Vector2 position1, Vector2 position2) {
        int worldOffset = chunkSize * (worldSize / 2);

        Vector2 adjustedPosition1 = new Vector2(position1.x + worldOffset, position1.y + worldOffset);
        Vector2 adjustedPosition2 = new Vector2(position2.x + worldOffset, position2.y + worldOffset);

        return UsefulMath.distance(adjustedPosition1, adjustedPosition2);
    }

    public final ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public final PhysicsWorld getPhysicsWorld() {
        return this.physicsWorld;
    }

    public final boolean hasCeiling(BlockPosition blockPosition) {
        Chunk chunk = getChunk(blockPosition.x, blockPosition.y);

        if (chunk != null) {
            BlockPosition chunkBlockPosition = getChunkRelativePosition(blockPosition.x, blockPosition.y);

            return chunk.getCeiling(chunkBlockPosition.x, chunkBlockPosition.y);
        }

        return false;
    }

    public final long getSeed() {
        return this.seed;
    }
}