package com.alchemy.woodsman.common.entities;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.Container;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.physics.PhysicsBody;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class EntityPlayer extends EntityLiving {

    private int hunger;
    private int maxHunger;
    private float hungerTimer;
    private int thirst;
    private int maxThirst;
    private float thirstTimer;

    private int reach;

    private float itemUseTimer;
    private Item usedItem;

    private boolean isInMenu;
    private boolean walking;
    private int selectedSlot;
    private Vector2 spawnPoint;

    private Container inventory;
    private Container equipment;

    private Animator animator;

    public EntityPlayer(World world, Vector2 position) {
        super(world, position);

        spawnPoint = new Vector2(position.x, position.y);

        setID("woodsman.player");
        setName("Player");
        setBody(new PhysicsBody(new Box(0f, 0f, 0.75f, 0.75f), 40f, position));
        setMaxHealth(20);
        setMaxHunger(20);
        setMaxThirst(20);
        body.friction = 1f;

        animator = new Animator();

        inventory = new Container(8);
        equipment = new Container(5);

        reach = 5;

        selectedSlot = 0;
        usedItem = null;
    }

    @Override
    public void tick() {
        super.tick();

        inventory.update();

        tickHunger();
        tickThirst();

        if (itemUseTimer > 0) {
            itemUseTimer -= Game.getDeltaTime();
            if (itemUseTimer < 0) {
                itemUseTimer = 0;
                usedItem = null;
            }
        }
    }

    @Override
    public void render(Renderer renderer, World world, Color color) {
        super.render(renderer, world, color);

        //* Player idle animations.
        Animation playerIdleUp = new Animation("playerIdleUp", Renderer.getTexture("sprites/entities/player/Player_Idle.png"), 48, 48, 0, new float[]{ 0.25f, 0.25f, 0.25f, 0.25f });
        Animation playerIdleDown = new Animation("playerIdleDown", Renderer.getTexture("sprites/entities/player/Player_Idle.png"), 48, 48, 1, new float[]{ 0.25f, 0.25f, 0.25f, 0.25f });
        Animation playerIdleLeft = new Animation("playerIdleLeft", Renderer.getTexture("sprites/entities/player/Player_Idle.png"), 48, 48, 2, new float[]{ 0.25f, 0.25f, 0.25f, 0.25f });
        Animation playerIdleRight = new Animation("playerIdleRight", Renderer.getTexture("sprites/entities/player/Player_Idle.png"), 48, 48, 3, new float[]{ 0.25f, 0.25f, 0.25f, 0.25f });

        //* Player walking animations.
        Animation playerWalkUp = new Animation("playerWalkUp", Renderer.getTexture("sprites/entities/player/Player_Walk.png"), 48, 48, 0, new float[]{ 0.125f, 0.125f, 0.125f, 0.125f });
        Animation playerWalkDown = new Animation("playerWalkDown", Renderer.getTexture("sprites/entities/player/Player_Walk.png"), 48, 48, 1, new float[]{ 0.125f, 0.125f, 0.125f, 0.125f });
        Animation playerWalkLeft = new Animation("playerWalkLeft", Renderer.getTexture("sprites/entities/player/Player_Walk.png"), 48, 48, 2, new float[]{ 0.125f, 0.125f, 0.125f, 0.125f });
        Animation playerWalkRight = new Animation("playerWalkRight", Renderer.getTexture("sprites/entities/player/Player_Walk.png"), 48, 48, 3, new float[]{ 0.125f, 0.125f, 0.125f, 0.125f });

        Direction direction = getDirection();

        if (usedItem != null) {
            TextureAsset useAnimationTexture = usedItem.getUseAnimation();
            if (useAnimationTexture != null) {
                Animation playerUseUp = new Animation("playerUseUp", useAnimationTexture, 48, 48, 0, new float[]{ 0.125f, 0.125f, 0.125f, 0.625f });
                Animation playerUseDown = new Animation("playerUseDown", useAnimationTexture, 48, 48, 1, new float[]{ 0.125f, 0.125f, 0.125f, 0.625f });
                Animation playerUseLeft = new Animation("playerUseLeft", useAnimationTexture, 48, 48, 2, new float[]{ 0.125f, 0.125f, 0.125f, 0.625f });
                Animation playerUseRight = new Animation("playerUseRight", useAnimationTexture, 48, 48, 3, new float[]{ 0.125f, 0.125f, 0.125f, 0.625f });

                switch (direction) {
                    case UP -> animator.setAnimation(playerUseUp, true);
                    case DOWN -> animator.setAnimation(playerUseDown, true);
                    case LEFT -> animator.setAnimation(playerUseLeft, true);
                    case RIGHT -> animator.setAnimation(playerUseRight, true);
                }
            }
        }
        else if (isWalking()) {
            switch (direction) {
                case UP -> animator.setAnimation(playerWalkUp, true);
                case DOWN -> animator.setAnimation(playerWalkDown, true);
                case LEFT -> animator.setAnimation(playerWalkLeft, true);
                case RIGHT -> animator.setAnimation(playerWalkRight, true);
            }
        }
        else {
            switch (direction) {
                case UP -> animator.setAnimation(playerIdleUp, true);
                case DOWN -> animator.setAnimation(playerIdleDown, true);
                case LEFT -> animator.setAnimation(playerIdleLeft, true);
                case RIGHT -> animator.setAnimation(playerIdleRight, true);
            }
        }

        Frame frame = animator.animate();
        renderer.addWorldViewable(new Viewable(frame, new Vector2(getPosition().x - 1.5f, getPosition().y - 1.5f), 15, 1f, color, 1f));

        TextureAsset shadowTexture = Renderer.getTexture("sprites/entities/Shadow.png");
        renderer.addWorldViewable(new Viewable(shadowTexture, new Vector2(getPosition().x - 0.5f, getPosition().y - 0.5f), 14, new Color(color.r, color.g, color.b, 0.25f)));
    }

    @Override
    public void onDeath() {
        isInMenu = true;
        Menus.MENU_DEATH.setPlayer(this);
        MenuHandler.focus(Menus.MENU_DEATH);
    }

    public void tickHunger() {
        if (hunger > 0) {
            hungerTimer += 1;
            if (hungerTimer > 8400) {
                hungerTimer = 0;
                famish(1);
            }
        }
        else {
            hungerTimer += 1;
            if (hungerTimer > 100) {
                hungerTimer = 0;
                hurt(1);
            }
        }
    }

    public void tickThirst() {
        if (thirst > 0) {
            thirstTimer += 1;
            if (thirstTimer > 3600) {
                thirstTimer = 0;
                parch(1);
            }
        }
        else {
            thirstTimer += 1;
            if (thirstTimer > 100) {
                thirstTimer = 0;
                hurt(1);
            }
        }
    }

    public final void respawn() {
        if (isDead()) {
            revive();

            hunger = maxHunger;
            thirst = maxThirst;
            isInMenu = false;
            setPosition(new Vector2(spawnPoint.x, spawnPoint.y));
        }
    }

    public final ItemStack getSelectedItemStack() {
        return inventory.getItemStack(selectedSlot);
    }

    public final void scrollHotbarLeft() {
        selectedSlot -= 1;

        if (selectedSlot < 0) {
            selectedSlot = 7;
        }
    }

    public final void scrollHotbarRight() {
        selectedSlot += 1;

        if (selectedSlot > 7) {
            selectedSlot = 0;
        }
    }

    public final void useItem(Item item) {
        if (itemUseTimer <= 0) {
            itemUseTimer = item.getUseTime();
            usedItem = item;
        }
    }

    public final void famish(int hunger) {
        if (this.hunger - hunger <= 0) {
            this.hunger = 0;
        }
        else {
            this.hunger -= hunger;
        }
    }

    public final void parch(int thirst) {
        if (this.thirst - thirst <= 0) {
            this.thirst = 0;
        }
        else {
            this.thirst -= thirst;
        }
    }

    public final void feed(int hunger) {
        if (this.hunger + hunger >= maxHunger) {
            this.hunger = maxHunger;
        }
        else {
            this.hunger += hunger;
        }
    }

    public final void drink(int thirst) {
        if (this.thirst + thirst >= maxThirst) {
            this.thirst = maxThirst;
        }
        else {
            this.thirst += thirst;
        }
    }

    public final void openMenu() {
        this.isInMenu = true;
    }

    public final void closeMenu() {
        this.isInMenu = false;
    }

    protected final void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;

        hunger = maxHunger;
    }

    protected final void setMaxThirst(int maxThirst) {
        this.maxThirst = maxThirst;

        thirst = maxThirst;
    }

    public final void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;

        if (selectedSlot < 0) {
            selectedSlot = 7;
        }
        else if (selectedSlot > 7) {
            selectedSlot = 0;
        }
    }

    public final void setWalking(boolean walking) {
        this.walking = walking;
    }

    public final int getSelectedSlot() {
        return this.selectedSlot;
    }

    public final int getHunger() {
        return this.hunger;
    }

    public final int getMaxHunger() {
        return this.maxHunger;
    }

    public final int getThirst() {
        return this.thirst;
    }

    public final int getMaxThirst() {
        return this.maxThirst;
    }

    public final int getReach() {
        return this.reach;
    }

    public final Container getInventory() {
        return this.inventory;
    }

    public final Container getEquipment() {
        return this.equipment;
    }

    public final boolean isInMenu() {
        return this.isInMenu;
    }

    public final boolean isWalking() {
        return this.walking;
    }

    public final boolean isUsingItem() {
        if (itemUseTimer != 0) {
            return true;
        }

        return false;
    }
}