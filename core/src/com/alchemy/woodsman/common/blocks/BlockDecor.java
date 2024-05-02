package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class BlockDecor extends Block {

    public BlockDecor(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        setOccludable(false);
    }

    public void render(Renderer renderer, World world, BlockPosition blockPosition, Color color) {
        Settings settings = Game.getSettings();

        if (getMainTexture() != null) {
            renderer.addWorldViewable(new Viewable(getMainTexture(), blockPosition.toWorldPosition(), 10, color));
        }

        if (settings.isHitboxShown && isCollidable()) {
            Box blockCollider = new Box(getCollider().x + blockPosition.x, getCollider().y + blockPosition.y, getCollider().width, getCollider().height);

            renderer.addWorldWireframe(new Wireframe(blockCollider, Wireframe.RED));
        }
    }

    @Override
    public boolean onInteract(World world, EntityPlayer player, BlockPosition blockPosition) {
        world.destroyBlock(blockPosition);

        return true;
    }
}
