package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditRestaurant extends AppCompatActivity {
    Button add;
    Button save;
    DynamicAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        listAdapter = new DynamicAdapter(this);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("restaurantList", listAdapter.getRestaruantList());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Restaurant rest = (Restaurant) intent.getSerializableExtra("newRest");
                listAdapter.addItem(rest);
                listAdapter.notifyDataSetChanged();
            }
        }
    }
}
