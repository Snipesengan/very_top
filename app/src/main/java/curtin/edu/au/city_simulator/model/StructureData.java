/*
StructureData.java
Author : Duy Tran
Data : 2.11
Structure data model class
 */


package curtin.edu.au.city_simulator.model;

import java.util.Arrays;
import java.util.List;

import curtin.edu.au.city_simulator.R;


/*
StructureData
data of structures in game
ref : practical 3 StructureData.java
 */
public class StructureData {
    //label for structure type
    public static final String ROAD = "ROAD";
    public static final String COMMERCIAL = "COMMERCIAL";
    public static final String RESIDENTIAL = "RESIDENTIAL";

    private static List<Structure> structureList = Arrays.asList(new Structure[] {
            new Commercial(R.drawable.ic_building1, COMMERCIAL),
            new Commercial(R.drawable.ic_building2, COMMERCIAL),
            new Commercial(R.drawable.ic_building3, COMMERCIAL),
            new Commercial(R.drawable.ic_building4, COMMERCIAL),
            new Commercial(R.drawable.ic_building5, COMMERCIAL),
            new Road(R.drawable.ic_road_n, ROAD),
            new Road(R.drawable.ic_road_e, ROAD),
            new Road(R.drawable.ic_road_s, ROAD),
            new Road(R.drawable.ic_road_w, ROAD),
            new Road(R.drawable.ic_road_nsew, ROAD),
            new Road(R.drawable.ic_road_ns, ROAD),
            new Road(R.drawable.ic_road_ew, ROAD),
            new Road(R.drawable.ic_road_ne, ROAD),
            new Road(R.drawable.ic_road_nw, ROAD),
            new Road(R.drawable.ic_road_se, ROAD),
            new Road(R.drawable.ic_road_sw, ROAD),
            new Road(R.drawable.ic_road_nse, ROAD),
            new Road(R.drawable.ic_road_nsw, ROAD),
            new Road(R.drawable.ic_road_new, ROAD),
            new Road(R.drawable.ic_road_sew, ROAD),
            new Residential(R.drawable.ic_building1, RESIDENTIAL),
            new Residential(R.drawable.ic_building2, RESIDENTIAL),
            new Residential(R.drawable.ic_building3, RESIDENTIAL)
    });

    private int count;

    private static StructureData instance = null;

    public static StructureData get()
    {
        if (instance == null)
        {
            instance = new StructureData();
        }
        return instance;
    }

    protected StructureData() {}

    public static Structure get(int i)
    {
        return structureList.get(i);
    }

    public int size()
    {
        return structureList.size();
    }

    public void add(Structure s)
    {
        structureList.add(0, s);
    }

    public void remove(int i)
    {
        structureList.remove(i);
    }

    public static int getCount() {
        return structureList.size();
    }

    public static Structure getStructureWithId(int id) {
        for (Structure structure : structureList) {
            if (structure.getDrawableId() == id) {
                return structure;
            }
        }
        return null;
    }
}