package com.alchemy.woodsman;

import com.alchemy.woodsman.core.Settings;
import com.alchemy.woodsman.core.graphics.Renderer;
import com.alchemy.woodsman.core.handlers.*;
import com.alchemy.woodsman.core.init.*;
import com.alchemy.woodsman.core.screens.ScreenGame;
import com.alchemy.woodsman.core.world.World;

public class Woodsman {

	public Renderer renderer;
	public SoundHandler soundHandler;
	public ScreenHandler screenHandler;
	public RecipeHandler recipeHandler;
	public LootHandler lootHandler;
	public LangHandler langHandler;
	public InputHandler inputHandler;
	public MessageHandler messageHandler;
	public CommandHandler commandHandler;
	public MenuHandler menuHandler;
	public World world;

	public void preInit() {

	}

	public void init() {
		Tags.register();
		Tiers.register();
		Floors.register();
		Blocks.register();
		Items.register();
		Entities.register();
		Menus.register();
		Commands.register();
		Recipes.register();
	}

	public void postInit() {
		renderer = new Renderer();
		soundHandler = new SoundHandler();
		screenHandler = new ScreenHandler();
		langHandler = new LangHandler();
		recipeHandler = new RecipeHandler();
		lootHandler = new LootHandler();
		messageHandler = new MessageHandler();
		commandHandler = new CommandHandler();
		menuHandler = new MenuHandler();

		renderer.load();

		langHandler.loadLocalNames();

		recipeHandler.loadRecipes();
		lootHandler.loadLoots();

		inputHandler = new InputHandler(renderer, screenHandler);
		inputHandler.setInputHandler();

		screenHandler.setScreen(new ScreenGame(renderer));
	}

	public void tick() {
		screenHandler.tick();
		menuHandler.tick();
	}

	public void physics() {
		screenHandler.getScreen().physics();
	}

	public void render() {
		//* Render both the screen and menus.
		screenHandler.render(renderer);
		menuHandler.render(renderer);

		//* New render system.
		renderer.render();
	}

	public void dispose() {
		renderer.dispose();
		soundHandler.dispose();
	}
}