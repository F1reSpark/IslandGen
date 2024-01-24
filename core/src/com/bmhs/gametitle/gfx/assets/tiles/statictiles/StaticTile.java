package com.bmhs.gametitle.gfx.assets.tiles.statictiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bmhs.gametitle.gfx.assets.tiles.Tile;

public class StaticTile extends Tile {

    private TextureRegion texture;

    private boolean isSolid;

    public StaticTile(TextureRegion texture, int id, String name) {
        super(id, name);
        this.texture = texture;

        isSolid = false;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean getIsSolid() {
        return isSolid;
    }

}
