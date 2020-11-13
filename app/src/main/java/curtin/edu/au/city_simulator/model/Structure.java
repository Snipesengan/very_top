/*
Structure.java
Date created : 30.10.2020
Author: Duy Tran
Purpose: Abstract structure class
 */

package curtin.edu.au.city_simulator.model;

import java.io.Serializable;

/*
Structure
abstract class for Structure
ref : practical 3 Structure.java
 */
public abstract class Structure implements Serializable {
    private final int drawableId;
    private final String label;

    public Structure(int drawableId, String label)
    {
        this.drawableId = drawableId;
        this.label = label;

    }

    public int getDrawableId()
    {
        return drawableId;
    }

    public String getLabel()
    {
        return label;
    }

    public int getCost()
    {
        int cost;
        GameData gameData = GameData.getInstance();
        Settings settings = gameData.getSettings();
        switch (label) {
            case "ROAD":
                cost = settings.getRoadBuildingCost();
                break;
            case "COMMERCIAL":
                cost = settings.getCommBuildingCost();
                break;
            case "RESIDENTIAL":
                cost = settings.getHouseBuildingCost();
                break;
            default:
                cost = 0;
                break;
        }

        return cost;
    }
}
