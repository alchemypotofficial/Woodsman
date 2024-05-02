package com.alchemy.woodsman.core.sound;

import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.badlogic.gdx.audio.Sound;

public class SoundAsset extends Registerable {

    public Sound rawSound;

    public SoundAsset(String id, Sound rawSound) {
        super(id);

        this.rawSound = rawSound;
    }
}
