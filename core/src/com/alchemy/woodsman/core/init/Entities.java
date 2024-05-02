package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityAnimal;
import com.alchemy.woodsman.core.graphics.data.Registry;
import com.badlogic.gdx.math.Vector2;

public class Entities {

    public static final Registry<Entity> ENTITIES = new Registry<Entity>();

    public static final EntityAnimal ENTITY_COW = new EntityAnimal(null, Vector2.Zero);

    public static void register() {
        ENTITIES.register(ENTITY_COW);
    }
}
