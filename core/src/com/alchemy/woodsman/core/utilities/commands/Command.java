package com.alchemy.woodsman.core.utilities.commands;

import com.alchemy.woodsman.common.entities.EntityPlayer;
import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.utilities.UsefulMath;
import com.alchemy.woodsman.core.world.World;

public class Command extends Registerable {

    public String name;

    public Command(String id, String name) {
        super(id);

        this.name = name;
    }

    public boolean onParse(World world, EntityPlayer player, String[] parameters) {
        return false;
    }

    public static boolean isInteger(String parameter) {
        return UsefulMath.isInteger(parameter);
    }

    public final String getName() {
        return this.name;
    }
}
