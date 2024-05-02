package com.alchemy.woodsman.core.init;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeTypeCampfire;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeTypeCrafting;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeType;

public class Recipes {

    public static final Registry<RecipeType> RECIPE_TYPES = new Registry<>();

    public static final RecipeTypeCrafting CRAFTING = new RecipeTypeCrafting("woodsman.crafting", "crafting");
    public static final RecipeTypeCampfire CAMPFIRE = new RecipeTypeCampfire("woodsman.campfire", "campfire");

    public static void register() {
        RECIPE_TYPES.register(CRAFTING);
        RECIPE_TYPES.register(CAMPFIRE);
    }
}
