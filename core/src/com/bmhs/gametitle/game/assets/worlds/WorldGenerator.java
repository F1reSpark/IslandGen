package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;
    private int s;
    private int xcoord;
    private int ycoord;
    Vector2 mapseed;
    private int elevtationnum = 14;



    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;


        worldIntMap = new int[worldMapRows][worldMapColumns];
        createBackground();

        for (int seednum = 0; seednum < 7; seednum++) {
            Vector2 mapseed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
            System.out.println(mapseed.y + " " + mapseed.x);

                for (int r = 1; r < worldIntMap.length - 1; r++) {
                    for (int c = 1; c < worldIntMap[r].length - 1; c++) {
                        Vector2 tempvector = new Vector2(c, r);
                        if (tempvector.dst(mapseed) < 1) {
                            worldIntMap[r][c] = elevtationnum;

                        }
                    }
                }


        }

        //call methods to build 2D array

        islandBuild();
        generateWorldTextFile();
        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void createBackground() {
        for (int r = 0; r < worldIntMap.length - 1; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = 0;
            }
        }
    }
    protected int currentelevation = elevtationnum;
    public void islandBuild() {

        while(currentelevation > 3) {
            for (int r = 1; r < worldIntMap.length - 2; r++) {
                for (int c = 1; c < worldIntMap[r].length - 2; c++) {
                    if (worldIntMap[r][c + 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r + 1][c] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r - 1][c - 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r][c - 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r + 1][c + 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r - 1][c + 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r - 1][c] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    } else if (worldIntMap[r + 1][c - 1] == currentelevation) {
                        if(worldIntMap[r][c] < currentelevation) {
                            if (MathUtils.random(0, 4) == 4) {
                                worldIntMap[r][c] = currentelevation;
                            } else {
                                worldIntMap[r][c] = currentelevation - 1;
                            }
                        }
                    }

                }
                //worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
            currentelevation--;
        }

             //islandBuild();
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }
    private void generateWorldTextFile(){
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }

}
