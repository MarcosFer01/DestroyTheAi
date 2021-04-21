package com.destroytheai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

	private AssetManager manager;

	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public MenuScreen menuScreen;
	public LoadingScreen loadingScreen;

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("knight_idle_anim_f0.png", Texture.class);
		manager.load("slime_idle_anim_f0.png", Texture.class);
		manager.load("floor_1.png", Texture.class);
		manager.load("wall_1.png", Texture.class);
		manager.load("gameover.png", Texture.class);
		manager.load("stair_nextlevel.png", Texture.class);

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);


	}

	public void cargaCompleta(){
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}