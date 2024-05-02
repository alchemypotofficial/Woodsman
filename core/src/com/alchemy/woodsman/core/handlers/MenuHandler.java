package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.common.menus.Menu;
import com.alchemy.woodsman.core.graphics.Renderer;

import java.util.ArrayList;

public class MenuHandler {

    private static ArrayList<Menu> visibleMenus = new ArrayList<Menu>();
    private static Menu focusedMenu;

    public MenuHandler() {
        focusedMenu = null;
    }

    public final void tick() {
        ArrayList<Menu> currentVisibleMenus = new ArrayList<Menu>(visibleMenus);

        if (focusedMenu != null) {
            focusedMenu.tick();
        }

        for (Menu menu : currentVisibleMenus) {
            menu.tick();
        }
    }

    public final void render(Renderer renderer) {
        if (focusedMenu != null) {
            focusedMenu.render(renderer, renderer.toMenuPosition(InputHandler.getMousePosition()));
        }

        for (Menu menu : visibleMenus) {
            menu.render(renderer, InputHandler.getMousePosition());
        }
    }

    public static final void show(Menu menu) {
        if (!visibleMenus.contains(menu)) {
            menu.onShow();
            visibleMenus.add(menu);
        }
    }

    public static final void hide(Menu menu) {
        if (visibleMenus.contains(menu)) {
            menu.onHide();
            visibleMenus.remove(menu);
        }
    }

    public static final void focus(Menu menu) {
        if (focusedMenu != null) {
            focusedMenu.onHide();
        }

        focusedMenu = menu;
        focusedMenu.onShow();
    }

    public static final void defocus() {
        focusedMenu.onHide();
        focusedMenu = null;
    }

    public static final boolean isShown(Menu menu) {
        if (visibleMenus.contains(menu) || focusedMenu == menu) {
            return true;
        }

        return false;
    }

    public static final boolean hasFocus(Menu menu) {
        if (focusedMenu == menu) {
            return true;
        }

        return false;
    }

    public static final boolean isFocused() {
        if (focusedMenu != null) {
            return true;
        }

        return false;
    }
}
