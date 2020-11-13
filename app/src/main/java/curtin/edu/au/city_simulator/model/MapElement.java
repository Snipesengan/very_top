/*
MapElement
Date created : 30.10.2020
Author: Duy Tran
Purpose:
 */

package curtin.edu.au.city_simulator.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

import curtin.edu.au.city_simulator.database.GameDBHelper;
import curtin.edu.au.city_simulator.model.Structure;

/*
MapElement
Represents single grid in square map
ref : practical 3 MapElement.java
 */
public class MapElement implements Serializable {
    private boolean buildable = true;
    private final int terrainNorthWest;
    private final int terrainSouthWest;
    private final int terrainNorthEast;
    private final int terrainSouthEast;
    private Structure structure;
    private int x, y;


    public MapElement(boolean buildable, int northWest, int northEast,
                      int southWest, int southEast, Structure structure, int x, int y)
    {
        this.buildable = buildable;
        this.terrainNorthWest = northWest;
        this.terrainNorthEast = northEast;
        this.terrainSouthWest = southWest;
        this.terrainSouthEast = southEast;
        this.structure = structure;
        this.x = x;
        this.y = y;
    }

    public int getNorthWest()
    {
        return terrainNorthWest;
    }

    public int getSouthWest()
    {
        return terrainSouthWest;
    }

    public int getNorthEast()
    {
        return terrainNorthEast;
    }

    public int getSouthEast()
    {
        return terrainSouthEast;
    }

    public boolean isBuildable() {
        return buildable;
    }

    /**
     * Retrieves the structure built on this map element.
     * @return The structure, or null if one is not present.
     */
    public Structure getStructure()
    {

        return structure;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

