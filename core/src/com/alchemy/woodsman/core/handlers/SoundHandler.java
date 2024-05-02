package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.sound.SoundAsset;
import com.alchemy.woodsman.core.sound.SoundInstance;
import com.alchemy.woodsman.core.utilities.Debug;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class SoundHandler {

    private static Registry<SoundAsset> sounds = new Registry<>();

    public final static SoundAsset getSound(String soundPath) {
        if (sounds.getEntry(soundPath) != null) {
            return sounds.getEntry(soundPath);
        }
        else {
            Sound rawSound;

            try {
                rawSound = Gdx.audio.newSound(Gdx.files.internal(soundPath));
            }
            catch (Exception exception) {
                Debug.logError("Could not find sound \"" + soundPath + "\".");

                return null;
            }

            if (rawSound != null) {
                Debug.logNormal("Sound", "Registering sound at " + soundPath + ".");

                SoundAsset soundAsset = new SoundAsset(soundPath, rawSound);
                sounds.register(soundAsset);

                return soundAsset;
            }
        }

        return null;
    }

    public final static SoundInstance playSound(SoundAsset soundAsset) {
        return playSound(soundAsset, false);
    }

    public final static SoundInstance playSound(SoundAsset soundAsset, boolean looping) {
        return playSound(soundAsset, Game.getSettings().masterVolume, looping);
    }

    public final static SoundInstance playSound(SoundAsset soundAsset, float volume, boolean looping) {
        if (soundAsset != null) {
            long id = 0;

            if (looping) {
                id = soundAsset.rawSound.loop(volume * Game.getSettings().masterVolume);
            }
            else {
                id = soundAsset.rawSound.play(volume * Game.getSettings().masterVolume);
            }

            SoundInstance soundInstance = new SoundInstance(id, soundAsset, looping);

            return soundInstance;
        }
        else {
            Debug.logError("Can not play null sound.");

            return null;
        }
    }

    public void dispose() {
        ArrayList<SoundAsset> soundAssets = sounds.getEntries();
        for (SoundAsset soundAsset : soundAssets) {
            soundAsset.rawSound.dispose();
        }
    }

}
