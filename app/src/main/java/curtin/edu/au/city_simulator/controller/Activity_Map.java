/*
Activity_Map
03.11
Duy Tran
Game Screen
 */

package curtin.edu.au.city_simulator.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.Structure;

public class Activity_Map extends AppCompatActivity {
    private static final int GAME_DATA_REQUEST_CODE = 1;
    private static final String GAME_DATA = "game_data";

    private FragmentManager fm;
    private Fragment_Game_Detail frag_gameDetail;
    private Fragment_Map frag_map;
    private Fragment_Structure_List frag_structureList;
    private Fragment_Functions frag_Functions;

    private GameData gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        gameData = GameData.getInstance();

        //create fragments
        fm = getSupportFragmentManager();

        //player stats at top of screen
        frag_gameDetail = (Fragment_Game_Detail)fm.findFragmentById(R.id.container_game_details);
        if (frag_gameDetail == null) {
            frag_gameDetail = new Fragment_Game_Detail();
            fm.beginTransaction().add(R.id.container_game_details, frag_gameDetail).commit();
        }

        //displays timestep
        frag_Functions = (Fragment_Functions)fm.findFragmentById(R.id.container_functions);
        if (frag_Functions == null) {
            frag_Functions = new Fragment_Functions();
            fm.beginTransaction().add(R.id.container_functions, frag_Functions).commit();
        }

        //map
        frag_map = (Fragment_Map)fm.findFragmentById(R.id.container_map);
        if (frag_map == null) {
            frag_map = new Fragment_Map();
            fm.beginTransaction().add(R.id.container_map, frag_map).commit();
        }

        //structure list
        frag_structureList = (Fragment_Structure_List)fm.findFragmentById(R.id.container_structure_list);
        if (frag_structureList == null) {
            frag_structureList = new Fragment_Structure_List();
            fm.beginTransaction().add(R.id.container_structure_list, frag_structureList).commit();
        }
    }

    /*get selected structure */
    public Structure getSelectedStructure()
    {
        return frag_structureList.getSelectedStructure();
    }

    /*
    update game detaisl
     */
    public void updateGameDetails() {
        frag_gameDetail.updateGameDetails();
    }
}