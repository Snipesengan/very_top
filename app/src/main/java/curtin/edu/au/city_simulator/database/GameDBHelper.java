package curtin.edu.au.city_simulator.database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.GenericArrayType;

import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.Settings;

public class GameDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "game_data_table";

    private SQLiteDatabase db;
    private Context context;

    public GameDBHelper(Context context) { super(context, DATABASE_NAME, null, VERSION); }

    /*
    create db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        //saved game data
        db.execSQL("CREATE TABLE " +
                GameDBSchema.NAME  + " (" +
                GameDBSchema.CITY_NAME + " TEXT, " +
                GameDBSchema.MAP_HEIGHT + " INTEGER, " +
                GameDBSchema.MAP_WIDTH + " INTEGER, " +

                GameDBSchema.INITIAL_MONEY + " INTEGER, " +
                GameDBSchema.FAMILY_SIZE + " INTEGER, " +
                GameDBSchema.SHOP_SIZE + " INTEGER, " +
                GameDBSchema.SALARY + " INTEGER, " +
                GameDBSchema.TAX_RATE + " INTEGER, " +
                GameDBSchema.SERVICE_COST + " INTEGER, " +
                GameDBSchema.HOUSE_BUILDING_COST + " INTEGER, " +
                GameDBSchema.COMM_BUILDING_COST + " INTEGER, " +
                GameDBSchema.ROAD_BUILDING_COST + " INTEGER, " +

                GameDBSchema.TIME + " INTEGER, " +
                GameDBSchema.POPULATION + " INTEGER, " +
                GameDBSchema.EMPLOYMENT_RATE + " INTEGER)");
        //saved map data
        db.execSQL("CREATE TABLE " +
                GameDBSchema.MapElementSchema.NAME + " ( " +
                GameDBSchema.MapElementSchema.X_POS + " INTEGER, " +
                GameDBSchema.MapElementSchema.Y_POS + " INTEGER, " +
                GameDBSchema.MapElementSchema.NW + " INTEGER, " +
                GameDBSchema.MapElementSchema.SW + " INTEGER, " +
                GameDBSchema.MapElementSchema.NE + " INTEGER, " +
                GameDBSchema.MapElementSchema.SE + " INTEGER," +
                GameDBSchema.MapElementSchema.ID + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + GameDBSchema.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GameDBSchema.MapElementSchema.NAME);
        onCreate(db);
    }

    public static void rebuildDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

}
