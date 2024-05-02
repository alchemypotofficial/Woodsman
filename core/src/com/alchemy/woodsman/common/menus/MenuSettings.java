package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.menus.elements.ElementButton;
import com.alchemy.woodsman.common.menus.elements.ElementSlider;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.handlers.SoundHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.utilities.physics.Box;
import com.badlogic.gdx.math.Vector2;

public class MenuSettings extends Menu {

    private EntityPlayer player;

    private ElementButton screenModeButton;
    private ElementButton usingVSyncButton;
    private ElementButton saveButton;
    private ElementButton cancelButton;

    private ElementSlider masterSlider;
    private ElementSlider musicSlider;
    private ElementSlider effectsSlider;

    public MenuSettings(String id) {
        super(id);
    }

    @Override
    public void tick() {
        Settings settings = Game.getSettings();

        if (masterSlider != null && masterSlider.isDragging()) {
            settings.masterVolume = masterSlider.getValue();
        }

        if (musicSlider != null && musicSlider.isDragging()) {
            settings.musicVolume = musicSlider.getValue();
        }

        if (effectsSlider != null && effectsSlider.isDragging()) {
            settings.effectsVolume = effectsSlider.getValue();
        }
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        String screenModeText;
        if (settings.screenMode == Settings.ScreenMode.Borderless) {
            screenModeText = "Screen Mode: Borderless";
        }
        else if (settings.screenMode == Settings.ScreenMode.Fullscreen) {
            screenModeText = "Screen Mode: Fullscreen";
        }
        else {
            screenModeText = "Screen Mode: Windowed";
        }

        String usingVSyncText = MessageHandler.formatBoolean(settings.usingVSync);

        screenModeButton = new ElementButton(new Box(-320, 120, 192, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), screenModeText);
        screenModeButton.render(renderer, 100, mousePosition);

        usingVSyncButton = new ElementButton(new Box(-320, 60, 192, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Using Vsync: " + usingVSyncText);
        usingVSyncButton.render(renderer, 100, mousePosition);

        saveButton = new ElementButton(new Box(-192 - 24, -120, 192, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Save Changes");
        saveButton.render(renderer, 100, mousePosition);

        cancelButton = new ElementButton(new Box(24, -120, 192, 40), Renderer.getTexture("sprites/menus/system/Menu_Border_2.png"), "Cancel Changes");
        cancelButton.render(renderer, 100, mousePosition);

        masterSlider.render(renderer, 100, mousePosition);
        renderer.addMenuText(new Text("Master Volume: " + MessageHandler.formatFloat(settings.masterVolume), new Vector2(0, 140), 100));

        musicSlider.render(renderer, 100, mousePosition);
        renderer.addMenuText(new Text("Music Volume: " + MessageHandler.formatFloat(settings.musicVolume), new Vector2(0, 80), 100));

        effectsSlider.render(renderer, 100, mousePosition);
        renderer.addMenuText(new Text("Effects Volume: " + MessageHandler.formatFloat(settings.effectsVolume), new Vector2(0, 20), 100));
    }

    @Override
    public void onShow() {
        masterSlider = new ElementSlider(new Box(0, 120, 32, 16), Game.getSettings().masterVolume, 64, false);
        musicSlider = new ElementSlider(new Box(0, 60, 32, 16), Game.getSettings().musicVolume, 64, false);
        effectsSlider = new ElementSlider(new Box(0, 0, 32, 16), Game.getSettings().effectsVolume, 64, false);
    }

    @Override
    public void onMouseLeftDown(Vector2 mousePosition) {
        Settings settings = Game.getSettings();

        if (masterSlider != null && masterSlider.isButtonHovered(mousePosition)) {
            masterSlider.startDrag();
        }

        if (musicSlider != null && musicSlider.isButtonHovered(mousePosition)) {
            musicSlider.startDrag();
        }

        if (effectsSlider != null && effectsSlider.isButtonHovered(mousePosition)) {
            effectsSlider.startDrag();
        }

        if (screenModeButton != null && screenModeButton.isHovered(mousePosition)) {
            //* Switch through screenModes.
            if (settings.screenMode == Settings.ScreenMode.Borderless) {
                settings.screenMode = Settings.ScreenMode.Fullscreen;
            }
            else if (settings.screenMode == Settings.ScreenMode.Fullscreen) {
                settings.screenMode = Settings.ScreenMode.Windowed;
            }
            else {
                settings.screenMode = Settings.ScreenMode.Borderless;
            }

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, settings.effectsVolume, false);
        }
        else if (usingVSyncButton != null && usingVSyncButton.isHovered(mousePosition)) {
            //* Toggle VSync.
            settings.usingVSync = !settings.usingVSync;

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, settings.effectsVolume, false);
        }
        else if (saveButton != null && saveButton.isHovered(mousePosition)) {
            //* Save and apply settings.
            Game.saveSettings();
            Game.getSettings().applySettings();

            MenuHandler.focus(Menus.MENU_PAUSE);

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, settings.effectsVolume, false);
        }
        else if (cancelButton != null && cancelButton.isHovered(mousePosition)) {
            MenuHandler.focus(Menus.MENU_PAUSE);

            //* Cancel changes made to settings.
            Game.loadSettings();

            SoundAsset sound = SoundHandler.getSound("sounds/system/Click.wav");
            SoundHandler.playSound(sound, settings.effectsVolume, false);
        }
    }

    @Override
    public void onMouseLeftUp(Vector2 mousePosition) {
        masterSlider.stopDrag();
        musicSlider.stopDrag();
        effectsSlider.stopDrag();
    }

    @Override
    public void onButtonDown(InputHandler.Button button, Vector2 mousePosition) {
        if (button == InputHandler.Cancel) {
            MenuHandler.focus(Menus.MENU_HOTBAR);
            player.closeMenu();
        }
    }

    public final void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
