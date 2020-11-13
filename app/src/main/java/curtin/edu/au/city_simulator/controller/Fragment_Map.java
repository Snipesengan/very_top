package curtin.edu.au.city_simulator.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.MapElement;
import curtin.edu.au.city_simulator.model.Settings;
import curtin.edu.au.city_simulator.model.Structure;

/**
Fragment_Map
 Duy Tran
 09.11.2020
 Contains map grid, city name
 */
public class Fragment_Map extends Fragment implements Serializable {
    private MapAdapter mapAdapter;
    private GameData gameData;
    private Settings settings;
    private Structure selectedStructure;
    int currIndex;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm;

    public Fragment_Map() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo: init state var
        //gameData.generateNewMap(); //todo: allow user to load map
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Settings settings = gameData.getSettings();
        gameData = GameData.getInstance();
        settings = gameData.getSettings();
        // Inflate the layout for this fragment
        //tell layout to be grid
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        RecyclerView rv = view.findViewById(R.id.map_recyclerView);
        rv.setLayoutManager(new GridLayoutManager(
                getActivity(), settings.getMapHeight(), GridLayoutManager.HORIZONTAL, false));

        mapAdapter = new MapAdapter();
        rv.setAdapter(mapAdapter);
        return view;
    }

    /***************************
    MapViewHolder
     contains view holder for displaying map
     */
    private class MapViewHolder extends RecyclerView.ViewHolder {
        private ImageView structureLayer;
        private ImageView backgroundNW;
        private ImageView backgroundSW;
        private ImageView backgroundNE;
        private ImageView backgroundSE;
        private int x, y;

        public MapViewHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.layout_cell, parent, false));
            int size = parent.getMeasuredHeight() / gameData.getSettings().getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            backgroundNW = itemView.findViewById(R.id.imageView);
            backgroundNE = itemView.findViewById(R.id.imageView2);
            backgroundSW = itemView.findViewById(R.id.imageView3);
            backgroundSE = itemView.findViewById(R.id.imageView4);
            structureLayer = itemView.findViewById(R.id.imageView5);

            structureLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedStructure = ((Activity_Map) getActivity()).getSelectedStructure();
                    if (selectedStructure != null) {
                        //build
                        if (!gameData.isDemolitionMode()) {
                            gameData.build(x, y, selectedStructure);
                            //check if map element currently has a structure
                            if (gameData.getMapElement(y, x).getStructure() != null) {
                                structureLayer.setImageResource(gameData.getMapElement(y, x).getStructure().getDrawableId());
                            } else {}
                        } else if (gameData.isDemolitionMode()) { //demolition mode
                            if (gameData.getMapElement(y, x).getStructure() != null) { //if struct exist
                                structureLayer.setImageResource(android.R.color.transparent);
                                gameData.destroy(y, x);
                            }
                            else {}
                        } else {}
                    } else {}
                    mapAdapter.notifyItemChanged(currIndex);
                    ((Activity_Map) getActivity()).updateGameDetails();
                }
            });
        }

        //bind image resource file to corresponding directional cell
        public void bind(MapElement mapElement, int position) {
            backgroundNW.setImageResource(mapElement.getNorthWest());
            backgroundNE.setImageResource(mapElement.getNorthEast());
            backgroundSW.setImageResource(mapElement.getSouthWest());
            backgroundSE.setImageResource(mapElement.getSouthEast());

            //this.mapElement = mapElement;
            //this.position = position;
            x = position % gameData.getSettings().getMapHeight();
            y = position / gameData.getSettings().getMapHeight();
        }

    }


    /***************************
     MapAdapter
     contains adapter for view holder
     recycler view uses to create view for item
     */
    private class MapAdapter extends RecyclerView.Adapter<MapViewHolder> implements Serializable {
        @Override
        public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MapViewHolder vh, int position) {
            currIndex = position;
            int height = gameData.getSettings().getMapHeight();
            int row = position % height;
            int col = position / height;
            vh.bind(gameData.getMapElement(col, row), position);
        }

        @Override
        public int getItemCount() {
            return gameData.getSettings().getMapHeight() * gameData.getSettings().getMapWidth();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}