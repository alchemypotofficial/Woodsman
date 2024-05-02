package com.alchemy.woodsman.common.blocks;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.blockstates.BlockState;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.floors.Floor;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Gesture;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.BlockTag;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class Block extends Registerable {
    private TextureAsset mainTexture;
    private ArrayList<BlockTag> blockTags;
    private int maxHealth;

    private Box collider;
    private Box occluder;

    private boolean isCollidable;
    private boolean isOccludable;

    private boolean hasCeiling;

    public Block(String id, int maxHealth, Box collider, boolean isCollidable, boolean hasCeiling, String texturePath) {
        super(id);

        this.maxHealth = maxHealth;
        this.collider = collider;
        this.isCollidable = isCollidable;
        this.hasCeiling = hasCeiling;

        occluder = new Box(0f,0.5f,1f, 1f);
        isOccludable = false;
        mainTexture = Renderer.getTexture(texturePath);

        blockTags = new ArrayList<>();
    }

    public Block(String id, int maxHealth, Box collider, boolean isCollidable, String texturePath) {
        super(id);

        this.maxHealth = maxHealth;
        this.collider = collider;
        this.isCollidable = isCollidable;

        hasCeiling = false;
        occluder = new Box(0f,0.5f,1f, 1f);
        isOccludable = false;
        mainTexture = Renderer.getTexture(texturePath);

        blockTags = new ArrayList<>();
    }

    public void tick(World world, BlockPosition blockPosition) {

    }

    public void render(Renderer renderer, World world, BlockPosition blockPosition, Color color) {
        Settings settings = Game.getSettings();

        if (mainTexture != null) {
            renderer.addWorldViewable(new Viewable(mainTexture, blockPosition.toWorldPosition(), 15, color));
        }

        if (settings.isHitboxShown && isCollidable) {
            Box blockCollider = new Box(collider.x + blockPosition.x, collider.y + blockPosition.y, collider.width, collider.height);

            renderer.addWorldWireframe(new Wireframe(blockCollider, Wireframe.RED));
        }
    }

    public void onBlockPlaced(World world, BlockPosition blockPosition) {

    }

    public void onBlockBroken(World world, BlockPosition blockPosition) {

    }

    public boolean onInteract(World world, EntityPlayer player, BlockPosition blockPosition) {
        return false;
    }

    public BlockState createBlockState() {
        return new BlockState(getMaxHealth());
    }

    public boolean canReplace() {
        return false;
    }

    public boolean isValidFloor(Floor floor) {
        return true;
    }

    public final void addBlockTags(BlockTag[] blockTags) {
        for (BlockTag blockTag : blockTags) {
            this.blockTags.add(blockTag);
        }
    }

    public final void setMainTexture(TextureAsset texture) {
        this.mainTexture = texture;
    }

    public final void setOccluder(Box occluder) {
        this.occluder = occluder;
    }
    public final void setOccludable(boolean isOccludable) {
        this.isOccludable = isOccludable;
    }

    public int getLightLevel(World world, BlockPosition blockPosition) {
        return 0;
    }

    public Gesture getGesture() {
        return null;
    }

    public SoundAsset getDamageSound() {
        return null;
    }

    public final TextureAsset getMainTexture() {
        return this.mainTexture;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    public final Box getCollider() {
        return this.collider;
    }

    public final Box getOccluder() {
        return this.occluder;
    }

    public final ArrayList<BlockTag> getBlockTags() {
        return new ArrayList<>(blockTags);
    }

    public final boolean isCollidable() {
        return this.isCollidable;
    }

    public final boolean isOccludable() {
        return this.isOccludable;
    }


    public final boolean hasCeiling() {
        return this.hasCeiling;
    }

    public final boolean hasBlockTag(BlockTag blockTag) {
        for (BlockTag tag : blockTags) {
            if (tag == blockTag) {
                return true;
            }
        }

        return false;
    }
}
