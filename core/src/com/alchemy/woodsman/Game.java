package com.alchemy.woodsman;

import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.utilities.Debug;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
    private Woodsman woodsman;
    private static Settings settings;

    private static String version = "1.01.1";

    private float physicsUpdateSpeed = 1f / 60f;
    private float lastUpdateDelta = 0f;

    @Override
    public void create () {
        loadSettings();

        woodsman = new Woodsman();

        woodsman.preInit();
        woodsman.init();
        woodsman.postInit();
    }

    @Override
    public void render () {
        clearScreen();

        woodsman.physics();
        woodsman.tick();
        woodsman.render();
    }

    public final static void exit() {
        Gdx.app.exit();
    }

    protected final void clearScreen() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    public final static void loadSettings() {
        FileHandle settingsFile = getLocalFile("settings.cfg");

        if(settingsFile.exists()) {
            String settingsString = settingsFile.readString();

            //* Load settings file.
            Debug.logState("Settings", "Loaded settings.");
            settings = new Json().fromJson(Settings.class, settingsString);

            settings.applySettings();
        }
        else {
            settings = new Settings();
            saveSettings();
        }
    }

    public final static void saveSettings() {
        FileHandle settingsFile = Game.getLocalFile("settings.cfg");

        if (settings != null) {
            String settingsString = new Json().toJson(settings);

            //* Save settings file.
            Debug.logState("Settings", "Saved settings.");
            settingsFile.writeString(settingsString, false);

            settings.applySettings();
        }
    }

    public final static void setBorderless() {
        Graphics.Monitor monitor = Gdx.graphics.getMonitor();
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(monitor);

        Gdx.graphics.setUndecorated(true);
        Gdx.graphics.setResizable(false);
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1);

        Settings settings = Game.getSettings();

        settings.screenMode = Settings.ScreenMode.Borderless;
    }

    public final static void setFullScreen() {
        Graphics.Monitor monitor = Gdx.graphics.getMonitor();
        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(monitor);

        Gdx.graphics.setUndecorated(true);
        Gdx.graphics.setResizable(false);
        Gdx.graphics.setFullscreenMode(displayMode);

        Settings settings = Game.getSettings();

        settings.screenMode = Settings.ScreenMode.Fullscreen;
    }

    public final static void setWindowed() {
        Gdx.graphics.setUndecorated(false);
        Gdx.graphics.setResizable(true);
        Gdx.graphics.setWindowedMode(896, 512);

        Settings settings = Game.getSettings();

        settings.screenMode = Settings.ScreenMode.Windowed;
    }

    public final static void setVSync(boolean usingVSync) {
        Gdx.graphics.setVSync(usingVSync);

        Settings settings = Game.getSettings();

        settings.usingVSync = usingVSync;
    }

    public final static void setSettings(Settings newSettings) {
        settings = newSettings;
    }

    public static long getSystemTime() {
        return System.currentTimeMillis();
    }

    public final static float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public final static int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }

    public final static FileHandle getInternalFile(String path) {
        return Gdx.files.internal(path);
    }

    public final static FileHandle getLocalFile(String path) {
        return Gdx.files.local(path);
    }

    public final static Settings getSettings() {
        return settings;
    }

    public final static String getVersion() {
        return version;
    }

    @Override
    public void dispose () {
        woodsman.dispose();
    }
}
