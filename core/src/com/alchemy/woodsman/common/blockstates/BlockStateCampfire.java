package com.alchemy.woodsman.common.blockstates;

import com.alchemy.woodsman.common.items.Inventory.Container;

public class BlockStateCampfire extends BlockState {

    public Container container;
    public boolean lit;
    public int fuel;
    public int initialFuel;

    public BlockStateCampfire(int maxHealth, Container container) {
        super(maxHealth);

        this.container = container;
    }
}
