package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.menus.elements.ElementButton;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.data.TextureAsset;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class MenuDeath extends Menu {

    private EntityPlayer player;
    private ElementButton respawnButton;
    private ElementButton exitButton;

    public MenuDeath(String id) {
        super(id);
    }

    public void tick() {

    }

    public void render(Renderer renderer, Vector2 mousePosition) {
        if (player != null) {
            respawnButton = new ElementButton(new Box(-80, 30, 160, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Respawn");
            respawnButton.render(renderer, 100, mousePosition);

            exitButton = new ElementButton(new Box(-80, -30, 160, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Exit Game");
            exitButton.render(renderer, 100, mousePosition);
        }
    }

    @Override
    public void onMouseLeftDown(Vector2 mousePosition) {
        if (respawnButton != null && respawnButton.isHovered(mousePosition)) {
            player.respawn();
            MenuHandler.focus(Menus.MENU_HOTBAR);
        }
        else if (exitButton != null && exitButton.isHovered(mousePosition)) {
            Game.exit();
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
