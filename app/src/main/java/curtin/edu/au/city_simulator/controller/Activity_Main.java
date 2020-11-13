package curtin.edu.au.city_simulator.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.database.GameDBHelper;
import curtin.edu.au.city_simulator.database.GameDBSchema;
import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.Settings;

public class Activity_Main extends AppCompatActivity {
    private static final int SETTINGS_REQUEST_CODE = 0;
    private static final int GAME_DATA_REQUEST_CODE = 1;
    private static final String SETTING_KEY = "settings";
    private static final String GAME_DATA_KEY = "game_data";

    private Settings settings;
    private GameData gameData;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        Button setting_button, map_button, resume_button;

        map_button = findViewById(R.id.map_screen_button);
        setting_button = findViewById(R.id.settings_screen_button);
        resume_button = findViewById(R.id.resume_button);

        gameData = GameData.getInstance();
        settings = gameData.getSettings();

        //map screen with default settings
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData.generateNewMap(); //create new map
                Intent intent = new Intent(Activity_Main.this, Activity_Map.class);
                intent.putExtra(SETTING_KEY, settings);
                //startActivity(intent); //start map_button
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
            }
        });
        //settings screen
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main.this, Activity_Settings.class);
                intent.putExtra(SETTING_KEY, settings);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE); //start map_button
            }
        });

        //resume game
        resume_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Main.this, Activity_Map.class);
                gameData.loadGameData(getApplicationContext()); //load database
                gameData.loadMap(getApplicationContext()); //load map
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
            }
        });
    }

    /* if settings changed

     */
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

        super.onActivityResult(reqCode, resCode, data);
        if (resCode == RESULT_OK && reqCode == SETTINGS_REQUEST_CODE) {
            //get settings
            settings = (Settings)data.getSerializableExtra(SETTING_KEY);

            //pass game data to map activity
            gameData = GameData.getInstance(); //get instance of game data
            gameData.setSettings(settings); //set settings in game data singleton
            gameData.generateNewMap();

            Intent intent = new Intent(Activity_Main.this, Activity_Map.class);
            startActivity(intent);
        }
        else {  } //do nothing return to main menu

    }
}