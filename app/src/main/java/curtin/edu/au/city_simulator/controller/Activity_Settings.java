package curtin.edu.au.city_simulator.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.Settings;

/*
Activity_Settings
Displays settings prompt prior to starting game
Returns Serialized settings on back
 */
public class Activity_Settings extends AppCompatActivity {
    private static final String SETTINGS_KEY = "settings";
    private TextView mapWidthInput, mapHeightInput, balanceInput, cityName;
    private Button saveSettings;
    private Settings settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = new Settings();

        cityName = findViewById(R.id.map_name);
        mapWidthInput = findViewById(R.id.map_width_input);
        mapHeightInput = findViewById(R.id.map_height_input);
        balanceInput = findViewById(R.id.starting_balance_input);
        saveSettings = findViewById(R.id.save_settings);

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent();
                try {
                    //get user input values
                    settings.setCity(cityName.getText().toString());
                    settings.setMapWidth(Integer.parseInt(mapWidthInput.getText().toString()));
                    settings.setMapHeight(Integer.parseInt(mapHeightInput.getText().toString()));
                    settings.setInitialMoney(Integer.parseInt(balanceInput.getText().toString()));
                    if (settings.getMapHeight() > 10 || settings.getMapWidth() > 30) {
                        throw new IllegalArgumentException();
                    }
                    intent.putExtra(SETTINGS_KEY, settings);
                    setResult(RESULT_OK, intent);
                    finish(); //go back
                } catch (IllegalArgumentException e) { //if missing user input
                    //print error prompt
                    String prompt = "Bad entry!";
                    Toast.makeText(Activity_Settings.this, prompt, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Settings getSettings(Intent intent) {
        return (Settings) intent.getSerializableExtra(SETTINGS_KEY);
    }

    public static Intent getIntent(Context c, Settings settings){
        Intent intent = new Intent(c, Activity_Settings.class);
        intent.putExtra(SETTINGS_KEY,settings);
        return intent;
    }
}