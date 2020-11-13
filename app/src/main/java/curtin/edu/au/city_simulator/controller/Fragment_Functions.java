package curtin.edu.au.city_simulator.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.GameData;

/*
fragment for functions
time step, current time
 */
public class Fragment_Functions extends Fragment {
    private Button timeStep, saveGame, demolish;
    private TextView currentTime;
    private GameData gameData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_functions, container, false);

        gameData = GameData.getInstance();

        timeStep = view.findViewById(R.id.time_step);
        currentTime = view.findViewById(R.id.current_time);
        saveGame = view.findViewById(R.id.save_game);
        demolish = view.findViewById(R.id.demolish);

        //do time step
        timeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameData.doTimeStep();
                currentTime.setText(Integer.toString(gameData.getTime())); //update time
                ((Activity_Map)getActivity()).updateGameDetails();
            }
        });

        //save game detaisl to database
        saveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameData.saveGameData(getActivity());
                //gameData.saveMap(getActivity());
            }
        });

        //demolish structure
        demolish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameData.setDemolitionMode(true);
            }
        });

        return view;
    }
}
