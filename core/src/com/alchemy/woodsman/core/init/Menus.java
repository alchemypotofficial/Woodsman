package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.common.menus.*;
import com.alchemy.woodsman.core.graphics.data.Registry;

public class Menus {

    public static final Registry<Menu> MENUS = new Registry<Menu>();

    public static final MenuPause MENU_PAUSE = new MenuPause("woodsman.pause");
    public static final MenuSettings MENU_SETTINGS = new MenuSettings("woodsman.settings");

    public static final MenuHealthBar MENU_HEALTH_BAR = new MenuHealthBar("woodsman.health_bar");
    public static final MenuHotbar MENU_HOTBAR = new MenuHotbar("woodsman.hotbar");
    public static final MenuDeath MENU_DEATH = new MenuDeath("woodsman.death");
    public static final MenuInventory MENU_INVENTORY = new MenuInventory("woodsman.inventory");
    public static final MenuMessageBar MENU_MESSAGE_BAR = new MenuMessageBar("woodsman.message_bar");
    public static final MenuMessageList MENU_MESSAGE_LIST = new MenuMessageList("woodsman.message_list");

    public static final MenuCampfire MENU_CAMPFIRE = new MenuCampfire("woodsman.campfire");
    public static final MenuChest MENU_CHEST = new MenuChest("woodsman.chest");

    public static void register() {
        MENUS.register(MENU_PAUSE);
        MENUS.register(MENU_SETTINGS);

        MENUS.register(MENU_HEALTH_BAR);
        MENUS.register(MENU_HOTBAR);
        MENUS.register(MENU_DEATH);
        MENUS.register(MENU_INVENTORY);
        MENUS.register(MENU_MESSAGE_BAR);
        MENUS.register(MENU_MESSAGE_LIST);
        MENUS.register(MENU_CAMPFIRE);
        MENUS.register(MENU_CHEST);
    }
}
