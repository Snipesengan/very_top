package curtin.edu.au.city_simulator.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.MapElement;
import curtin.edu.au.city_simulator.model.Settings;
import curtin.edu.au.city_simulator.model.Structure;
import curtin.edu.au.city_simulator.model.StructureData;

public class DBCursor extends CursorWrapper {
    private Context context;

    public DBCursor(Cursor c, Context context) {
        super(c);
        this.context = context;
    }

    public Settings loadSettings() {
        Settings settings;
        String cityName;
        int mapWidth, mapHeight, initialMoney, familySize, shopSize, salary, taxRate, serviceCost,
                houseBuildingCost, commBuildingCost, roadBuildingCost;

        cityName = getString(getColumnIndex(GameDBSchema.CITY_NAME));
        mapWidth = getInt(getColumnIndex(GameDBSchema.MAP_WIDTH));
        mapHeight = getInt(getColumnIndex(GameDBSchema.MAP_HEIGHT));

        initialMoney = getInt(getColumnIndex(GameDBSchema.INITIAL_MONEY));
        familySize = getInt(getColumnIndex(GameDBSchema.FAMILY_SIZE));
        shopSize = getInt(getColumnIndex(GameDBSchema.SHOP_SIZE));
        salary = getInt(getColumnIndex(GameDBSchema.SALARY));
        taxRate = getInt(getColumnIndex(GameDBSchema.TAX_RATE));
        serviceCost = getInt(getColumnIndex(GameDBSchema.SERVICE_COST));
        houseBuildingCost = getInt(getColumnIndex(GameDBSchema.HOUSE_BUILDING_COST));
        commBuildingCost = getInt(getColumnIndex(GameDBSchema.COMM_BUILDING_COST));
        roadBuildingCost = getInt(getColumnIndex(GameDBSchema.ROAD_BUILDING_COST));

        settings = new Settings(cityName, mapWidth, mapHeight, initialMoney, familySize, shopSize,
                salary, taxRate, serviceCost, houseBuildingCost, commBuildingCost, roadBuildingCost);

        return settings;
    }

    public void loadMap(MapElement[][] mapElements) {
        int x, y , nw, ne, sw, se, id;

        x = getInt(getColumnIndex(GameDBSchema.MapElementSchema.X_POS));
        y = getInt(getColumnIndex(GameDBSchema.MapElementSchema.Y_POS));
        nw = getInt(getColumnIndex(GameDBSchema.MapElementSchema.NW));
        ne = getInt(getColumnIndex(GameDBSchema.MapElementSchema.NE));
        sw = getInt(getColumnIndex(GameDBSchema.MapElementSchema.SW));
        se = getInt(getColumnIndex(GameDBSchema.MapElementSchema.SE));
        id = getInt(getColumnIndex(GameDBSchema.MapElementSchema.ID));

        mapElements[y][x] = new MapElement(false, nw, ne, sw, se, null, x, y);

        if (id != -1) {
            mapElements[y][x].setStructure(StructureData.getStructureWithId(id));
        }

        System.out.println(id);

    }

    public int getXPos() {
        return getInt(getColumnIndex(GameDBSchema.MapElementSchema.X_POS));
    }

    public int getYPos() {
        return getInt(getColumnIndex(GameDBSchema.MapElementSchema.Y_POS));
    }

    public int loadTime() {
        return getInt(getColumnIndex(GameDBSchema.TIME));
    }

    public int loadPopulation() {
        return  getInt(getColumnIndex(GameDBSchema.POPULATION));
    }

    public int loadEmploymentRate() {
        return getInt(getColumnIndex(GameDBSchema.EMPLOYMENT_RATE));
    }
}