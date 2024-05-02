package com.alchemy.woodsman.common.entities.brains;

import com.alchemy.woodsman.common.entities.Entity;

import java.util.ArrayList;

public class Brain {
    private Entity entity;
    private ArrayList<Lobe> lobes;

    public Brain(Entity entity) {
        this.entity = entity;

        lobes = new ArrayList<Lobe>();
    }

    public final void think() {
        for (Lobe lobe : lobes) {
            lobe.action(this);
        }
    }

    public final void addLobe(Lobe lobe) {
        lobes.add(lobe);
    }

    public final Entity getEntity() {
        return this.entity;
    }
}
