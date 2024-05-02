package com.alchemy.woodsman.common.entities;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.physics.PhysicsBody;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class EntityItem extends Entity {

    private ItemStack itemStack;
    private float dropTime;

    private float height;
    private float heightVelocity;

    public EntityItem(World world, Vector2 position, ItemStack itemStack, float height, Vector2 force) {
        super(world, position);

        this.itemStack = itemStack;
        this.height = height;

        setID("woodsman.item");
        setName("Item");
        setBody(new PhysicsBody(new Box(0, 0, 0.75f, 0.75f), 4, position));
        body.hasBodyCollision = false;
        body.friction = 0.5f;

        addForce(force);

        heightVelocity = 0f;
    }

    @Override
    public void tick() {
        //* Check if player is colliding and add item to inventory.
        if (itemStack == null || itemStack.getAmount() < 0) {
            destroy();
        }
        else {
            if (dropTime > 2f) {
                ArrayList<Entity> entities = getWorld().getEntities();
                for (Entity entity : entities) {
                    if (entity instanceof EntityPlayer player) {
                        Box playerCollider = player.getCollider();

                        if (playerCollider.collides(body.getCollider())) {
                            int oldAmount = itemStack.getAmount();
                            this.itemStack = player.getInventory().addItemStack(itemStack);

                            if (itemStack == null || itemStack.getAmount() != oldAmount) {
                                Random random = new Random(Game.getSystemTime());
                                int pickupSoundIndex = random.nextInt(3);

                                if (pickupSoundIndex == 0) {
                                    SoundAsset pickupSound = SoundHandler.getSound("sounds/system/Pickup_1.wav");
                                    SoundHandler.playSound(pickupSound, Game.getSettings().effectsVolume, false);
                                }
                                else if (pickupSoundIndex == 1) {
                                    SoundAsset pickupSound = SoundHandler.getSound("sounds/system/Pickup_2.wav");
                                    SoundHandler.playSound(pickupSound, Game.getSettings().effectsVolume, false);
                                }
                                else {
                                    SoundAsset pickupSound = SoundHandler.getSound("sounds/system/Pickup_3.wav");
                                    SoundHandler.playSound(pickupSound, Game.getSettings().effectsVolume, false);
                                }
                            }

                            if (this.itemStack == null) {
                                destroy();
                            }
                        }
                    }
                }
            }
            else {
                dropTime += Game.getDeltaTime();
            }
        }

        //* Bounce item.
        if (height > 0f) {
            heightVelocity += -0.75f * Game.getDeltaTime();
        }
        else {
            height = 0f;

            if (heightVelocity > 0.03f || heightVelocity < -0.03f) {
                heightVelocity = -heightVelocity * 0.5f;
            }
            else {
                heightVelocity = 0f;
            }
        }

        //* If heightVelocity is low enough, reduce it to 0.
        if (heightVelocity > -0.001f && heightVelocity < 0.001f) {
            heightVelocity = 0f;
        }

        //* Adjust height with velocity.
        if (heightVelocity != 0f) {
            height += heightVelocity;
        }
    }

    @Override
    public void render(Renderer renderer, World world, Color color) {
        Settings settings = Game.getSettings();

        super.render(renderer, world, color);

        if (itemStack != null) {
            Vector2 itemPosition = new Vector2(getPosition().x - 0.375f, getPosition().y - 0.375f + height);
            renderer.addWorldViewable(new Viewable(itemStack.getItem().getTexture(), itemPosition, 15, 0.75f, color));
        }

        TextureAsset shadowTexture = Renderer.getTexture("sprites/Entities/Shadow_Small.png");
        renderer.addWorldViewable(new Viewable(shadowTexture, new Vector2(getPosition().x - 0.5f, getPosition().y - 0.5f), 14, new Color(color.r, color.g, color.b, 0.25f)));

        if (settings.isHitboxShown) {
            renderer.addWorldWireframe(new Wireframe(body.getCollider(), Wireframe.GREEN));
        }
    }

    @Override
    public void renderInfo(Renderer renderer, World world) {
        if (itemStack != null) {
            renderer.addMenuText(new Text(getName(), new Vector2(280, 200), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("(" + getID() + ")", new Vector2(280, 176), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Position: " + MessageHandler.formatVector(getPosition()), new Vector2(280, 152), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Item: " + itemStack.getItem().getName(), new Vector2(280, 128), 100, 1f, Renderer.WHITE, Renderer.BLACK));
            renderer.addMenuText(new Text("Amount: " + itemStack.getAmount(), new Vector2(280, 102), 100, 1f, Renderer.WHITE, Renderer.BLACK));
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
