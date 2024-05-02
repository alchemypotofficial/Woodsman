package com.alchemy.woodsman.core.utilities.commands;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.Message;
import com.alchemy.woodsman.core.world.World;

public class CommandTime extends Command {

    public CommandTime(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean onParse(World world, EntityPlayer player, String[] parameters) {
        if (parameters.length > 0) {
            if (isInteger(parameters[0])) {
                int time = Integer.parseInt(parameters[0]);

                if (time >= 0 && time < 24000) {
                    world.setTime(time);

                    String message = "Set time to \"" + time + "\".";
                    Debug.logNormal("Command", message);
                    MessageHandler.sendMessage(new Message("System", message));
                    return true;
                }
                else {
                    String message = "Could not set time outside of 23999 ticks.";
                    Debug.logNormal("Command", message);
                    MessageHandler.sendMessage(new Message("System", message));
                    return false;
                }
            }
            else if (parameters[0].equals("morning")){
                world.setTime(6000);

                String message = "Set time to morning.";
                Debug.logNormal("Command", message);
                MessageHandler.sendMessage(new Message("System", message));
                return true;
            }
            else if (parameters[0].equals("day")){
                world.setTime(12000);

                String message = "Set time to day.";
                Debug.logNormal("Command", message);
                MessageHandler.sendMessage(new Message("System", message));
                return true;
            }
            else if (parameters[0].equals("night")){
                world.setTime(20000);

                String message = "Set time to night.";
                Debug.logNormal("Command", message);
                MessageHandler.sendMessage(new Message("System", message));
                return true;
            }
            else {
                String message = "Could not set time to \"" + parameters[0] + "\".";
                Debug.logNormal("Command", message);
                MessageHandler.sendMessage(new Message("System", message));

                return false;
            }
        }

        String message = "Could not set time to nothing.";
        Debug.logNormal("Command", message);
        MessageHandler.sendMessage(new Message("System", message));
        return false;
    }
}
