/*
GameData.java
Author : Duy Tran
Data : 2.11
GameData model class
 */
package curtin.edu.au.city_simulator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.Map;

import curtin.edu.au.city_simulator.database.DBCursor;
import curtin.edu.au.city_simulator.database.GameDBHelper;
import curtin.edu.au.city_simulator.database.GameDBSchema;

/*
singleton
 */
public class GameData implements Serializable {
    private static GameData instance = null;
    private Settings settings;
    private MapElement[][] mapElement;
    private SQLiteDatabase db;

    private int time, population;
    private double employmentRate;
    private boolean demolitionMode;
    private int rCount, cCount;

    /* also resets game data */
    public GameData(Settings mSettings) {

        this.settings = mSettings;
        mapElement = new MapElement[settings.getMapHeight()][settings.getMapWidth()];
        time = 0;
        population = 0;
        demolitionMode = false;
        rCount = 0;
        cCount = 0;

        //todo: init other vars
        generateNewMap();
    }

    public void resetGame()
    {
        mapElement = new MapElement[settings.getMapHeight()][settings.getMapWidth()];
        generateNewMap();
    }

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData(new Settings());
        }
        return instance;
    }

    /*
    do time step
     */
    public void doTimeStep() {
        time++; //inc by 1

        int money = this.settings.getInitialMoney();
        this.settings.setSalary((int)(population * (employmentRate * this.settings.getSalary() * this.settings.getTaxRate() - this.settings.getServiceCost())));
        money += this.settings.getSalary();
        this.settings.setInitialMoney(money); // increment money

        population = this.settings.getFamilySize() * rCount;

        if (population > 0)
        {
            employmentRate = Math.min(1, cCount * this.settings.getShopSize() / population);
        }
    }


    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public MapElement getMapElement(int row, int col) {
        return mapElement[col][row];
    }

    public void generateNewMap() {
        MapData mapData = MapData.get();
        mapData.setHEIGHT(settings.getMapHeight());
        mapData.setWIDTH(settings.getMapWidth());
        mapData.regenerate();

        for (int x = 0; x < settings.getMapWidth(); x++) {
            for (int y = 0; y < settings.getMapHeight(); y++ ) {
                mapElement[y][x] = mapData.get(y,x);
            }
        }
    }


    public int getPopulation() {
        return population;
    }

    public double getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(double employmentRate) {
        this.employmentRate = employmentRate;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void loadGameData(Context context) {
        SQLiteDatabase db = new GameDBHelper(context.getApplicationContext()).getReadableDatabase();
        DBCursor c = new DBCursor(
                db.query(GameDBSchema.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null), context);
        try {
            if (c.getCount() == 0) { //db empty
                saveGameData(context.getApplicationContext());
            } else {
                c.moveToFirst();
                this.settings = c.loadSettings();
                this.time = c.loadTime();
                this.population = c.loadPopulation();
                this.employmentRate = c.loadEmploymentRate();
            }
        } finally {
            c.close(); //close cursor
        }
    }

    /*
    save game data to db by clearing existing db
     */
    public void saveGameData(Context context) {
        GameDBHelper gameDBHelper = new GameDBHelper(context.getApplicationContext());
        GameDBHelper.rebuildDB(context);
        db = gameDBHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(GameDBSchema.CITY_NAME, settings.getCity());
        cv.put(GameDBSchema.MAP_HEIGHT, settings.getMapHeight());
        cv.put(GameDBSchema.MAP_WIDTH, settings.getMapWidth());

        cv.put(GameDBSchema.INITIAL_MONEY, settings.getInitialMoney());
        cv.put(GameDBSchema.FAMILY_SIZE, settings.getFamilySize());
        cv.put(GameDBSchema.SHOP_SIZE, settings.getShopSize());
        cv.put(GameDBSchema.SALARY, settings.getSalary());
        cv.put(GameDBSchema.TAX_RATE, settings.getTaxRate());
        cv.put(GameDBSchema.SERVICE_COST, settings.getServiceCost());
        cv.put(GameDBSchema.HOUSE_BUILDING_COST, settings.getHouseBuildingCost());
        cv.put(GameDBSchema.COMM_BUILDING_COST, settings.getCommBuildingCost());
        cv.put(GameDBSchema.ROAD_BUILDING_COST, settings.getRoadBuildingCost());

        cv.put(GameDBSchema.TIME, this.time);
        cv.put(GameDBSchema.POPULATION, this.population);
        cv.put(GameDBSchema.EMPLOYMENT_RATE, this.employmentRate);

        db.insert(GameDBSchema.NAME, null, cv);

        for (int i = 0; i < settings.getMapHeight(); i++) {
            for (int j = 0; j < settings.getMapWidth(); j++) {
                ContentValues cv2 = new ContentValues();
                MapElement map = mapElement[i][j];

                cv2.put(GameDBSchema.MapElementSchema.X_POS, j);
                cv2.put(GameDBSchema.MapElementSchema.Y_POS, i);
                cv2.put(GameDBSchema.MapElementSchema.NW, map.getNorthWest());
                cv2.put(GameDBSchema.MapElementSchema.NE, map.getNorthEast());
                cv2.put(GameDBSchema.MapElementSchema.SW, map.getSouthWest());
                cv2.put(GameDBSchema.MapElementSchema.SE, map.getNorthEast());

                if (map.getStructure() != null) {
                    cv2.put(GameDBSchema.MapElementSchema.ID, map.getStructure().getDrawableId());
                }
                else {
                    cv2.put(GameDBSchema.MapElementSchema.ID, -1);
                }

                db.insert(GameDBSchema.MapElementSchema.NAME, null, cv2);
            }
        }
    }

    /*
    build structure
     */
    public void build(int row, int col, Structure structure) {
        int cost = structure.getCost();
        if (cost <= settings.getInitialMoney()) {
            mapElement[row][col].setStructure(structure);
            settings.setInitialMoney(settings.getInitialMoney() - cost);
            if (structure.getLabel().equals("COMMERCIAL")) {
                cCount++;
            } else if (structure.getLabel().equals("RESIDENTIAL")) {
                rCount++;
            }
        }
    }

    /*
    destroy structre
     */
    public void destroy(int row, int col) {
        mapElement[row][col].setStructure(null);
    }


    public boolean isDemolitionMode() {
        return demolitionMode;
    }

    public void setDemolitionMode(boolean demolitionMode) {
        this.demolitionMode = demolitionMode;
    }

    /*
    load saved map
     */
    public void loadMap(Context context) {
        SQLiteDatabase db = new GameDBHelper(context.getApplicationContext()).getReadableDatabase();
        DBCursor c = new DBCursor(
                db.query(GameDBSchema.MapElementSchema.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null), context);
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                c.loadMap(mapElement);
                c.moveToNext();
            }
        } finally {
            c.close(); //close cursor
        }
    }

    public boolean checkIfBuildable(int x, int y, Structure structure) {
        boolean isBuildable = false;

        if (structure.getLabel().equals("ROAD"))
        {
            isBuildable = true;
        }

        if ((mapElement[y - 1][x].getStructure() != null)) {
            if (!(mapElement[y - 1][x].getStructure().getLabel().equals("ROAD"))) {
                isBuildable = true;
            }
        } else if ((mapElement[y + 1][x].getStructure() != null)) {
            if (!(mapElement[y + 1][x].getStructure().getLabel().equals("ROAD"))) {
                isBuildable = true;
            }
        } else if ((mapElement[y][x - 1].getStructure() != null)) {
            if (!(mapElement[y][x - 1].getStructure().getLabel().equals("ROAD"))){
                isBuildable = true;
            }
        } else if ((mapElement[y][x + 1].getStructure() != null)) {
            if (!(mapElement[y+1][x + 1].getStructure().getLabel().equals("ROAD")))
            {
                isBuildable = true;
            }
        }

        return isBuildable;
    }
}
