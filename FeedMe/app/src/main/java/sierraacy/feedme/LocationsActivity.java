package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsActivity extends AppCompatActivity {
    LocationListAdapter listAdapter;
    YelpAPIFactory apiFactory;
    YelpAPI yelpAPI;
    ArrayList<Business> businesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("name");

        apiFactory = new YelpAPIFactory(getResources().getString(R.string.consumerKey),
                getResources().getString(R.string.consumerSecret),
                getResources().getString(R.string.token),
                getResources().getString(R.string.tokenSecret));
        yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", restaurantName);
        params.put("limit", "3");
        params.put("category_filter", "restaurants,bars,food,coffee");

        Call<SearchResponse> call = yelpAPI.search("Austin", params);
        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                // Update UI text with the searchResponse.
                businesses = searchResponse.businesses();
                if(businesses.isEmpty()) {
                    TextView info = (TextView) findViewById(R.id.info_text);
                    info.setText("There are no matches.");
                }
                else {
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened, do something to handle it.
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        };
        call.enqueue(callback);
    }

        public void setAdapter() {
            listAdapter = new LocationListAdapter(this);
            ListView listView = (ListView)findViewById(R.id.location_list);
            listView.setAdapter(listAdapter);

            listAdapter.createList(businesses);
        }
}
