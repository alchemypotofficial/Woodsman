package com.alchemy.woodsman.core.screens;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityLiving;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Blocks;
import com.alchemy.woodsman.core.init.Floors;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.handlers.CommandHandler;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.utilities.*;
import com.alchemy.woodsman.core.world.Chunk;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ScreenGame extends Screen {

    private CommandHandler commandHandler;

    private World world;
    private EntityPlayer player;

    private boolean upHeld;
    private boolean downHeld;
    private boolean leftHeld;
    private boolean rightHeld;

    private boolean shiftHeld;

    private int worldSize = 8;
    private int chunkSize = 16;

    public ScreenGame(Renderer renderer) {
        super(renderer);

        commandHandler = new CommandHandler();
    }

    public void start() {
        world = new World(worldSize, chunkSize);
        player = world.getPlayer();

        commandHandler.setWorld(world);
    }

    public void tick() {
        int tickrate = 1;
        for (int tickNumber = 0; tickNumber < tickrate; tickNumber++) {
            world.tick();
        }
    }

    public void physics() {
        if (world != null) {
            player.setWalking(false);

            if (!player.isDead() && !player.isInMenu() && !player.isUsingItem()) {
                if (rightHeld && !leftHeld) {
                    if (shiftHeld) {
                        player.addForce(new Vector2(160, 0));
                    }
                    else {
                        player.addForce(new Vector2(120, 0));
                    }

                    player.setWalking(true);
                    player.setDirection(EntityLiving.Direction.RIGHT);
                }
                else if (leftHeld && !rightHeld) {
                    if (shiftHeld) {
                        player.addForce(new Vector2(-160, 0));
                    }
                    else {
                        player.addForce(new Vector2(-120, 0));
                    }

                    player.setWalking(true);
                    player.setDirection(EntityLiving.Direction.LEFT);
                }

                if (upHeld && !downHeld) {
                    if (shiftHeld) {
                        player.addForce(new Vector2(0, 160));
                    }
                    else {
                        player.addForce(new Vector2(0, 120));
                    }

                    player.setWalking(true);
                    player.setDirection(EntityLiving.Direction.UP);
                }
                else if (downHeld && !upHeld) {
                    if (shiftHeld) {
                        player.addForce(new Vector2(0, -160));
                    }
                    else {
                        player.addForce(new Vector2(0, -120));
                    }

                    player.setWalking(true);
                    player.setDirection(EntityLiving.Direction.DOWN);
                }
            }

            world.getPhysicsWorld().tickPhysics();
        }
    }

    public void render(Renderer renderer) {
        Settings settings = Game.getSettings();

        world.render(renderer);

        Vector2 worldPosition = renderer.toWorldPosition(InputHandler.getMousePosition());
        worldPosition.x = (float)Math.floor(worldPosition.x);
        worldPosition.y = (float)Math.floor(worldPosition.y);

        //* Show version number if in Alpha or Beta.
        renderer.addMenuText(new Text("Alpha Version " + Game.getVersion(), new Vector2(300, 240), 100));

        //* Display world map for debugging.
        if (settings.isInDebugMode) {
            int worldBlockSize = worldSize * chunkSize;

            Pixmap pixmap = new Pixmap(worldBlockSize, worldBlockSize, Pixmap.Format.RGBA8888);

            for (int y = 0; y < worldBlockSize; y++) {
                for (int x = 0; x < worldBlockSize; x++) {
                    BlockPosition blockPosition = new BlockPosition(x - (worldBlockSize / 2), y - (worldBlockSize / 2));
                    Floor floor = world.getFloor(blockPosition);
                    Block block = world.getBlock(blockPosition);

                    if (floor == Floors.FLOOR_CLAY) {
                        pixmap.setColor(Renderer.DARK_GREEN);
                    }
                    else if (block == Blocks.BLOCK_CHERT_STONE) {
                        pixmap.setColor(Renderer.DARK_GREY);
                    }
                    else if (floor == Floors.FLOOR_GRASS) {
                        pixmap.setColor(Renderer.GREEN);
                    }
                    else if (floor == Floors.FLOOR_SAND) {
                        pixmap.setColor(Renderer.YELLOW);
                    }
                    else {
                        pixmap.setColor(Renderer.BLUE);
                    }

                    pixmap.drawPixel(x, worldBlockSize - y);
                }
            }

            pixmap.setColor(Renderer.RED);

            BlockPosition playerPosition = new BlockPosition(player.getPosition());
            playerPosition.x += (worldBlockSize / 2);
            playerPosition.y += (worldBlockSize / 2);

            pixmap.drawPixel(playerPosition.x, worldBlockSize - playerPosition.y);
            pixmap.drawPixel(playerPosition.x, worldBlockSize - playerPosition.y - 1);
            pixmap.drawPixel(playerPosition.x, worldBlockSize - playerPosition.y + 1);
            pixmap.drawPixel(playerPosition.x - 1, worldBlockSize - playerPosition.y);
            pixmap.drawPixel(playerPosition.x + 1, worldBlockSize - playerPosition.y);

            renderer.addMenuViewable(new Viewable(new TextureAsset("woodsman.map", new Texture(pixmap)), new Vector2(275, -240), 30));
        }

        if (!player.isInMenu()) {
            //* Show Held ItemStack render when it is hovered.
            EntityPlayer player = world.getPlayer();
            ItemStack selectedItemStack = player.getInventory().getItemStack(player.getSelectedSlot());
            if (selectedItemStack != null) {
                selectedItemStack.getItem().renderHovered(renderer, world, player, InputHandler.getMousePosition());
            }

            //* TODO: Show gestures.
            BlockPosition blockPosition = renderer.toBlockPosition(InputHandler.getMousePosition());
            Block block = world.getBlock(blockPosition);

            if (block != null) {
                Gesture gesture = block.getGesture();

                if (gesture != null) {
                    Vector2 mousePosition = renderer.toMenuPosition(InputHandler.getMousePosition());
                    Vector2 gesturePosition = new Vector2(mousePosition.x + 4f, mousePosition.y - 22f);

                    renderer.addMenuViewable(new Viewable(gesture.getTexture(), gesturePosition, 30));
                }
            }
        }

        if (settings.isInDebugMode) {
            DecimalFormat decimalFormat = renderer.getDecimalFormat();

            Vector2 mouseWorldPosition = renderer.toWorldPosition(InputHandler.getMousePosition());

            float playerX = player.getPosition().x;
            float playerY = player.getPosition().y;
            BlockPosition mouseBlockPosition = new BlockPosition(mouseWorldPosition);

            Chunk chunk = world.getChunk(playerX, playerY);

            renderer.addMenuText(new Text("FPS: " + Game.getFPS(), new Vector2(-432, 240), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Position: " + MessageHandler.formatVector(player.getPosition()), new Vector2(-432, 216), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Chunk: " + MessageHandler.formatVector(chunk.position.toWorldPosition()), new Vector2(-432, 192), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Block Position: " + MessageHandler.formatVector(mouseBlockPosition.toWorldPosition()), new Vector2(-432, 168), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Light Level: " + decimalFormat.format(world.getLightLevel(mouseWorldPosition.x, mouseBlockPosition.y)), new Vector2(-432, 144), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Time: " + world.getTime(), new Vector2(-432, 120), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Entities: " + world.getEntities().size(), new Vector2(-432, 96), 100, 1f, Renderer.WHITE, Renderer.BLACK));

            renderer.addMenuText(new Text("Hunger: " + player.getHunger(), new Vector2(-432, 72), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Thirst: " + player.getThirst(), new Vector2(-432, 48), 100, 1f, Renderer.WHITE, Renderer.BLACK));

            renderer.addMenuText(new Text("Velocity X: " + decimalFormat.format(player.getVelocity().x), new Vector2(-432, -24), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Velocity Y: " + decimalFormat.format(player.getVelocity().y), new Vector2(-432, -48), 100, 1f, Renderer.WHITE, Renderer.BLACK));

            Block Block = world.getBlock(mouseBlockPosition);
            if (Block != null) {
                renderer.addMenuText(new Text("Block: " + Block.getID(), new Vector2(-432, -72), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            }

            Floor floor = world.getFloor(mouseBlockPosition);
            if (floor != null) {
                renderer.addMenuText(new Text("Floor: " + floor.getID(), new Vector2(-432, -96), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            }

            ArrayList<Entity> entities = world.getEntities();
            for (Entity entity : entities) {
                if (entity.getCollider().isPointWithin(mouseWorldPosition)) {
                    entity.renderInfo(renderer, world);

                    break;
                }
            }
        }
    }

    @Override
    public void onMouseLeftDown(Vector2 clickPosition) {
        if (!player.isDead() && !player.isInMenu() && !player.isUsingItem()) {
            BlockPosition blockPosition = renderer.toBlockPosition(clickPosition);
            ItemStack itemStack = player.getSelectedItemStack();

            if (itemStack != null) {
                Vector2 worldPosition = renderer.toWorldPosition(clickPosition);

                itemStack.getItem().onPrimary(itemStack, world, player, worldPosition);
            }
        }
    }

    @Override
    public void onMouseRightDown(Vector2 clickPosition) {
        if (!player.isDead() && !player.isInMenu() && !player.isUsingItem()) {
            Vector2 worldPosition = renderer.toWorldPosition(clickPosition);
            BlockPosition blockPosition = renderer.toBlockPosition(clickPosition);

            Block clickedBlock = world.getBlock(blockPosition);
            if (clickedBlock != null) {
                if (clickedBlock.onInteract(world, player, blockPosition) == false) {
                    ItemStack itemStack = player.getSelectedItemStack();

                    if (itemStack != null) {
                        itemStack.getItem().onSecondary(itemStack, world, player, worldPosition);
                    }
                }
            }
            else {
                ItemStack itemStack = player.getSelectedItemStack();

                if (itemStack != null) {
                    itemStack.getItem().onSecondary(itemStack, world, player, worldPosition);
                }
            }
        }
    }

    @Override
    public void onButtonDown(InputHandler.Button button) {
        Settings settings = Game.getSettings();

        if (button == InputHandler.Cancel) {
            if (MenuHandler.hasFocus(Menus.MENU_HOTBAR)) {
                Menus.MENU_PAUSE.setPlayer(player);
                MenuHandler.focus(Menus.MENU_PAUSE);

                player.openMenu();
            }
            else if (MenuHandler.hasFocus(Menus.MENU_PAUSE)) {
                Menus.MENU_HOTBAR.setPlayer(player);
                MenuHandler.focus(Menus.MENU_HOTBAR);

                player.closeMenu();
            }
        }

        if (player != null) {
            if (button == InputHandler.DebugMode) {
                settings.isInDebugMode = !settings.isInDebugMode;
            }
            else if (button == InputHandler.ShowHitboxes) {
                settings.isHitboxShown = !settings.isHitboxShown;
            }
            else if (button == InputHandler.ShowChunkBorder) {
                settings.isChunkBorderShown = !settings.isChunkBorderShown;
            }
            else if (button == InputHandler.ZoomOut) {
                if (!settings.isMaximized) {
                    world.getCamera().setZoom(2f);
                    settings.isMaximized = true;
                }
                else {
                    world.getCamera().setZoom(0.5f);
                    settings.isMaximized = false;
                }
            }

            if (button == InputHandler.TakeScreenshot) {
                renderer.takeScreenshot();
            }

            if (button == InputHandler.Up) {
                upHeld = true;
            }
            else if (button == InputHandler.Down) {
                downHeld = true;
            }
            else if (button == InputHandler.Left) {
                leftHeld = true;
            }
            else if (button == InputHandler.Right) {
                rightHeld = true;
            }

            if (button == InputHandler.Shift) {
                shiftHeld = true;
            }

            if (!player.isDead() && !player.isUsingItem()) {
                if (button == InputHandler.Inventory) {
                    if (MenuHandler.hasFocus(Menus.MENU_HOTBAR) && !MenuHandler.isShown(Menus.MENU_MESSAGE_BAR)) {
                        player.openMenu();

                        Menus.MENU_INVENTORY.setPlayer(player);
                        MenuHandler.focus(Menus.MENU_INVENTORY);
                    }
                    else if (!MenuHandler.hasFocus(Menus.MENU_HOTBAR) && !MenuHandler.isShown(Menus.MENU_MESSAGE_BAR)){
                        player.closeMenu();

                        Menus.MENU_HOTBAR.setPlayer(player);
                        MenuHandler.focus(Menus.MENU_HOTBAR);
                    }
                }

                if (button == InputHandler.Enter) {
                    if (MenuHandler.hasFocus(Menus.MENU_HOTBAR)) {
                        if (MenuHandler.isShown(Menus.MENU_MESSAGE_BAR)) {
                            player.closeMenu();

                            MenuHandler.hide(Menus.MENU_MESSAGE_BAR);
                        }
                        else {
                            player.openMenu();

                            MenuHandler.show(Menus.MENU_MESSAGE_BAR);
                            MenuHandler.show(Menus.MENU_MESSAGE_LIST);
                        }
                    }
                }
            }

            if (!player.isDead() && !player.isInMenu() && !player.isUsingItem()) {
                if (button == InputHandler.One) {
                    player.setSelectedSlot(0);
                }
                else if (button == InputHandler.Two) {
                    player.setSelectedSlot(1);
                }
                else if (button == InputHandler.Three) {
                    player.setSelectedSlot(2);
                }
                else if (button == InputHandler.Four) {
                    player.setSelectedSlot(3);
                }
                else if (button == InputHandler.Five) {
                    player.setSelectedSlot(4);
                }
                else if (button == InputHandler.Six) {
                    player.setSelectedSlot(5);
                }
                else if (button == InputHandler.Seven) {
                    player.setSelectedSlot(6);
                }
                else if (button == InputHandler.Eight) {
                    player.setSelectedSlot(7);
                }

                if (button == InputHandler.Drop) {
                    if (player != null) {
                        if (MenuHandler.hasFocus(Menus.MENU_HOTBAR)) {
                            ItemStack selectedItemStack = player.getSelectedItemStack();

                            if (selectedItemStack != null) {
                                Vector2 mousePosition = renderer.toMenuPosition(InputHandler.getMousePosition());

                                float regionX = Math.abs(mousePosition.x);
                                float regionY = Math.abs(mousePosition.y) + 25f;

                                if (regionX > regionY) {
                                    if (mousePosition.x < 0) {
                                        player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(-300f, 0));
                                    }
                                    else {
                                        player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(300f, 0));
                                    }
                                }
                                else if (regionY > regionX) {
                                    if (mousePosition.y > 0) {
                                        player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(0, 300f));
                                    }
                                    else {
                                        player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(0, -300f));
                                    }
                                }
                                else {
                                    player.drop(new ItemStack(selectedItemStack.getItem(), 1), new Vector2(0, 300f));
                                }

                                selectedItemStack.decrease(1);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onButtonUp(InputHandler.Button button) {
        if (button == InputHandler.Up) {
            upHeld = false;
        }
        else if (button == InputHandler.Down) {
            downHeld = false;
        }
        else if (button == InputHandler.Left) {
            leftHeld = false;
        }
        else if (button == InputHandler.Right) {
            rightHeld = false;
        }

        if (button == InputHandler.Shift) {
            shiftHeld = false;
        }
    }

    @Override
    public void onScroll(int direction) {
        if (!player.isDead() && !player.isDead()) {
            if (direction > 0) {
                player.scrollHotbarRight();
            }
            else if (direction < 0) {
                player.scrollHotbarLeft();
            }
        }
    }

    private boolean hasControl() {
        if (!player.isDead() && !MenuHandler.isFocused()) {
            return true;
        }

        return false;
    }
}
