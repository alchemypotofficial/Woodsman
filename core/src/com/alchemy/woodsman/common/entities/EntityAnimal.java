package com.alchemy.woodsman.common.entities;

import com.alchemy.woodsman.common.entities.brains.Brain;
import com.alchemy.woodsman.common.entities.brains.LobeSearch;
import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.utilities.physics.PhysicsBody;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class EntityAnimal extends EntityLiving {
    public Brain brain;

    private int hunger;
    private int maxHunger;
    private float hungerTimer;
    private int thirst;
    private int maxThirst;
    private float thirstTimer;

    public EntityAnimal(World world, Vector2 position) {
        super(world, position);

        setID("woodsman.animal");
        setName("Animal");
        setBody(new PhysicsBody(new Box(0f, 0f, 0.75f, 0.75f), 70f, position));
        setMaxHealth(10);
        setMaxHunger(10);
        setMaxThirst(10);

        brain = new Brain(this);
        brain.addLobe(new LobeSearch());
    }

    @Override
    public void tick() {
        super.tick();

        brain.think();

        tickHunger();
        tickThirst();
    }

    @Override
    public void render(Renderer renderer, World world, Color color) {
        super.render(renderer, world, color);

        TextureAsset playerIdleTexture = Renderer.getTexture("sprites/entities/player/Player_Idle.png");

        renderer.addWorldViewable(new Viewable(playerIdleTexture, new Box(0, 0, 48, 48), new Vector2(getPosition().x - 1.5f, getPosition().y - 1.5f), 15, 1f, color));
    }

    @Override
    public Entity spawn(World world, Vector2 position) {
        return new EntityAnimal(world, position);
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

    protected final void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;

        hunger = maxHunger;
    }

    protected final void setMaxThirst(int maxThirst) {
        this.maxThirst = maxThirst;

        thirst = maxThirst;
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
}
