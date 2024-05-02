package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.Game;
import com.alchemy.woodsman.core.init.Recipes;
import com.alchemy.woodsman.core.utilities.Debug;
import com.alchemy.woodsman.core.utilities.recipes.types.RecipeType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class RecipeHandler {

    public void loadRecipes() {
        ArrayList<RecipeType> recipeTypes = Recipes.RECIPE_TYPES.getEntries();
        for (RecipeType recipeType : recipeTypes) {
            FileHandle recipesFolder = Game.getInternalFile("assets/data/recipes/" + recipeType.getPath());

            if (recipesFolder != null) {
                FileHandle[] recipeFiles = recipesFolder.list();
                for (FileHandle recipeFile : recipeFiles) {
                    if (!recipeFile.isDirectory()) {
                        Debug.logAlert("Loading recipe \"" + recipeFile.name() + "\".");

                        loadRecipe(recipeFile);
                    }
                }
            }
        }
    }

    private void loadRecipe(FileHandle recipeFile) {
        if (recipeFile != null) {
            JsonReader json = new JsonReader();
            JsonValue jsonBase = json.parse(recipeFile);

            if (!jsonBase.has("type")) {
                Debug.logError("Could not find recipe type in \"" + recipeFile.name() + "\".");
                return;
            }

            String recipeTypeValue = jsonBase.getString("type");

            if (!jsonBase.has("ingredients")) {
                Debug.logError("Could not find recipe ingredients in \"" + recipeFile.name() + "\".");
                return;
            }

            if (Recipes.RECIPE_TYPES.hasEntry(recipeTypeValue)) {
                RecipeType recipeType = Recipes.RECIPE_TYPES.getEntry(recipeTypeValue);
                recipeType.loadRecipe(jsonBase);
            }
            else {
                Debug.logError("Could not find recipe type \"" + recipeTypeValue + "\".");
                return;
            }

            return;
        }

        Debug.logError("Could not load null recipe.");
    }
}
