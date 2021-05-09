package com.destroytheai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

	private AssetManager manager;
	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public MenuScreen menuScreen;
	public LoadingScreen loadingScreen;
	public PersonajesScreen personajesScreen;
	public OpcionesScreen opcionesScreen;
	public EstadisticasScreen estadisticasScreen;
	public Cinematica1Screen cinematica1screen;
	public Cinematica2Screen cinematica2screen;

	public AssetManager getManager() {
		return manager;
	}
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	@Override
	/**
	 * En el creador del juego,se muestra la pantalla de carga mientras se cargan todas las texturas y sonidos necesarios
	 */
	public void create() {
		manager = new AssetManager();
		manager.load("knight_idle_anim_f0.png", Texture.class);
		manager.load("knight_idle_anim_f0R.png", Texture.class);
		manager.load("elf_f_hit_anim_f0.png", Texture.class);
		manager.load("elf_f_hit_anim_f0R.png", Texture.class);
		manager.load("wizzard_f_hit_anim_f0.png", Texture.class);
		manager.load("wizzard_f_hit_anim_f0R.png", Texture.class);
		manager.load("slime_idle_anim_f0.png", Texture.class);
		manager.load("goblin_idle_anim_f0.png", Texture.class);
		manager.load("medic.png", Texture.class);
		manager.load("fly_anim_f1.png", Texture.class);
		manager.load("floor_1.png", Texture.class);
		manager.load("floor_5.png", Texture.class);
		manager.load("wall_1.png", Texture.class);
		manager.load("gameover.png", Texture.class);
		manager.load("stair_nextlevel.png", Texture.class);
		manager.load("key_silver.png", Texture.class);

		manager.load("bubble2.ogg", Sound.class);
		manager.load("metal-small3.ogg", Sound.class);
		manager.load("coin.ogg", Sound.class);
		manager.load("giant5.ogg", Sound.class);
		manager.load("magic.ogg", Sound.class);
		manager.load("ogre2.ogg", Sound.class);
		manager.load("slime8.ogg", Sound.class);
		manager.load("swing.ogg", Sound.class);
		manager.load("musica.ogg", Music.class);

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	/**
	 * Este método se encarga de crear todas las pantallas necesarias para el juego y mostrar la del menú principal
	 */
	public void cargaCompleta(){
		personajesScreen = new PersonajesScreen(this);
		gameScreen = new GameScreen(this, 1);
		gameOverScreen = new GameOverScreen(this);
		opcionesScreen = new OpcionesScreen(this);
		estadisticasScreen = new EstadisticasScreen(this);
		cinematica1screen = new Cinematica1Screen(this);
		cinematica2screen = new Cinematica2Screen(this);

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}