package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Viewable;
import com.badlogic.gdx.math.Vector2;

public class MenuHealthBar extends Menu {

    protected EntityPlayer player;

    public MenuHealthBar(String id) {
        super(id);
    }

    public void tick() {

    }

    public void render(Renderer renderer, Vector2 mousePosition) {
        TextureAsset healthEmpty = Renderer.getTexture("sprites/menus/Menu_Heart_Empty.png");
        TextureAsset healthHalf = Renderer.getTexture("sprites/menus/Menu_Heart_Half.png");
        TextureAsset healthFull = Renderer.getTexture("sprites/menus/Menu_Heart_Full.png");

        TextureAsset thirstEmpty = Renderer.getTexture("sprites/menus/Menu_Thirst_Empty.png");
        TextureAsset thirstHalf = Renderer.getTexture("sprites/menus/Menu_Thirst_Half.png");
        TextureAsset thirstFull = Renderer.getTexture("sprites/menus/Menu_Thirst_Full.png");

        TextureAsset hungerEmpty = Renderer.getTexture("sprites/menus/Menu_Hunger_Empty.png");
        TextureAsset hungerHalf = Renderer.getTexture("sprites/menus/Menu_Hunger_Half.png");
        TextureAsset hungerFull = Renderer.getTexture("sprites/menus/Menu_Hunger_Full.png");

        if (player != null) {
            for (int x = 0; x < player.getMaxHealth() / 2; x++) {
                renderer.addMenuViewable(new Viewable(healthEmpty, new Vector2(-432f + (x * 24f), 224f), 20, 1.5f));
                renderer.addMenuViewable(new Viewable(hungerEmpty, new Vector2(-432f + (x * 18f), 200f), 20, 1.5f));
                renderer.addMenuViewable(new Viewable(thirstEmpty, new Vector2(-432f + (x * 18f), 176f), 20, 1.5f));
            }

            for (int x = 0; x < player.getHealth() / 2; x++) {
                renderer.addMenuViewable(new Viewable(healthFull, new Vector2(-432f + (x * 24f), 224f), 20, 1.5f));
            }

            for (int x = 0; x < player.getHunger() / 2; x++) {
                renderer.addMenuViewable(new Viewable(hungerFull, new Vector2(-432f + (x * 18f), 200f), 20, 1.5f));
            }

            for (int x = 0; x < player.getThirst() / 2; x++) {
                renderer.addMenuViewable(new Viewable(thirstFull, new Vector2(-432f + (x * 18f), 176f), 20, 1.5f));
            }

            if (player.getHealth() % 2 != 0) {
                int fullHearts = player.getHealth() / 2;

                renderer.addMenuViewable(new Viewable(healthHalf, new Vector2(-432f + (fullHearts * 24f), 224f), 20, 1.5f));
            }

            if (player.getHunger() % 2 != 0) {
                int fullThirsts = player.getHunger() / 2;

                renderer.addMenuViewable(new Viewable(hungerHalf, new Vector2(-432f + (fullThirsts * 18f), 200f), 20, 1.5f));
            }

            if (player.getThirst() % 2 != 0) {
                int fullThirsts = player.getThirst() / 2;

                renderer.addMenuViewable(new Viewable(thirstHalf, new Vector2(-432f + (fullThirsts * 18f), 176f), 20, 1.5f));
            }
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
