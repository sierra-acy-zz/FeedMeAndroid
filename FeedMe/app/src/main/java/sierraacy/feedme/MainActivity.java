package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button feedme;
    ArrayList<Restaurant> restaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedme = (Button) findViewById(R.id.btn_feed_me);
        feedme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(restaurants.size()==0){
                    Toast.makeText(getApplicationContext(), "You have no restaurants.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("list", restaurants);
                    startActivity(intent);
                }

            }
        });
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
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                restaurants = (ArrayList<Restaurant>) intent.getSerializableExtra("restaurantList");

            }
        }
    }
}
