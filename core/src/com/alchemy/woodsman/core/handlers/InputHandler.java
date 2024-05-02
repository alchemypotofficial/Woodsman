package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.common.menus.Menu;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.utilities.Debug;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class InputHandler implements InputProcessor {

    public static class Button {
        private int key;

        public Button(int key) {
            this.key = key;
        }

        public final void setKey(int key) {
            this.key = key;
        }

        public final int getKey() {
            return this.key;
        }
    }

    public static Button Up = new Button(Input.Keys.W);
    public static Button Down = new Button(Input.Keys.S);
    public static Button Left = new Button(Input.Keys.A);
    public static Button Right = new Button(Input.Keys.D);
    public static Button Shift = new Button(Input.Keys.SHIFT_LEFT);
    public static Button Inventory = new Button(Input.Keys.E);
    public static Button Drop = new Button(Input.Keys.Q);
    public static Button Enter = new Button(Input.Keys.ENTER);
    public static Button One = new Button(Input.Keys.NUM_1);
    public static Button Two = new Button(Input.Keys.NUM_2);
    public static Button Three = new Button(Input.Keys.NUM_3);
    public static Button Four = new Button(Input.Keys.NUM_4);
    public static Button Five = new Button(Input.Keys.NUM_5);
    public static Button Six = new Button(Input.Keys.NUM_6);
    public static Button Seven = new Button(Input.Keys.NUM_7);
    public static Button Eight = new Button(Input.Keys.NUM_8);
    public static Button ArrowUp = new Button(Input.Keys.UP);
    public static Button ArrowDown = new Button(Input.Keys.DOWN);
    public static Button DebugMode = new Button(Input.Keys.F3);
    public static Button ShowHitboxes = new Button(Input.Keys.F8);
    public static Button ShowChunkBorder = new Button(Input.Keys.F7);
    public static Button ShowHUD = new Button(Input.Keys.F1);
    public static Button TakeScreenshot = new Button(Input.Keys.F2);
    public static Button ZoomOut = new Button(Input.Keys.F5);
    public static Button Cancel = new Button(Input.Keys.ESCAPE);

    private ArrayList<Button> buttons;

    private Renderer renderer;
    private ScreenHandler screenHandler;

    public InputHandler (Renderer renderer, ScreenHandler screenHandler) {
        this.renderer = renderer;
        this.screenHandler = screenHandler;

        buttons = new ArrayList<>();

        init();
    }

    private void init() {
        buttons.add(Up);
        buttons.add(Down);
        buttons.add(Left);
        buttons.add(Right);
        buttons.add(Shift);

        buttons.add(Inventory);
        buttons.add(Drop);
        buttons.add(Enter);

        buttons.add(One);
        buttons.add(Two);
        buttons.add(Three);
        buttons.add(Four);
        buttons.add(Five);
        buttons.add(Six);
        buttons.add(Seven);
        buttons.add(Eight);

        buttons.add(ArrowUp);
        buttons.add(ArrowDown);
        buttons.add(DebugMode);
        buttons.add(ShowHitboxes);
        buttons.add(ShowChunkBorder);
        buttons.add(ShowHUD);
        buttons.add(TakeScreenshot);
        buttons.add(ZoomOut);
        buttons.add(Cancel);
    }

    public static final Vector2 getMousePosition() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public static final Vector2 getWorldMousePosition() {
        return new Vector2(Gdx.input.getX() / 16, Gdx.input.getY() / -16);
    }

    public boolean keyDown (int keycode) {
        Vector2 mousePosition = renderer.toMenuPosition(InputHandler.getMousePosition());

        for (Button button : buttons) {
            if (button.getKey() == keycode) {
                ArrayList<Menu> menus = Menus.MENUS.getEntries();
                for (Menu menu : menus) {
                    if (MenuHandler.isShown(menu)) {
                        menu.onButtonDown(button, mousePosition);
                    }
                }

                screenHandler.getScreen().onButtonDown(button);
            }
        }

        return false;
    }

    public boolean keyUp (int keycode) {
        Vector2 mousePosition = renderer.toMenuPosition(InputHandler.getMousePosition());

        for (Button button : buttons) {
            if (button.getKey() == keycode) {
                ArrayList<Menu> menus = Menus.MENUS.getEntries();
                for (Menu menu : menus) {
                    if (MenuHandler.isShown(menu)) {
                        menu.onButtonUp(button, mousePosition);
                    }
                }

                screenHandler.getScreen().onButtonUp(button);
            }
        }

        return false;
    }

    public boolean keyTyped (char character) {
        ArrayList<Menu> menus = Menus.MENUS.getEntries();
        for (Menu menu : menus) {
            if (MenuHandler.isShown(menu)) {
                menu.onType(character);
            }
        }

        screenHandler.getScreen().onType(character);

        return true;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        Vector2 clickPosition = new Vector2(x, y);

        if (button == Input.Buttons.LEFT) {
            Vector2 menuClickPosition = renderer.toMenuPosition(clickPosition);

            screenHandler.getScreen().onMouseLeftDown(clickPosition);

            ArrayList<Menu> menus = Menus.MENUS.getEntries();
            for (Menu menu : menus) {
                if (MenuHandler.isShown(menu)) {
                    menu.onMouseLeftDown(menuClickPosition);
                }
            }

            return true;
        }
        else if (button == Input.Buttons.RIGHT) {
            Vector2 menuClickPosition = renderer.toMenuPosition(clickPosition);

            screenHandler.getScreen().onMouseRightDown(clickPosition);

            ArrayList<Menu> menus = Menus.MENUS.getEntries();
            for (Menu menu : menus) {
                if (MenuHandler.isShown(menu)) {
                    menu.onMouseRightDown(menuClickPosition);
                }
            }

            return true;
        }

        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        Vector2 clickPosition = renderer.toMenuPosition(new Vector2(x, y));

        if (button == Input.Buttons.LEFT) {
            ArrayList<Menu> menus = Menus.MENUS.getEntries();
            for (Menu menu : menus) {
                if (MenuHandler.isShown(menu)) {
                    menu.onMouseLeftUp(clickPosition);
                }
            }

            return true;
        }
        else if (button == Input.Buttons.RIGHT) {
            Vector2 menuClickPosition = renderer.toMenuPosition(clickPosition);

            screenHandler.getScreen().onMouseRightDown(clickPosition);

            ArrayList<Menu> menus = Menus.MENUS.getEntries();
            for (Menu menu : menus) {
                if (MenuHandler.isShown(menu)) {
                    menu.onMouseRightUp(menuClickPosition);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (float amountX, float amountY) {
        int direction = (int)amountY;

        screenHandler.getScreen().onScroll(direction);

        ArrayList<Menu> menus = Menus.MENUS.getEntries();
        for (Menu menu : menus) {
            if (MenuHandler.isShown(menu)) {
                menu.onScroll(direction);
            }
        }

        return false;
    }

    private void addButton(Button button) {
        buttons.add(button);
    }

    public void setInputHandler() {
        Gdx.input.setInputProcessor(this);
    }

    public final ArrayList<Button> getButtons() {
        return this.buttons;
    }
}
