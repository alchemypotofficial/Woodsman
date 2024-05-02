package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.core.graphics.data.Registerable;

public class Tier extends Registerable {

    private int damage;
    private int durability;
    private float speed;

    public Tier(String id, int damage, int durability, float speed) {
        super(id);

        this.damage = damage;
        this.durability = durability;
        this.speed = speed;
    }

    public final int getDamage() {
        return this.damage;
    }

    public final int getDurability() {
        return this.durability;
    }

    public final float getSpeed() {
        return this.speed;
    }
}
