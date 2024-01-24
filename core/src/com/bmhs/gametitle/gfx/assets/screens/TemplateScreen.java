package com.bmhs.gametitle.gfx.assets.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.bmhs.gametitle.game.assets.worlds.World;
import com.bmhs.gametitle.game.utils.GameHandler;
import com.bmhs.gametitle.gfx.assets.tiles.Tile;

public class TemplateScreen implements Screen {

    private GameHandler game;
    private Screen parent;

    private Stage stage;

    private OrthographicCamera camera;
    private Viewport viewport;
    private float effectiveViewportWidth, effectiveViewportHeight;

    private World world;

    public TemplateScreen(final GameHandler game, final Screen parent) {
        this.game = game;
        this.parent = parent;

        stage = new Stage();
        createScreenLabel();
        createScreenButton();

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new World();

        setCameraLimits();
        camera.update();
    }

    @Override
    public void show() {
        Gdx.app.error("TemplateScreen", "loaded");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        setCameraLimits();
        camera.update();

        checkInput();

        int rowStart = (int)((camera.position.y/32-effectiveViewportHeight/64)/2);
        int rowEnd = (int)(camera.position.y/64+(effectiveViewportHeight/64/2));
        int colStart = (int)((camera.position.x/32-effectiveViewportWidth/64)/2);
        int colEnd = (int)(camera.position.x/64+(effectiveViewportWidth/64/2));

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for(int r = rowStart; r < rowEnd; r++) {
            for(int c = colStart; c < colEnd; c++) {
                game.batch.draw(world.getWorldTileTextureRegion(r,c), c * Tile.ON_SCREEN_DEFAULT_WIDTH, r * Tile.ON_SCREEN_DEFAULT_HEIGHT);
            }
        }

        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 20*camera.zoom, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-20*camera.zoom, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -20*camera.zoom, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(20*camera.zoom, 0, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom += 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.2;
        }
    }

    private void setCameraLimits() {
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, ((float)world.getWorldMapRows()*(float)Tile.ON_SCREEN_DEFAULT_HEIGHT/(float)Gdx.graphics.getHeight()));

        // world.getWorldMapRows()*Tile.ON_SCREEN_DEFAULT_HEIGHT)/Gdx.graphics.getHeight()
        effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2.0f, world.getWorldMapColumns()*Tile.ON_SCREEN_DEFAULT_WIDTH - effectiveViewportWidth/2.0f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2.0f, world.getWorldMapRows()*Tile.ON_SCREEN_DEFAULT_HEIGHT - effectiveViewportHeight/2.0f);
    }

    private void createScreenLabel() {
        Label renderLayerLabel = new Label("Render Layer ", game.skin);
        renderLayerLabel.setAlignment(Align.left);
        renderLayerLabel.setWidth(100);
        renderLayerLabel.setHeight(25);
        renderLayerLabel.setPosition(20, Gdx.graphics.getHeight()-45);
        stage.addActor(renderLayerLabel);
    }

    private void createScreenButton() {
        TextButton backButton = new TextButton("Return", game.skin, "default");
        backButton.setWidth(150);
        backButton.setHeight(25);
        backButton.setPosition(20, 20);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(parent);            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
    }
}
