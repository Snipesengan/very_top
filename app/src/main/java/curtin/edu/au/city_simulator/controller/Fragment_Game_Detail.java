package curtin.edu.au.city_simulator.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import curtin.edu.au.city_simulator.R;
import curtin.edu.au.city_simulator.model.GameData;
import curtin.edu.au.city_simulator.model.Settings;

/**
Fragment_Game_Detail
 Duy Tran
 09.11.2020
 Recycler view containing player money, most recent income, income, population,
 employment rate, current temp
 */
public class Fragment_Game_Detail extends Fragment {
    private ImageButton imageButton;
    private GameData gameData;
    private TextView city, balance, income, population, employment_rate, weather;
    private Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameData = GameData.getInstance();
        settings = gameData.getSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);

        settings = gameData.getSettings();

        //Get UI elements
        city = view.findViewById(R.id.city);
        balance = view.findViewById(R.id.balance);
        income = view.findViewById(R.id.income);
        population = view.findViewById(R.id.population);
        employment_rate = view.findViewById(R.id.employment_rate);
        weather = view.findViewById(R.id.weather);

        updateGameDetails();

        return view;
    }

    public void updateGameDetails() {
        city.setText(settings.getCity());
        balance.setText("Balance: " + Integer.toString(settings.getInitialMoney()));
        income.setText("Income: " + Integer.toString(settings.getSalary()));
        population.setText("Population: " + Integer.toString(gameData.getPopulation()));
        employment_rate.setText("E. Rate: " + Double.toString(gameData.getEmploymentRate()));
        find_weather();
    }

    //api weather thing
    //ref : https://www.youtube.com/watch?v=8-7Ip6xum6E&fbclid=IwAR3VhBAVHo7FEZbOR-FSucrYpRGi1c2FqJcbv_n9_hv7cZKOUJttPv2hjBo
    public void find_weather() {
        //antarctica
        String url = "http://api.openweathermap.org/data/2.5/weather?id=6255152&appid=9e6f736ab2923fc9ccdc7cd48c23c2b1&units=metric";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp;
                    JSONObject main_object = response.getJSONObject("main");
                    temp = String.valueOf(main_object.getDouble("temp"));
                    weather.setText(temp + "C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq = Volley.newRequestQueue(this.getContext());
        rq.add(jor);
    }
}