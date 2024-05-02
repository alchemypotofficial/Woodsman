package com.alchemy.woodsman.core;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Recipes;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class Settings implements Json.Serializable {

    public enum ScreenMode { Windowed, Fullscreen, Borderless }

    public static boolean isMaximized = false;
    public static boolean isInDebugMode = false;
    public static boolean isChunkBorderShown = false;
    public static boolean isHitboxShown = false;
    public static boolean isHUDShown = true;

    public ScreenMode screenMode = ScreenMode.Borderless;

    public boolean usingVSync = true;

    public float masterVolume = 1f;
    public float musicVolume = 1f;
    public float effectsVolume = 1f;
    public float ambienceVolume = 1f;

    public final void applySettings() {
        if (screenMode == Settings.ScreenMode.Borderless) {
            Game.setBorderless();
        }
        else if (screenMode == Settings.ScreenMode.Fullscreen) {
            Game.setFullScreen();
        }
        else {
            Game.setWindowed();
        }

        Game.setVSync(usingVSync);
    }

    @Override
    public void write(Json json) {
        json.writeValue("screenMode", screenMode.toString());
        json.writeValue("usingVSync", usingVSync);

        json.writeValue("masterVolume", masterVolume);
        json.writeValue("musicVolume", musicVolume);
        json.writeValue("effectsVolume", effectsVolume);
        json.writeValue("ambienceVolume", ambienceVolume);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        if (jsonData.has("screenMode")) {
            String screenModeName = jsonData.getString("screenMode");

            switch (screenModeName) {
                case "Windowed": screenMode = ScreenMode.Windowed; break;
                case "Fullscreen": screenMode = ScreenMode.Fullscreen; break;
                case "Borderless":
                default: screenMode = ScreenMode.Borderless; break;
            }
        }

        if (jsonData.has("usingVSync")) {
            usingVSync = jsonData.getBoolean("usingVSync");
        }

        if (jsonData.has("masterVolume")) {
            masterVolume = jsonData.getFloat("masterVolume");
        }

        if (jsonData.has("musicVolume")) {
            musicVolume = jsonData.getFloat("musicVolume");
        }

        if (jsonData.has("effectsVolume")) {
            effectsVolume = jsonData.getFloat("effectsVolume");
        }

        if (jsonData.has("ambienceVolume")) {
            ambienceVolume = jsonData.getFloat("ambienceVolume");
        }
    }
}
