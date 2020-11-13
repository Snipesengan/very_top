package curtin.edu.au.city_simulator.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.Structure;
import curtin.edu.au.city_simulator.model.StructureData;

/**
Fragment_Structure_List
 Duy Tran
 9.11.2020
 Contains demolish, build -> recycler view (scroll horizontal)
 */
public class Fragment_Structure_List extends Fragment {
    private ImageButton structureImgBttn;
    private TextView structureCost, structureType;
    private GridLayoutManager mGridLayoutManager;
    private StructListAdapter sLAdapter;
    private int currIndex;
    private Structure selectedStructure;
    private int selected_position;
    private GameData gameData;

    public Fragment_Structure_List() {
        // Required empty public constructor
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    } */

    public Structure getSelectedStructure() {
        return selectedStructure;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui,
                             Bundle savedInstanceState) {
        gameData = GameData.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_structure_list, ui, false);

        //obtain recyclerview ui element
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.structure_list_recyclerView);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(mGridLayoutManager);

        sLAdapter = new StructListAdapter();
        rv.setAdapter(sLAdapter);

        return view;
    }

    /***************************
     StructureListVH
     contains view holder for displaying structure list
     */
    private class StructureListVH extends RecyclerView.ViewHolder {
        public StructureListVH(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.layout_structure_detail, parent, false));

            structureImgBttn = itemView.findViewById(R.id.structureImgBttn);
            structureCost = itemView.findViewById(R.id.structureCost);
            structureType = itemView.findViewById(R.id.structureName);
        }

        public void bind(Structure data, int index) {
            structureImgBttn.setImageResource(data.getDrawableId());
            structureType.setText(data.getLabel());
            structureCost.setText(Integer.toString(data.getCost()));
            currIndex = index;
        }
    }


    /***************************
     StructListAdapter
     contains adapter for view holder
     recycler view uses to create view for item
     ref : https://stackoverflow.com/questions/50872380/how-to-select-and-de-select-an-item-in-recyclerview-how-to-highlight-selected-i
     */
    private class StructListAdapter extends RecyclerView.Adapter<StructureListVH> implements Serializable {
        /*public StructListAdapter(StructureData data) {
            this.data = data;
        }*/

        @Override
        public void onBindViewHolder(StructureListVH vh, int index) {
            if (selected_position == index) {
                //highlight
            } else {

            }

            structureImgBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gameData.isDemolitionMode()) {
                        gameData.setDemolitionMode(false); //if demolition mode, cancel demolition
                    }

                    if (selected_position == index) {
                        selected_position = -1;
                        notifyDataSetChanged();
                    }
                    selected_position = index;
                    selectedStructure = StructureData.get(selected_position);
                    notifyDataSetChanged();
                }
            });

            vh.bind(StructureData.get(index), index);

        }

        @Override
        public StructureListVH onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new StructureListVH(li, parent);
        }

        @Override
        public int getItemCount() {
            return StructureData.getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}