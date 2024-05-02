package com.alchemy.woodsman.core.utilities.commands;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.handlers.LangHandler;
import com.alchemy.woodsman.core.handlers.MessageHandler;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.Message;
import com.alchemy.woodsman.core.world.World;

public class CommandGive extends Command {

    public CommandGive(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean onParse(World world, EntityPlayer player, String[] parameters) {
        if (parameters.length > 0) {
            if (Items.ITEMS.hasEntry(parameters[0])) {
                Item item = Items.ITEMS.getEntry(parameters[0]);

                if (parameters.length > 1) {
                    if (isInteger(parameters[1])) {
                        int amount = Integer.parseInt(parameters[1]);

                        if (amount > 0) {
                            String message = "Player has given themselves \"" + LangHandler.getLocalName(item.getStaticName()) + "\".";
                            Debug.logNormal("Command", message);
                            MessageHandler.sendMessage(new Message("System", message));

                            if (amount > item.getMaxAmount()) {
                                int itemStackAmount = amount;
                                while (itemStackAmount > item.getMaxAmount()) {
                                    ItemStack newItem = new ItemStack(item, item.getMaxAmount());

                                    if (!player.getInventory().hasCombinableSlot(newItem)) {
                                        return true;
                                    }

                                    player.getInventory().addItemStack(newItem);
                                    itemStackAmount -= item.getMaxAmount();
                                }

                                player.getInventory().addItemStack(new ItemStack(item, itemStackAmount));
                            } else {
                                player.getInventory().addItemStack(new ItemStack(item, amount));
                            }

                            return true;
                        }
                    }
                }
                else {
                    String message = "Player has given themselves \"" + LangHandler.getLocalName(item.getStaticName()) + "\".";
                    Debug.logNormal("Command", message);
                    MessageHandler.sendMessage(new Message("System", message));

                    player.getInventory().addItemStack(new ItemStack(item, 1));
                    return true;
                }
            }
        }

        String message = "Could not give player nothing.";
        Debug.logNormal("Command", message);
        MessageHandler.sendMessage(new Message("System", message));
        return false;
    }
}
