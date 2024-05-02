package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.utilities.Message;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MenuMessageList extends Menu {
    public MenuMessageList(String id) {
        super(id);
    }

    public float maxShowTime = 3f;
    public float currentShowTime = 0f;

    @Override
    public void tick() {
        if (!MenuHandler.isShown(Menus.MENU_MESSAGE_BAR)) {
            currentShowTime += Game.getDeltaTime();
            if (currentShowTime > maxShowTime) {
                currentShowTime = 0f;

                MenuHandler.hide(Menus.MENU_MESSAGE_LIST);
            }
        }
        else {
            currentShowTime = 0f;
        }
    }

    @Override
    public void render(Renderer renderer, Vector2 mousePosition) {
        ArrayList<Message> messages = MessageHandler.getMessages();

        if (messages.size() >= 8) {
            for (int index = 0; index < 8; index++) {
                Message message = messages.get(index);

                renderer.addMenuText(new Text("<" + message.getSender() + "> " + message.getMessage(), new Vector2(-400, -150 + (index * 25)), 20));
            }
        }
        else {
            for (int index = 0; index < messages.size(); index++) {
                Message message = messages.get(index);

                renderer.addMenuText(new Text("<" + message.getSender() + "> " + message.getMessage(), new Vector2(-400, -150 + (index * 25)), 20));
            }
        }
    }

    @Override
    public void onShow() {
        currentShowTime = 0f;
    }
}
