package com.alchemy.woodsman.common.items.Tools;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Wireframe;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

public class ItemSword extends ItemTool {

    public ItemSword(String id, String staticName, String texturePath, int maxAmount, String tier) {
        super(id, staticName, texturePath, maxAmount, tier);
    }

    @Override
    public void onPrimary(ItemStack itemStack, World world, EntityPlayer player, Vector2 mouseWorldPosition) {

    }

    @Override
    public void renderHovered(Renderer renderer, World world, EntityPlayer player, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        if (settings.isHitboxShown) {
            Box hurtBox = new Box(0, 0, 1f, 1f);
            hurtBox = hurtBox.translate(player.getPosition().x, player.getPosition().y);

            renderer.addWorldWireframe(new Wireframe(hurtBox, Wireframe.YELLOW));
        }
    }
}
