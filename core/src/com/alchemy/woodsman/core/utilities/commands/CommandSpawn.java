package com.alchemy.woodsman.core.utilities.commands;

import com.alchemy.woodsman.common.entities.Entity;
import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.handlers.LangHandler;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Entities;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.Message;
import com.alchemy.woodsman.core.world.World;
import com.badlogic.gdx.math.Vector2;

public class CommandSpawn extends Command {

    public CommandSpawn(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean onParse(World world, EntityPlayer player, String[] parameters) {
        if (parameters.length > 0) {
            if (Entities.ENTITIES.hasEntry(parameters[0])) {
                Entity entity = Entities.ENTITIES.getEntry(parameters[0]);

                if (parameters.length > 1) {
                    if (isInteger(parameters[1])) {
                        int amount = Integer.parseInt(parameters[1]);

                        if (amount > 0) {
                            for (int i = 0; i < amount; i++) {
                                Entity spawnedEntity = world.spawnEntity(entity.spawn(world, new Vector2(player.getPosition().x, player.getPosition().y + 0.001f)));
                            }

                            String message = "Player has spawned " + amount + " \"" + LangHandler.getLocalName(entity.getName()) + "\".";
                            Debug.logNormal("Command", message);
                            MessageHandler.sendMessage(new Message("System", message));

                            return true;
                        }
                    }
                }
                else {
                    String message = "Player has spawned \"" + entity.getName() + "\".";
                    Debug.logNormal("Command", message);
                    MessageHandler.sendMessage(new Message("System", message));

                    Entity spawnedEntity = world.spawnEntity(entity.spawn(world, new Vector2(player.getPosition().x, player.getPosition().y + 0.001f)));
                    MessageHandler.sendMessage(new Message("System", "Spawned " + spawnedEntity.getID() + " at " + MessageHandler.formatVector(player.getPosition())));

                    return true;
                }
            }
        }

        String message = "Could not spawn nothing.";
        Debug.logNormal("Command", message);
        MessageHandler.sendMessage(new Message("System", message));
        return false;
    }
}
