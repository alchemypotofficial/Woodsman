package com.alchemy.woodsman.core.sound;

import com.alchemy.woodsman.Game;
import com.badlogic.gdx.math.MathUtils;

public class SoundInstance {

    private long id;
    private SoundAsset sound;
    private float volume;
    private boolean looping;

    public SoundInstance(long id, SoundAsset sound, boolean looping) {
        this.id = id;
        this.sound = sound;
        this.looping = looping;

        volume = 1f;

        if (sound != null && sound.rawSound != null) {
            setLooping(looping);
        }
    }

    public SoundInstance(long id, SoundAsset sound, float volume, boolean looping) {
        this.id = id;
        this.sound = sound;
        this.volume = volume;
        this.looping = looping;

        if (sound != null && sound.rawSound != null) {
            setLooping(looping);
        }
    }

    public final void pause() {
        sound.rawSound.pause(id);
    }

    public final void resume() {
        sound.rawSound.resume(id);
    }

    public final void stop() {
        sound.rawSound.stop(id);
    }

    public final void setVolume(float volume) {
        this.volume = MathUtils.clamp(volume, 0f, 1f);
        sound.rawSound.setVolume(id, volume * Game.getSettings().masterVolume);
    }

    public final void setLooping(boolean looping) {
        this.looping = looping;

        if (sound != null && sound.rawSound != null) {
            sound.rawSound.setLooping(id, looping);
        }
    }

    public final boolean isLooping() {
        return this.looping;
    }
}
