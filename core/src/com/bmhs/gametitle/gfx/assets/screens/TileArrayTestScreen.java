package com.bmhs.gametitle.gfx.assets.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bmhs.gametitle.game.utils.GameHandler;
import com.bmhs.gametitle.gfx.assets.tiles.Tile;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public class TileArrayTestScreen implements Screen {

    GameHandler game;
    Screen parent;
    Array<WorldTile> tileArray;

    WorldTile[][] renderArray;
    BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;

    public TileArrayTestScreen(GameHandler game, Screen parent, Array<WorldTile> tileArray) {
        this.game = game;
        this.parent = parent;
        this.tileArray = tileArray;

        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.zoom = 64;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderArray = new WorldTile[12][20];

        int index = 0;
        for(int r = 0; r < 12; r++) {
            for(int c = 0; c < 20; c++) {
                renderArray[r][c] = tileArray.get(index++);
                if(index > tileArray.size-1)
                    index = tileArray.size-1;
            }
        }
    }

    @Override
    public void show() {
        Gdx.app.log("TileArrayScreen", "loaded");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1);

        camera.position.set(0, 0, 0);
        camera.zoom = 0;

        //rendering
        int indexToPrint = 0;

        game.batch.begin();
        for(int r = 0; r < renderArray.length; r++) {
            for(int c = 0; c < renderArray[r].length; c++) {
                game.batch.draw(renderArray[r][c].getTexture(), c* Tile.ON_SCREEN_DEFAULT_WIDTH, r*Tile.ON_SCREEN_DEFAULT_HEIGHT);
                font.draw(game.batch, ""+indexToPrint++, c*Tile.ON_SCREEN_DEFAULT_WIDTH+(Tile.ON_SCREEN_DEFAULT_WIDTH/2), r*Tile.ON_SCREEN_DEFAULT_HEIGHT+(Tile.ON_SCREEN_DEFAULT_HEIGHT/2));
            }
        }
        game.batch.end();

        //input
        //switch screens with escape key
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(parent);
        }

        //screenshot with num 0
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)){
            Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
            ByteBuffer pixels = pixmap.getPixels();

            int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
            for (int i = 3; i < size; i += 4) {
                pixels.put(i, (byte) 255);
            }

            PixmapIO.writePNG(Gdx.files.external("\\Desktop\\screenshot.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
            pixmap.dispose();
        }
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

    }
}
