package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    Button feedMe, startOver;
    TextView picked;
    ArrayList<Restaurant> restList;
    boolean checkedMeal, checkedDessert, checkedDrinks;
    HashMap<String, Boolean> filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        feedMe = (Button) findViewById(R.id.btn_feed_me);
        startOver = (Button) findViewById(R.id.start_over);
        picked = (TextView) findViewById(R.id.picked_rest);

        Intent intent = getIntent();
        restList = (ArrayList<Restaurant>) intent.getSerializableExtra("list");
//        checkedMeal = intent.getBooleanExtra("checkedMeal", false);
//        checkedDessert = intent.getBooleanExtra("checkedDessert", false);
//        checkedDrinks = intent.getBooleanExtra("checkedDrinks", false);
        filters = (HashMap<String, Boolean>) intent.getSerializableExtra("filters");

        Restaurant rest;
//        if(checkedMeal || checkedDessert || checkedDrinks) {
            restList = buildList();
//        }
        rest = getRestaurant();

        picked.setText(rest.name);

        feedMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant rest = getRestaurant();
                picked.setText(rest.name);
            }
        });

        startOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public Restaurant getRestaurant(){
        Random rand = new Random();
        int rest = rand.nextInt(restList.size());
        Restaurant r = restList.get(rest);
        return r;

    }

    public ArrayList<Restaurant> buildList() {
        ArrayList<Restaurant> filteredList = new ArrayList<Restaurant>();
        for(int i = 0; i < restList.size(); i++) {
            //style, dining type, price range, meal, dessert, drinks
            Restaurant r = restList.get(i);
            if(filters.get(r.style) && filters.get(r.diningType) && filters.get(r.priceRange)
                    && r.hasMeal == filters.get("meal") && r.hasDrinks == filters.get("drinks")
                    && r.hasDessert == filters.get("dessert")) { //CHANGE TO SPINNER
                filteredList.add(r);
            }
//            if((r.hasMeal && checkedMeal) || (r.hasDessert && checkedDessert)
//                    || (r.hasDrinks && checkedDrinks)) {
//                filteredList.add(r);
//            }
        }
        return filteredList;
    }
}