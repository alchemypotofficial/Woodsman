package com.alchemy.woodsman.core.utilities;

import com.badlogic.gdx.math.Vector2;

public class UsefulMath {

    /**
     * Apply a weighted average to point A.
     * @param a Point A
     * @param b Point B
     * @param f Frequency
     * @return Averaged point
     */
    public static float weightedAverage(float a, float b, float f) {
        return ((a * (f - 1)) + b) / f;
    }

    /**
     * Wrap integer I between minimum and maximum.
     * @param i Integer
     * @param min Minimum
     * @param max Maximum
     * @return Wrapped integer
     */
    public static int wrap(int i, int min, int max) {
        int range = max - min + 1;

        if (i < min) {
            i += range * ((min - i) / range + 1);
        }

        return min + (i - min) % range;
    }

    /**
     * Wrap float F between minimum and maximum.
     * @param f Float
     * @param min Minimum
     * @param max Maximum
     * @return Wrapped float
     */
    public static float wrap(float f, float min, float max) {
        float range = max - min + 1;

        if (f < min) {
            f += range * ((min - f) / range + 1);
        }

        return min + (f - min) % range;
    }

    /**
     * Returns the percentage of the range max-min that corresponds to value.
     *
     * @param min Minimum
     * @param max Maximum
     * @param f Float
     * @return Interpolant value within the range [min, max]
     */
    public static float inverseLerp(float f, float min, float max) {
        return (f - min) / (max - min);
    }

    public static boolean isInteger(String string) {
        if (string != null) {
            try {
                int integer = Integer.parseInt(string);

                return true;
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        return false;
    }

    /**
     * Finds the distance between two points.
     * @param point1 Point 1
     * @param point2 Point 2
     * @return Distance
     */
    public static float distance(Vector2 point1, Vector2 point2) {
        double ac = Math.abs(point2.y - point1.y);
        double cb = Math.abs(point2.x - point1.x);

        double distance = Math.hypot(ac, cb);

        return (float)distance;
    }

    /**
     * Finds the direction between two points.
     * @param point1 Point 1
     * @param point2 Point 2
     * @return Direction
     */
    public static Vector2 direction(Vector2 point1, Vector2 point2) {
        return point2.sub(point1).nor();
    }

    /**
     * Finds the direction between two points.
     * @param point1 Point 1
     * @param point2 Point 2
     * @return Direction
     */
    public static Vector2 direction(Vector2 point1, Vector2 point2, float min, float max) {
        return point2.sub(point1).nor();
    }

    /**
     * Adds two vectors.
     * @param vec1 Vector 1
     * @param vec2 Vector 2
     * @return Sum
     */
    public static Vector2 vecAdd(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    /**
     * Subtracts two vectors.
     * @param vec1 Vector 1
     * @param vec2 Vector 2
     * @return Difference
     */
    public static Vector2 vecSub(Vector2 vec1, Vector2 vec2) {
        return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    /**
     * Multiplies a vector by an integer.
     * @param vec Vector
     * @param i Integer
     * @return Product
     */
    public static Vector2 vecScalarMult(Vector2 vec, int i) {
        return new Vector2(vec.x * i, vec.y * i);
    }

    /**
     * Produces the dot product of vec1 and vec2.
     * @param vec1 Vector 1
     * @param vec2 Vector 2
     * @return Product
     */
    public float vecDotProduct(Vector2 vec1, Vector2 vec2) {
        return (vec1.x * vec2.x) + (vec1.y * vec2.y);
    }

    /**
     * Produces the length of a vector.
     * @param vec Vector
     * @return Length
     */
    public float vecLength(Vector2 vec) {
        return (float)Math.abs(Math.sqrt((vec.x * vec.x) + (vec.y * vec.y)));
    }
}
