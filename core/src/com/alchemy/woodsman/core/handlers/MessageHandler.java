package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.common.blocks.Block;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.utilities.BlockPosition;
import com.alchemy.woodsman.core.utilities.Message;
import com.badlogic.gdx.math.Vector2;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MessageHandler {

    private static ArrayList<Message> messages = new ArrayList<>();

    public static void sendMessage(Message message) {
        messages.add(0, message);
    }

    public static final String formatBoolean(boolean bool) {
        return String.valueOf(bool).substring(0, 1).toUpperCase() + String.valueOf(bool).substring(1);
    }

    public static final String formatVector(Vector2 position) {
        DecimalFormat decimalFormat = Renderer.getDecimalFormat();

        return "(" + decimalFormat.format(position.x) + ", " + decimalFormat.format(position.y) + ")";
    }

    public static final String formatFloat(float i) {
        DecimalFormat decimalFormat = Renderer.getDecimalFormat();

        return decimalFormat.format(i);
    }

    public static final String formatBlockPosition(BlockPosition blockPosition) {
        DecimalFormat decimalFormat = Renderer.getDecimalFormat();

        return "(" + decimalFormat.format(blockPosition.x) + ", " + decimalFormat.format(blockPosition.y) + ")";
    }

    public static final ArrayList<Message> getMessages() {
        return messages;
    }
}