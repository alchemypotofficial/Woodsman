package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.utilities.commands.Command;
import com.alchemy.woodsman.core.utilities.commands.CommandGive;
import com.alchemy.woodsman.core.utilities.commands.CommandSpawn;
import com.alchemy.woodsman.core.utilities.commands.CommandTime;

public class Commands {

    public static final Registry<Command> COMMANDS = new Registry<Command>();

    public static final CommandGive COMMAND_GIVE = new CommandGive("woodsman.give", "give");
    public static final CommandTime COMMAND_TIME = new CommandTime("woodsman.time", "time");
    public static final CommandSpawn COMMAND_SPAWN = new CommandSpawn("woodsman.spawn", "spawn");

    public static void register() {
        COMMANDS.register(COMMAND_GIVE);
        COMMANDS.register(COMMAND_TIME);
        COMMANDS.register(COMMAND_SPAWN);
    }
}
