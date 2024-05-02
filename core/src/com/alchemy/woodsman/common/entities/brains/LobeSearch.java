package com.alchemy.woodsman.common.entities.brains;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.core.utilities.UsefulMath;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

public class LobeSearch extends Lobe {

    @Override
    public void action(Brain brain) {
        Entity entity = brain.getEntity();
        World world = entity.getWorld();

        if (world.getPlayer() != null) {
            Vector2 direction = UsefulMath.direction(entity.getPosition(), world.getPlayer().getPosition());
            float distance = UsefulMath.distance(entity.getPosition(), world.getPlayer().getPosition());

            if (distance > 2f && distance < 5f) {
                entity.addForce(new Vector2(direction.x * 120f, direction.y * 120f));
            }
        }
    }
}
