package com.alchemy.woodsman.common.blockstates;

import com.alchemy.woodsman.common.items.Inventory.Container;

public class BlockStateContainer extends BlockState {

    public Container container;

    public BlockStateContainer(int maxHealth, Container container) {
        super(maxHealth);

        this.container = container;
    }
}
