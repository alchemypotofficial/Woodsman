package com.alchemy.woodsman.common.items;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.LangHandler;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

public class Item extends Registerable {

    protected String staticName;
    protected TextureAsset texture;
    protected int maxAmount;
    protected int maxDurability;
    protected float useTime;

    public Item(String id, String staticName, String texturePath, int maxAmount) {
        super(id);

        this.maxAmount = maxAmount;

        this.staticName = staticName;
        texture = Renderer.getTexture(texturePath);

        useTime = 0f;
    }

    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {

    }

    public void onSecondary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {

    }

    public void renderHovered(Renderer renderer, World world, EntityPlayer player, Vector2 mousePosition) {

    }

    public TextureAsset getUseAnimation() {
        return null;
    }

    public final String getStaticName() {
        return this.staticName;
    }

    public final String getName() {
        return LangHandler.getLocalName(this.getStaticName());
    }

    public final TextureAsset getTexture() {
        return this.texture;
    }

    public final int getMaxAmount() {
        return this.maxAmount;
    }

    public final int getMaxDurability() {
        return this.maxDurability;
    }

    public final float getUseTime() {
        return this.useTime;
    }
}