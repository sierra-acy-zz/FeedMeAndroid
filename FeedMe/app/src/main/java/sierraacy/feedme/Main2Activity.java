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
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    Button feedMe, startOver;
    TextView picked;
    ArrayList<Restaurant> restList;
    boolean checkedMeal, checkedDessert, checkedDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        feedMe = (Button) findViewById(R.id.btn_feed_me);
        startOver = (Button) findViewById(R.id.start_over);
        picked = (TextView) findViewById(R.id.picked_rest);

        Intent intent = getIntent();
        restList = (ArrayList<Restaurant>) intent.getSerializableExtra("list");
        checkedMeal = intent.getBooleanExtra("checkedMeal", false);
        checkedDessert = intent.getBooleanExtra("checkedDessert", false);
        checkedDrinks = intent.getBooleanExtra("checkedDrinks", false);

        Restaurant rest;
        if(checkedMeal || checkedDessert || checkedDrinks) {
            restList = buildList();
        }
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
            Restaurant curr = restList.get(i);
            if((curr.hasMeal && checkedMeal) || (curr.hasDessert && checkedDessert)
                    || (curr.hasDrinks && checkedDrinks)) {
                filteredList.add(curr);
            }
        }
        return filteredList;
    }
}
