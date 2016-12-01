package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button feedme, advancedFilters, somethingNew;
    CheckBox meal, dessert, drinks;
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    final int EDIT_CODE = 1;
    final int FILTER_CODE = 2;
    AppliedFilters filters;
    YelpAPIFactory apiFactory;
    YelpAPI yelpAPI;
    ArrayList<Business> businesses;

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

        filters = new AppliedFilters();
        clearFilters();

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

                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("list", restaurants);
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

        somethingNew = (Button) findViewById(R.id.btn_random);
        somethingNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiFactory = new YelpAPIFactory(getResources().getString(R.string.consumerKey),
                        getResources().getString(R.string.consumerSecret),
                        getResources().getString(R.string.token),
                        getResources().getString(R.string.tokenSecret));
                yelpAPI = apiFactory.createAPI();

                Map<String, String> params = new HashMap<>();

                // general params
                params.put("term", "food");
                params.put("limit", "25");
                params.put("category_filter", "restaurants,bars,food,coffee");

                Call<SearchResponse> call = yelpAPI.search("Austin", params);
                Callback<SearchResponse> callback = new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        SearchResponse searchResponse = response.body();
                        // Update UI text with the searchResponse.
                        businesses = searchResponse.businesses();
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra("businesses", businesses);
                        intent.putExtra("list", restaurants);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        // HTTP error happened, do something to handle it.
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                };
                call.enqueue(callback);
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
                TextView filterText = (TextView) findViewById(R.id.filter_text);
                String newText = "Filters:";
                if(!filters.styles.isEmpty())
                    newText += " " + filters.styles.toString();
                if(!filters.dining.isEmpty())
                    newText += " " + filters.dining.toString();
                if(!filters.price.isEmpty())
                    newText += " " + filters.price.toString();
                newText = newText.replaceAll("\\[", "");
                newText = newText.replaceAll("\\]", "");
                filterText.setText(newText);
            }
        }
    }

    public void clearFilters() {
        meal.setChecked(false);
        dessert.setChecked(false);
        drinks.setChecked(false);
        filters.general.clear();
        filters.styles.clear();
        filters.price.clear();
        filters.dining.clear();
    }
}
