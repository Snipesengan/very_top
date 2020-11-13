package curtin.edu.au.city_simulator.database;

/*
GameDBSchema
Duy Tran
9.11.2020
Contains schema for required to be reloaded
 */
public class GameDBSchema {
    private GameDBSchema() {} //security

    public static final String NAME = "game_data_table"; //table name

    //settings
    public static final String CITY_NAME = "city_name";
    public static final String MAP_WIDTH = "map_width";
    public static final String MAP_HEIGHT = "map_height";

    public static final String INITIAL_MONEY = "initial_money";
    public static final String FAMILY_SIZE = "family_size";
    public static final String SHOP_SIZE = "shop_size";
    public static final String SALARY = "salary";
    public static final String TAX_RATE = "tax_rate";
    public static final String SERVICE_COST = "service_cost";
    public static final String HOUSE_BUILDING_COST = "house_building_cost";
    public static final String COMM_BUILDING_COST = "comm_building_cost";
    public static final String ROAD_BUILDING_COST = "road_building_cost";


    //game specific values
    public static final String TIME = "time";
    public static final String POPULATION = "population";
    public static final String EMPLOYMENT_RATE = "employment_rate";

    public static class MapElementSchema {
        public static final String NAME = "map_element_table"; //table name

        public static final String X_POS = "x";
        public static final String Y_POS = "y";
        public static final String NW = "nw";
        public static final String NE = "ne";
        public static final String SW = "sw";
        public static final String SE = "se";
        public static final String ID = "id";
    }
}
