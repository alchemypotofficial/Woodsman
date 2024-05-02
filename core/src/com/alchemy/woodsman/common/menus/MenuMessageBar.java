package com.alchemy.woodsman.common.menus;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.graphics.Text;
import com.alchemy.woodsman.core.handlers.CommandHandler;
import com.alchemy.woodsman.core.handlers.InputHandler;
import com.alchemy.woodsman.core.handlers.MenuHandler;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Menus;
import com.alchemy.woodsman.core.utilities.Message;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class MenuMessageBar extends Menu {
    protected EntityPlayer player;

    protected String message;
    private int messageListPos;

    public MenuMessageBar(String id) {
        super(id);

        message = "";
        messageListPos = -1;
    }

    public void tick() {

    }

    public void render(Renderer renderer, Vector2 mousePosition) {
        renderer.addMenuText(new Text(message, new Vector2(-400, -175), 50, 1f, new Color(1, 1, 1, 1), false, 1));
    }

    @Override
    public void onShow() {
        MenuHandler.show(Menus.MENU_MESSAGE_LIST);

        message = "";
        messageListPos = -1;
    }

    @Override
    public void onHide() {
        message = "";
        messageListPos = -1;
    }

    @Override
    public void onButtonDown(InputHandler.Button button, Vector2 mousePosition) {
        if (button == InputHandler.Enter) {
            if (message != "") {
                boolean usedCommand = CommandHandler.useCommand(message);
                if (!usedCommand) {
                    MessageHandler.sendMessage(new Message("Player", message));
                }
            }

            message = "";
            messageListPos = -1;
        }
        else if (button == InputHandler.ArrowDown) {
            if (CommandHandler.getTotalMessages() > 0 && messageListPos < 0) {
                messageListPos = CommandHandler.getTotalMessages() - 1;

                message = CommandHandler.getMessage(messageListPos);
            }
            else if (CommandHandler.getTotalMessages() > 0 && messageListPos > 0) {
                messageListPos -= 1;

                message = CommandHandler.getMessage(messageListPos);
            }
        }
        else if (button == InputHandler.ArrowUp) {
            if (CommandHandler.getTotalMessages() > 0 && messageListPos < CommandHandler.getTotalMessages() - 1) {
                messageListPos += 1;

                message = CommandHandler.getMessage(messageListPos);
            }
        }
    }

    @Override
    public void onButtonUp(InputHandler.Button button, Vector2 mousePosition) {

    }

    @Override
    public void onType(char character) {
        if (character != '\n') {
            if (character == '\b') {
                if (message.length() > 0) {
                    message = message.substring(0, message.length() - 1);
                }
            }
            else {
                message += character;
            }
        }
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
