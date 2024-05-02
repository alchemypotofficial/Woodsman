package com.alchemy.woodsman.common.floors;

import com.alchemy.woodsman.core.graphics.*;
import com.alchemy.woodsman.core.graphics.data.DirectionMap;
import com.alchemy.woodsman.core.graphics.data.RuleTexture;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class FloorWater extends Floor {

    public FloorWater(String id, String texturePath) {
        super(id, texturePath);
    }

    @Override
    public void render(Renderer renderer, World world, Vector2 displayPosition, BlockPosition blockPosition, Color color) {
        renderer.addWorldViewable(new Viewable(mainTexture, new Box(0, 0, 16, 16), displayPosition, -5, color));
    }
}
