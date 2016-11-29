package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    Button feedMe, startOver;
    TextView picked;
    ArrayList<Restaurant> restList;
    AppliedFilters filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        feedMe = (Button) findViewById(R.id.btn_feed_me);
        startOver = (Button) findViewById(R.id.start_over);
        picked = (TextView) findViewById(R.id.picked_rest);

        Intent intent = getIntent();
        restList = (ArrayList<Restaurant>) intent.getSerializableExtra("list");
        filters = (AppliedFilters) intent.getSerializableExtra("filters");

        Restaurant rest;
        restList = buildList();
        rest = getRestaurant();
        if(rest == null)
            picked.setText("There are no matches.");
        else
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

        picked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationsActivity.class);
                intent.putExtra("name", picked.getText().toString());
                startActivity(intent);
            }
        });
    }

    public Restaurant getRestaurant(){
        if(restList.size() == 0) {
            return null;
        }
        Random rand = new Random();
        int rest = rand.nextInt(restList.size());
        return restList.get(rest);
    }

    public ArrayList<Restaurant> buildList() {
        ArrayList<Restaurant> filteredList = new ArrayList<Restaurant>();
        for(int i = 0; i < restList.size(); i++) {
            Restaurant r = restList.get(i);
            //needs to match at least 1 style
            //needs to match at least 1 dining type
            //needs to match price
            //needs to match all gen

            if(checkStyle(r) && checkDining(r) && checkPrice(r) && checkGeneral(r)) {
                filteredList.add(r);
            }
        }
        return filteredList;
    }

    public boolean checkStyle(Restaurant r) {
        boolean ret = false;
        if(!filters.styles.isEmpty() && filters.styles.contains(r.style)) {
            ret = true;
        }
        else if(filters.styles.isEmpty()) {
            ret = true;
        }
        return ret;
    }

    public boolean checkDining(Restaurant r) {
        boolean ret = false;
        if(!filters.dining.isEmpty() && filters.dining.contains(r.diningType)) {
            ret = true;
        }
        else if(filters.dining.isEmpty()) {
            ret = true;
        }
        return ret;
    }

    public boolean checkPrice(Restaurant r) {
        boolean ret = false;
        if(!filters.price.isEmpty() && filters.price.contains(r.priceRange)) {
            ret = true;
        }
        else if(filters.price.isEmpty()) {
            ret = true;
        }
        return ret;
    }

    public boolean checkGeneral(Restaurant r) {
        //if meal checked and restaurant matches, move on
        //if meal not checked, move on
        //if meal checked and restaurant doesn't match, skip
        boolean meal = false;
        if(!filters.general.isEmpty() && filters.general.contains("meal") && r.hasMeal)
            meal = true;
        else if(!filters.general.contains("meal"))
            meal = true;

        boolean dessert = false;
        if(!filters.general.isEmpty() && filters.general.contains("dessert") && r.hasDessert)
            dessert = true;
        else if(!filters.general.contains("dessert"))
            dessert = true;

        boolean drinks = false;
        if(!filters.general.isEmpty() && filters.general.contains("drinks") && r.hasDrinks)
            drinks = true;
        else if(!filters.general.contains("drinks"))
            drinks = true;

        return meal && dessert && drinks;
    }
}