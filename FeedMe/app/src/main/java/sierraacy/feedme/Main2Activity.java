package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    Button feedMe;
    TextView picked;
    ArrayList<Restaurant> restList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        feedMe = (Button) findViewById(R.id.btn_feed_me);
        picked = (TextView) findViewById(R.id.picked_rest);

        Intent intent = getIntent();
        restList = (ArrayList<Restaurant>) intent.getSerializableExtra("list");
        Restaurant r = getRestaurant();
        picked.setText(r.name);

        feedMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant rest = getRestaurant();
                picked.setText(rest.name);
            }
        });
    }

    public Restaurant getRestaurant(){
        Random rand = new Random();
        int rest = rand.nextInt(restList.size());
        Restaurant r = restList.get(rest);
        return r;

    }
}
