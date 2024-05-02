package com.alchemy.woodsman.common.entities;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EntityLiving extends Entity {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private int health;
    private int maxHealth;
    private float hurtTimer;
    private Direction direction;
    private boolean isDead;

    public EntityLiving(World world, Vector2 position) {
        super(world, position);

        setID("woodsman.living");

        setMaxHealth(10);

        isDead = false;
        health = maxHealth;

        direction = Direction.DOWN;
    }

    @Override
    public void tick() {
        if (hurtTimer > 0f) {
            hurtTimer -= Game.getDeltaTime();

            if (hurtTimer < 0f) {
                hurtTimer = 0f;
            }
        }
    }

    @Override
    public Entity spawn(World world, Vector2 position) {
        return new EntityLiving(world, position);
    }

    public void onDeath() {
        destroy();
    }

    public final void hurt(int damage) {
        if (hurtTimer == 0) {
            if (health - damage < 0) {
                health = 0;
            }
            else {
                health -= damage;
                hurtTimer = 1f;
            }

            if (health == 0) {
                isDead = true;
                onDeath();
            }
        }
    }

    public final void kill() {
        isDead = true;
        health = 0;

        onDeath();
    }

    public final void revive() {
        isDead = false;
        health = maxHealth;
    }

    public final void attack(Box collider, int damage) {
        ArrayList<Entity> entities = getWorld().getEntities();
        for (Entity entity : entities) {
            if (entity.getCollider().collides(collider) && entity instanceof EntityLiving) {
                ((EntityLiving) entity).hurt(damage);
            }
        }
    }

    protected final void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;

        health = maxHealth;
    }

    public final void setDirection(Direction direction) {
        this.direction = direction;
    }

    public final int getHealth() {
        return this.health;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    public final Direction getDirection() {
        return this.direction;
    }

    public final boolean isDead() {
        return this.isDead;
    }
}
