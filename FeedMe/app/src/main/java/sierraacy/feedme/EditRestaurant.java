package sierraacy.feedme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EditRestaurant extends AppCompatActivity {
    String[] restaurantNames = {
            "Taco Bell",
            "Chick-fil-a",
            "Pizza Hut",
            "Panda Express",
            "Wendy's",
            "Subway",
            "Panara",
            "Taco Cabana"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantNames);
        ListView listView = (ListView)findViewById(R.id.restaurant_list);
        listView.setAdapter(listAdapter);
    }
}
