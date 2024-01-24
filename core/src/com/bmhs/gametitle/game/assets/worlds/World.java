package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.bmhs.gametitle.gfx.assets.tiles.Tile;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

public class World {

    private WorldTile[][] worldTileMap;
    private int worldMapRows, worldMapColumns;

    private WorldGenerator worldGenerator;

    public World () {
        this(Gdx.graphics.getHeight()/Tile.ON_SCREEN_DEFAULT_HEIGHT, Gdx.graphics.getWidth()/Tile.ON_SCREEN_DEFAULT_WIDTH);
        Gdx.app.error("World", "World()");
    }

    public World (int worldMapRows, int worldMapColumns){
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldGenerator = new WorldGenerator(worldMapRows, worldMapColumns);

        worldTileMap = worldGenerator.generateWorld();

        Gdx.app.error("World", "World(int,int)");
    }

    public WorldTile getWorldTile(int r, int c) {
        return worldTileMap[r][c];
    }

    public TextureRegion getWorldTileTextureRegion(int r, int c) {
        return worldTileMap[r][c].getTexture();
    }

    public int getWorldMapRows() {
        return worldMapRows;
    }

    public int getWorldMapColumns() {
        return worldMapColumns;
    }
}