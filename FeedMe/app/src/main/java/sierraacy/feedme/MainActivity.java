package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Button feedme, advancedFilters;
    boolean checkedMeal, checkedDessert, checkedDrinks;
    CheckBox meal, dessert, drinks;
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    final int EDIT_CODE = 1;
    final int FILTER_CODE = 2;
    final int NUM_BASIC_FILTERS = 3;
//    HashMap<String, Boolean> filters;
    AppliedFilters filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.rooster_logo_white);

        meal = (CheckBox) findViewById(R.id.chk_meal);
        dessert = (CheckBox) findViewById(R.id.chk_dessert);
        drinks = (CheckBox) findViewById(R.id.chk_drinks);

        clearCheckboxes();

//        int capacity = getResources().getStringArray(R.array.styles_array).length;
//        capacity += getResources().getStringArray(R.array.price_array).length;
//        capacity += getResources().getStringArray(R.array.dining_array).length;
//        capacity += NUM_BASIC_FILTERS;
//        filters = new HashMap<String, Boolean>(capacity);
        filters = new AppliedFilters();

        readFile();
        feedme = (Button) findViewById(R.id.btn_feed_me);
        feedme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(restaurants.size()==0){
                    Toast.makeText(getApplicationContext(), "You have no restaurants.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(meal.isChecked())
                        filters.general.add("meal");
                    if(dessert.isChecked())
                        filters.general.add("dessert");
                    if(drinks.isChecked())
                        filters.general.add("drinks");

//                    filters.put("meal", meal.isChecked());
//                    filters.put("dessert", dessert.isChecked());
//                    filters.put("drinks", drinks.isChecked());
//                    checkedMeal = meal.isChecked();
//                    checkedDessert = dessert.isChecked();
//                    checkedDrinks = drinks.isChecked();

                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("list", restaurants);
//                    intent.putExtra("checkedMeal", checkedMeal);
//                    intent.putExtra("checkedDessert", checkedDessert);
//                    intent.putExtra("checkedDrinks", checkedDrinks);
                    intent.putExtra("filters", filters);
                    startActivity(intent);
                }
            }
        });

        advancedFilters = (Button) findViewById(R.id.btn_advanced_filters);
        advancedFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdvancedFilters.class);
                intent.putExtra("filters", filters);
                startActivityForResult(intent, FILTER_CODE);
            }
        });
    }

    public void readFile() {
        try {
            Scanner scan = new Scanner(openFileInput("feedme_lists.txt"));
            String str = "";
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                str += line;
            }
            Gson gson = new Gson();
            Object obj = gson.fromJson(str, new TypeToken<ArrayList<Restaurant>>() {}.getType());
            restaurants = (ArrayList<Restaurant>) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btn_settings) {
            //open settings
            Intent intent = new Intent(this, EditRestaurant.class);
            intent.putExtra("restaurants", restaurants);
            startActivityForResult(intent, EDIT_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == RESULT_OK){
            if(requestCode == EDIT_CODE){
                restaurants = (ArrayList<Restaurant>) intent.getSerializableExtra("restaurantList");
            }
            else if(requestCode == FILTER_CODE) {
                filters = (AppliedFilters) intent.getSerializableExtra("advanced_filters");
            }
        }
    }

    public void clearCheckboxes() {
        meal.setChecked(false);
        dessert.setChecked(false);
        drinks.setChecked(false);
    }
}
