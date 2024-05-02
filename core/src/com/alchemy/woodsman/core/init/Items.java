package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.items.*;
import com.alchemy.woodsman.common.items.Tools.*;
import com.alchemy.woodsman.core.graphics.data.Registry;

public class Items {

    public static final Registry<Item> ITEMS = new Registry<Item>();

    public static final ItemFloor ITEM_STICK = new ItemFloor("woodsman.stick", "woodsman.item.stick", "sprites/items/Item_Stick.png", 16, "woodsman.dirt");
    public static final Item ITEM_STRAW = new Item("woodsman.straw", "woodsman.item.straw", "sprites/items/Item_Straw.png", 16);
    public static final Item ITEM_CLAY = new Item("woodsman.clay", "woodsman.item.clay", "sprites/items/Item_Clay.png", 8);
    public static final Item ITEM_ROCK = new Item("woodsman.rock", "woodsman.item.rock", "sprites/items/Item_Rock.png", 8);
    public static final Item ITEM_WET_BRICK = new Item("woodsman.wet_brick", "woodsman.item.wet_brick", "sprites/items/Item_Wet_Brick.png", 8);
    public static final Item ITEM_DRY_BRICK = new Item("woodsman.dry_brick", "woodsman.item.dry_brick", "sprites/items/Item_Dry_Brick.png", 8);

    public static final Item ITEM_OAK_LOG = new Item("woodsman.oak_log", "woodsman.item.oak_log", "sprites/items/Item_Oak_Log.png", 8);
    public static final Item ITEM_FLINT = new Item("woodsman.flint", "woodsman.item.flint", "sprites/items/Item_Flint.png", 8);
    public static final Item ITEM_MALACHITE_NUGGET = new Item("woodsman.malachite_nugget", "woodsman.item.malachite_nugget", "sprites/items/Item_Malachite_Nugget.png", 16);

    public static final ItemPickaxe ITEM_FLINT_PICKAXE = new ItemPickaxe("woodsman.flint_pickaxe", "woodsman.item.flint_pickaxe", "sprites/items/Item_Flint_Pickaxe.png", 1, "woodsman.flint");
    public static final ItemPickaxe ITEM_COPPER_PICKAXE = new ItemPickaxe("woodsman.copper_pickaxe", "woodsman.item.copper_pickaxe", "sprites/items/Item_Copper_Pickaxe.png", 1, "woodsman.copper");

    public static final ItemAxe ITEM_FLINT_AXE = new ItemAxe("woodsman.flint_axe", "woodsman.item.flint_axe", "sprites/items/Item_Flint_Axe.png", 1, "woodsman.flint");
    public static final ItemAxe ITEM_COPPER_AXE = new ItemAxe("woodsman.copper_axe", "woodsman.item.copper_axe", "sprites/items/Item_Copper_Axe.png", 1, "woodsman.copper");

    public static final ItemHammer ITEM_FLINT_HAMMER = new ItemHammer("woodsman.flint_hammer", "woodsman.item.flint_hammer","sprites/items/Item_Flint_Hammer.png", 1, "woodsman.flint");
    public static final ItemHammer ITEM_COPPER_HAMMER = new ItemHammer("woodsman.copper_hammer", "woodsman.item.copper_hammer","sprites/items/Item_Copper_Hammer.png", 1, "woodsman.copper");

    public static final ItemSword ITEM_COPPER_SWORD = new ItemSword("woodsman.copper_sword", "woodsman.item.copper_sword", "sprites/items/Item_Copper_Sword.png", 1, "woodsman.copper");

    public static final ItemFireStarter ITEM_FIRE_PLOW = new ItemFireStarter("woodsman.fire_plow", "woodsman.item.fire_plow", "sprites/items/Item_Fire_Plow.png", 1, "woodsman.flint");

    public static final ItemBlock ITEM_CHEST = new ItemBlock("woodsman.chest", "woodsman.item.chest", "sprites/items/Item_Chest.png", 1, "woodsman.chest");
    public static final ItemBlock ITEM_CAMPFIRE = new ItemBlock("woodsman.campfire", "woodsman.item.campfire", "sprites/blocks/Block_Campfire.png", 1, "woodsman.campfire");
    public static final Item ITEM_POT = new Item("woodsman.pot", "woodsman.item.pot", "sprites/items/Item_Pot.png", 1);



    public static final ItemSapling ITEM_OAK_SAPLING = new ItemSapling("woodsman.oak_sapling", "woodsman.item.oak_sapling", "sprites/items/Item_Flint.png", 8, "woodsman.oak_tree");

    public static void register() {
        ITEMS.register(ITEM_STICK);
        ITEMS.register(ITEM_STRAW);
        ITEMS.register(ITEM_CLAY);
        ITEMS.register(ITEM_ROCK);

        ITEMS.register(ITEM_WET_BRICK);
        ITEMS.register(ITEM_DRY_BRICK);

        ITEMS.register(ITEM_OAK_LOG);

        ITEMS.register(ITEM_FLINT);
        ITEMS.register(ITEM_MALACHITE_NUGGET);

        ITEMS.register(ITEM_FLINT_PICKAXE);
        ITEMS.register(ITEM_COPPER_PICKAXE);

        ITEMS.register(ITEM_FLINT_AXE);
        ITEMS.register(ITEM_COPPER_AXE);

        ITEMS.register(ITEM_FLINT_HAMMER);
        ITEMS.register(ITEM_COPPER_HAMMER);

        ITEMS.register(ITEM_COPPER_SWORD);
        ITEMS.register(ITEM_FIRE_PLOW);

        ITEMS.register(ITEM_CAMPFIRE);
        ITEMS.register(ITEM_POT);
        ITEMS.register(ITEM_CHEST);

        ITEMS.register(ITEM_OAK_SAPLING);
    }
}
