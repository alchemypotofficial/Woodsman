package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class BlockTree extends Block {
    public BlockTree(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        setOccludable(true);
        setOccluder(new Box(-1f, 0.75f, 3, 3));

        addBlockTags(new BlockTag[]{ Tags.DESTROYABLE_AXE });
    }

    @Override
    public void render(Renderer renderer, World world, BlockPosition blockPosition, Color color) {
        Settings settings = Game.getSettings();

        if (getMainTexture() != null) {
            renderer.addWorldViewable(new Viewable(getMainTexture(), new Vector2(blockPosition.x - 1f, blockPosition.y), 15, color));
        }

        if (settings.isHitboxShown && isCollidable()) {
            Box blockCollider = new Box(getCollider().x + blockPosition.x, getCollider().y + blockPosition.y, getCollider().width, getCollider().height);

            renderer.addWorldWireframe(new Wireframe(blockCollider, Wireframe.RED));
        }
    }
}
