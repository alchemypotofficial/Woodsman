package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.common.items.Inventory.ItemStack;
import com.alchemy.woodsman.common.items.Item;
import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.init.Items;
import com.alchemy.woodsman.core.init.Recipes;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.loot.Loot;
import com.alchemy.woodsman.core.utilities.loot.LootItem;
import com.alchemy.woodsman.core.utilities.recipes.RecipeCampfire;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class LootHandler {

    private static Registry<Loot> LOOTS = new Registry<>();

    public void loadLoots() {
        FileHandle lootFolder = Game.getInternalFile("assets/data/loot/");

        if (lootFolder != null) {
            FileHandle[] lootFiles = lootFolder.list();
            for (FileHandle lootFile : lootFiles) {
                if (!lootFile.isDirectory()) {
                    Debug.logNormal("Loot", "Loading loot \"" + lootFile.name() + "\".");
                    loadLoot(lootFile);
                }
                else {
                    //* Load loot from sub-folder.
                    loadSubFolder(lootFile.path());
                }
            }
        }
    }

    private void loadSubFolder(String path) {
        FileHandle lootFolder = Game.getInternalFile(path);

        if (lootFolder != null) {
            FileHandle[] lootFiles = lootFolder.list();
            for (FileHandle lootFile : lootFiles) {
                if (!lootFile.isDirectory()) {
                    Debug.logState("Loot", "Loading loot \"" + lootFile.name() + "\".");
                    loadLoot(lootFile);
                }
                else {
                    //* Load loot from sub-folder.
                    loadSubFolder(lootFile.path());
                }
            }
        }
    }

    private void loadLoot(FileHandle recipeFile) {
        if (recipeFile != null) {
            JsonReader json = new JsonReader();
            JsonValue jsonBase = json.parse(recipeFile);

            if (!jsonBase.has("id")) {
                Debug.logError("Could not find loot id in \"" + recipeFile.name() + "\".");
                return;
            }

            String id = jsonBase.getString("id");

            if (!jsonBase.has("items")) {
                Debug.logError("Could not find loot items in \"" + recipeFile.name() + "\".");
                return;
            }

            JsonValue itemLootValues = jsonBase.get("items");

            Loot loot = new Loot(id);

            for (JsonValue itemLootValue : itemLootValues) {
                if (!itemLootValue.has("item")) {
                    Debug.logError("Ingredient has no item in \"" + recipeFile.name() + "\".");
                    return;
                }
                else if (!itemLootValue.has("min")) {
                    Debug.logError("Ingredient has no min in \"" + recipeFile.name() + "\".");
                    return;
                }
                else if (!itemLootValue.has("max")) {
                    Debug.logError("Ingredient has no max in \"" + recipeFile.name() + "\".");
                    return;
                }

                String itemID = itemLootValue.getString("item");
                int min = itemLootValue.getInt("min");
                int max = itemLootValue.getInt("max");

                if (Items.ITEMS.hasEntry(itemID) && min >= 0 && min <= max) {
                    Item item = Items.ITEMS.getEntry(itemID);
                    LootItem lootItem = new LootItem(item, min, max);

                    loot.addLootItem(lootItem);
                }
                else {
                    Debug.logError("Could not load loot item in \"" + recipeFile.name() + "\".");
                }
            }

            LOOTS.register(loot);

            return;
        }

        Debug.logError("Could not load null loot.");
    }

    public static final ArrayList<Loot> getLoots() {
        return new ArrayList<Loot>(LOOTS.getEntries());
    }

    public static final Loot getLoot(String id) {
        return LOOTS.getEntry(id);
    }
}
