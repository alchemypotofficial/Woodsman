package com.alchemy.woodsman.core.utilities;
import com.badlogic.gdx.math.MathUtils;


public class Noise {

    private long seed;

    public Noise(long seed) {
        this.seed = seed;
    }

    public float sampleTileable(int x, int y, int size, float scale, int octaves, float persistence, float lacunarity) {
        float amplitude = 1f;
        float frequency = 1f;
        float noiseHeight = 0f;
        float totalAmplitude = 0f;

        //* Add each octave.
        for (int octave = 0; octave < octaves; octave++) {

            float s = x / size;
            float t = y / size;

            //* Wrap Y axis.
            double sinY = MathUtils.sin(y * MathUtils.PI2 / size) / MathUtils.PI2 * size / scale;
            double cosY = MathUtils.cos(y * MathUtils.PI2 / size) / MathUtils.PI2 * size / scale;

            //* Wrap X axis.
            double sinX = MathUtils.sin(x * MathUtils.PI2 / size) / MathUtils.PI2 * size / scale;
            double cosX = MathUtils.cos(x * MathUtils.PI2 / size) / MathUtils.PI2 * size / scale;

            //* Evaluate 4D noise using wrapped X and Y axis.
            double value = OpenSimplex2S.noise4_ImproveXYZ(seed, sinX * frequency, cosX * frequency, sinY * frequency, cosY * frequency);

            //* Accumulate noise.
            noiseHeight += value * amplitude;

            //* Increase amplitude and frequencies and add to totalAmplitude.
            totalAmplitude += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }

        return noiseHeight / totalAmplitude;
    }
}