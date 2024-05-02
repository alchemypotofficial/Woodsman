package com.alchemy.woodsman.core.utilities.recipes.types;

import com.alchemy.woodsman.core.graphics.data.Registerable;
import com.alchemy.woodsman.core.utilities.recipes.Recipe;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class RecipeType extends Registerable {

    protected ArrayList<Recipe> recipes = new ArrayList<>();
    protected String path;

    public RecipeType(String id, String path) {
        super(id);

        this.path = path;
    }

    public void loadRecipe(JsonValue jsonBase) {

    }

    public final ArrayList<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public final String getPath() {
        return this.path;
    }
}
