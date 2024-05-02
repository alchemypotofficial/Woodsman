package com.alchemy.woodsman.common.items.Tools;

public class ItemFireStarter extends ItemTool {

    public ItemFireStarter(String id, String staticName, String texturePath, int maxAmount, String tier) {
        super(id, staticName, texturePath, maxAmount, tier);

        useTime = 2.75f;
    }
}
