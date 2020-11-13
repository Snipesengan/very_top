package curtin.edu.au.city_simulator.model;

import java.io.Serializable;

/*
Commercial
Duy Tran
07.11.2020
Model class for commercial structures
 */
public class Commercial extends Structure implements Serializable {
    public Commercial(int imageId, String label) { super(imageId, label); }
}
