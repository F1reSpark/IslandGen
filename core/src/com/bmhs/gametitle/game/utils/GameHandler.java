package com.bmhs.gametitle.game.utils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

import com.bmhs.gametitle.gfx.assets.screens.TitleScreen;

public class GameHandler implements ApplicationListener {

	protected Screen screen;
	public SpriteBatch batch;
	public Skin skin;
	public float deltaTime;

	@Override
	public void create() {
		batch = new SpriteBatch();
		deltaTime = 0;
		skin = new Skin(Gdx.files.internal("skins/clean-crispy/skin/clean-crispy-ui.json"), new TextureAtlas(Gdx.files.internal("skins/clean-crispy/skin/clean-crispy-ui.atlas")));
		setScreen(new TitleScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null) screen.resize(width, height);
	}

	@Override
	public void render() {
		if (screen != null)
			screen.render(Gdx.graphics.getDeltaTime());
		deltaTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	@Override
	public void dispose() {
		if (screen != null)
			screen.hide();
		batch.dispose();
	}

	public void setScreen(Screen screen) {
		if (this.screen != null)
			this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
}
