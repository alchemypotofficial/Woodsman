package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.floors.*;
import com.alchemy.woodsman.core.graphics.data.Registry;

public class Floors {

    public static final Registry<Floor> FLOORS = new Registry<Floor>();

    public static final FloorGrass FLOOR_GRASS = new FloorGrass("woodsman.grass", "sprites/floors/Floor_Grass.png");
    public static final FloorDirt FLOOR_DIRT = new FloorDirt("woodsman.dirt", "sprites/floors/Floor_Dirt.png");
    public static final FloorSand FLOOR_SAND = new FloorSand("woodsman.sand", "sprites/floors/Floor_Sand.png");
    public static final FloorStone FLOOR_STONE = new FloorStone("woodsman.stone", "sprites/floors/Floor_Stone.png");
    public static final FloorWater FLOOR_WATER = new FloorWater("woodsman.water", "sprites/floors/Floor_Water.png");
    public static final FloorFreshWater FLOOR_FRESH_WATER = new FloorFreshWater("woodsman.fresh_water", "sprites/floors/Floor_Fresh_Water.png");
    public static final FloorClay FLOOR_CLAY = new FloorClay("woodsman.clay", "sprites/floors/Floor_Clay.png");

    public static void register() {
        FLOORS.register(FLOOR_GRASS);
        FLOORS.register(FLOOR_DIRT);
        FLOORS.register(FLOOR_SAND);
        FLOORS.register(FLOOR_STONE);
        FLOORS.register(FLOOR_WATER);
        FLOORS.register(FLOOR_FRESH_WATER);
        FLOORS.register(FLOOR_CLAY);
    }
}
