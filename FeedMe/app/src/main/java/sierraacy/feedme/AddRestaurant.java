package sierraacy.feedme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Category;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class AddRestaurant extends AppCompatActivity {
    Button save, cancel;
    EditText name;
    Spinner style, type, price;
    CheckBox hasMeal, hasDessert, hasDrinks;
    Restaurant res;

//    YelpAPIFactory apiFactory;
//    YelpAPI yelpAPI;

    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

//        apiFactory = new YelpAPIFactory(getResources().getString(R.string.consumerKey),
//                getResources().getString(R.string.consumerSecret),
//                getResources().getString(R.string.token),
//                getResources().getString(R.string.tokenSecret));
//        yelpAPI = apiFactory.createAPI();

        Intent intent = getIntent();

        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.edit_rest);
        hasMeal = (CheckBox) findViewById(R.id.meal);
        hasDessert = (CheckBox) findViewById(R.id.dessert);
        hasDrinks = (CheckBox) findViewById(R.id.drinks);
//        autofill = (ToggleButton) findViewById(R.id.btn_toggle);
//        search = (ImageButton) findViewById(R.id.btn_yelp_search);

        style = (Spinner) findViewById(R.id.style);
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(this,
                R.array.styles_array, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        style.setAdapter(styleAdapter);

        type = (Spinner) findViewById(R.id.dining);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.dining_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        price = (Spinner) findViewById(R.id.edit_price);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_array, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price.setAdapter(priceAdapter);

//        autofill.setChecked(false);
//        search.setVisibility(View.INVISIBLE);

        res = ((Restaurant)intent.getSerializableExtra("restaurant"));
        position = intent.getIntExtra("position", -1);
        if(res != null)
            editRestaurant();


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String restName = name.getText().toString();
                String restStyle = style.getSelectedItem().toString();
                String restType = type.getSelectedItem().toString();
                boolean restHasMeal = hasMeal.isChecked();
                boolean restHasDessert = hasDessert.isChecked();
                boolean restHasDrinks = hasDrinks.isChecked();

                //We will default the price to 1 if they do not select anything.
                String restPrice = "$";
                if(!price.getSelectedItem().toString().equals("Select the price!"))
                     restPrice = price.getSelectedItem().toString();

                if(res != null){
                    Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                    res.name = restName;
                    res.style = restStyle;
                    res.diningType = restType;
                    res.priceRange = restPrice;
                    res.hasMeal = restHasMeal;
                    res.hasDessert = restHasDessert;
                    res.hasDrinks = restHasDrinks;
                    Bundle b = new Bundle();
                    b.putSerializable("newRest", res);
                    b.putInt("position", position);
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Restaurant newRest = new Restaurant(restName, restStyle, restType, restPrice,
                            restHasMeal, restHasDessert, restHasDrinks);
                    Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                    Bundle b = new Bundle();
                    b.putSerializable("newRest", newRest);
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                finish();
            }
        });

//        autofill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(autofill.isChecked()) {
//                    //turn on yelp search
//                    setEnabled(false);
//                    search.setVisibility(View.VISIBLE);
//                } else {
//                    setEnabled(true);
//                    search.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String, String> params = new HashMap<>();
//
//                // general params
//                params.put("term", name.getText().toString());
//                params.put("limit", "1");
//                params.put("category_filter", "restaurants,bars,food,coffeeshops");
//
//                Call<SearchResponse> call = yelpAPI.search("Austin", params);
//                try {
//                    Response<SearchResponse> response = call.execute();
//                    SearchResponse searchResponse = response.body();
//                    ArrayList<Business> businesses = searchResponse.businesses();
//                    Business b = businesses.get(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void editRestaurant(){
        name.setText(res.name);
        style.setSelection(looping(R.array.styles_array, res.style));
        type.setSelection(looping(R.array.dining_array, res.diningType));
        price.setSelection(looping(R.array.price_array, res.priceRange));
        hasMeal.setChecked(res.hasMeal);
        hasDessert.setChecked(res.hasDessert);
        hasDrinks.setChecked(res.hasDrinks);
    }

    public int looping(int array, String type){
        Resources resources = getResources();
        String[] resArray = resources.getStringArray(array);
        int index = 0;
        for(int i = 0; i < resArray.length; i++){
            if(resArray[i].equals(type))
                index = i;
        }
        return index;
    }

//    public void setEnabled(boolean enable) {
//        style.setEnabled(enable);
//        type.setEnabled(enable);
//        price.setEnabled(enable);
//    }
}