package com.alchemy.woodsman.common.blockstates;

import com.alchemy.woodsman.core.graphics.Animation;

public class BlockState {

    public int health;
    public int maxHealth;

    public BlockState(int maxHealth) {
        this.maxHealth = maxHealth;

        health = maxHealth;
    }

    public final void damage(int damage) {
        health -= damage;

        if (health < 0) {
            health = 0;
        }
    }

    public final int getHealth() {
        return this.health;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

}