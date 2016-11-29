package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditRestaurant extends AppCompatActivity {
    Button add;
    Button save;
    RestaurantListAdapter listAdapter;
    final int ADD_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        listAdapter = new RestaurantListAdapter(this);
        ListView listView = (ListView)findViewById(R.id.restaurant_list);
        listView.setAdapter(listAdapter);
        Intent intent = getIntent();
        listAdapter.createList((ArrayList<Restaurant>)intent.getSerializableExtra("restaurants"));

        add = (Button) findViewById(R.id.btn_add_restaurant);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRestaurant.class);
                startActivityForResult(intent, 1);
            }
        });

        save = (Button) findViewById(R.id.btn_save_list);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Restaurant> restaurants = listAdapter.getRestaurantList();
                writeFile(restaurants);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("restaurantList", restaurants);
                setResult(RESULT_OK, intent);
                finish();
            }

            /* Gson API code pulled from : http://www.vogella.com/tutorials/JavaLibrary-Gson/article.html */
            public boolean writeFile(ArrayList<Restaurant> restaurants) {
                OutputStreamWriter outputStreamWriter = null;
                boolean success = false;
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Restaurant>>() {}.getType();
                    String json = gson.toJson(restaurants, type);
                    System.out.println(json);
                    outputStreamWriter = new OutputStreamWriter(openFileOutput("feedme_lists.txt", getApplicationContext().MODE_PRIVATE));
                    outputStreamWriter.write(json);
                    outputStreamWriter.close();
                    success = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return success;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == RESULT_OK){
            if(requestCode == ADD_CODE){
                Restaurant rest = (Restaurant) intent.getSerializableExtra("newRest");
                int position = intent.getIntExtra("position", -1);
                if(position != -1)
                    listAdapter.removeItem(position);
                listAdapter.addItem(rest);
                listAdapter.notifyDataSetChanged();
            }
        }
    }
}
