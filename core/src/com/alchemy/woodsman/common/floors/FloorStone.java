package com.alchemy.woodsman.common.floors;

import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class FloorStone extends Floor {

    public FloorStone(String id, String texturePath) {
        super(id, texturePath);
    }

    @Override
    public void render(Renderer renderer, World world, Vector2 displayPosition, BlockPosition blockPosition, Color color) {
        int floorSeed = (blockPosition.x * 10000 + blockPosition.y) * 2000;
        Random random = new Random(floorSeed);

        int floorValue = random.nextInt(4);

        renderer.addWorldViewable(new Viewable(mainTexture, new Box(floorValue * 16, 0, 16, 16), displayPosition, 1, color));
    }
}