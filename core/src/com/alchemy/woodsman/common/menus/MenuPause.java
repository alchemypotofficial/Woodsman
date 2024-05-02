package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.Woodsman;
import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.menus.elements.ElementButton;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MenuPause extends Menu {

    private EntityPlayer player;

    private ElementButton resumeButton;
    private ElementButton settingsButton;
    private ElementButton exitButton;

    public MenuPause(String id) {
        super(id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        resumeButton = new ElementButton(new Box(-150, 60, 300, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Resume Game");
        resumeButton.render(renderer, 100, mousePosition);

        settingsButton = new ElementButton(new Box(-170, 0, 160, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Settings");
        settingsButton.render(renderer, 100, mousePosition);

        exitButton = new ElementButton(new Box(10, 0, 160, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Exit Game");
        exitButton.render(renderer, 100, mousePosition);
    }

    @Override
    public void onMouseLeftDown(Vector2 mousePosition) {
        if (resumeButton != null && resumeButton.isHovered(mousePosition)) {
            MenuHandler.focus(Menus.MENU_HOTBAR);
            player.closeMenu();

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, Game.getSettings().effectsVolume, false);
        }
        else if (settingsButton != null && settingsButton.isHovered(mousePosition)) {
            Menus.MENU_SETTINGS.setPlayer(player);
            MenuHandler.focus(Menus.MENU_SETTINGS);

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, Game.getSettings().effectsVolume, false);
        }
        else if (exitButton != null && exitButton.isHovered(mousePosition)) {
            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, Game.getSettings().effectsVolume, false);

            Game.exit();
        }
    }

    public final void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
