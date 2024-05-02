package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.init.Commands;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.Message;
import com.alchemy.woodsman.core.utilities.commands.Command;
import com.alchemy.woodsman.core.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandHandler {

    private static World world;
    private static ArrayList<String> messageList = new ArrayList<>();

    public static boolean useCommand(String message) {
        messageList.add(message);

        if (message.length() > 0 && message.charAt(0) == '/') {
            String[] rawParameters = message.split("\\s+");

            String name = rawParameters[0].substring(1);
            String[] parameters = Arrays.copyOfRange(message.split("\\s+"), 1, rawParameters.length);

            ArrayList<Command> commands = Commands.COMMANDS.getEntries();
            for (Command command : commands) {
                if (command.getName().equals(name)) {
                    EntityPlayer player = world.getPlayer();

                    if (player != null) {
                        if (command.onParse(world, player, parameters)) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }

            MessageHandler.sendMessage(new Message("Player", "Could not find command."));
        }

        return false;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public static String getMessage(int index) {
        return messageList.get(index);
    }

    public static int getTotalMessages() {
        return messageList.size();
    }
}
