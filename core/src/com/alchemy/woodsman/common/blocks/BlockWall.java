package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.init.Tags;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class  BlockWall extends Block {

    public BlockWall(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id, maxHealth, collider, isCollidable, hasCeiling, texturePath);

        setMainTexture(Renderer.getTexture(texturePath));

        setOccludable(true);
        setOccluder(new Box(-1f, 1f, 3f, 2f));

        addBlockTags(new BlockTag[]{ Tags.DESTROYABLE_PICKAXE });
    }

    public void render(Renderer renderer, World world, BlockPosition blockPosition, boolean isOccluding, float lightLevel, Color tint) {
        Settings settings = Game.getSettings();

        TextureAsset chertWall3 = Renderer.getTexture("sprites/blocks/Block_Chert_Wall_3.png");
        TextureAsset chertWall5 = Renderer.getTexture("sprites/blocks/Block_Chert_Wall_5.png");
        TextureAsset chertWall6 = Renderer.getTexture("sprites/blocks/Block_Chert_Wall_6.png");
        TextureAsset chertWall9 = Renderer.getTexture("sprites/blocks/Block_Chert_Wall_9.png");

        if (getMainTexture() != null) {
            boolean northBlockSame = world.getBlock(new BlockPosition(blockPosition.x, blockPosition.y + 1)) == this ? true : false;
            boolean southBlockSame = world.getBlock(new BlockPosition(blockPosition.x, blockPosition.y - 1)) == this ? true : false;
            boolean westBlockSame = world.getBlock(new BlockPosition(blockPosition.x - 1, blockPosition.y)) == this ? true : false;
            boolean eastBlockSame = world.getBlock(new BlockPosition(blockPosition.x + 1, blockPosition.y)) == this ? true : false;

            if (!isOccluding) {
                if (northBlockSame && westBlockSame && southBlockSame && eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall5, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 1f)));
                }
                else if (northBlockSame && westBlockSame && !southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall3, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 1f)));
                }
                else if (northBlockSame && westBlockSame && !southBlockSame && eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(getMainTexture(), blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 1f)));
                }
                else if (northBlockSame && westBlockSame && southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall6, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 1f)));
                }
                else if (!northBlockSame && westBlockSame && southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall9, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 1f)));
                }
            }
            else {
                if (northBlockSame && westBlockSame && southBlockSame && eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall5, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 0.25f)));
                }
                else if (northBlockSame && westBlockSame && !southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall3, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 0.25f)));
                }
                else if (northBlockSame && westBlockSame && !southBlockSame && eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(getMainTexture(), blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 0.25f)));
                }
                else if (northBlockSame && westBlockSame && southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall6, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 0.25f)));
                }
                else if (!northBlockSame && westBlockSame && southBlockSame && !eastBlockSame) {
                    renderer.addWorldViewable(new Viewable(chertWall9, blockPosition.toWorldPosition(), 15, new Color(lightLevel, lightLevel, lightLevel, 0.25f)));
                }
            }
        }

        if (settings.isHitboxShown && isCollidable()) {
            Box propCollider = new Box(getCollider().x + blockPosition.x, getCollider().y + blockPosition.y, getCollider().width, getCollider().height);

            renderer.addWorldWireframe(new Wireframe(propCollider, Wireframe.RED));
        }
    }

    @Override
    public void onBlockPlaced(World world, BlockPosition blockPosition) {
        world.setCeiling(true, blockPosition);
    }
}
