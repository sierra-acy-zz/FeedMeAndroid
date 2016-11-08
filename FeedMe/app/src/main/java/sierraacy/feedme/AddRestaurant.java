package sierraacy.feedme;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddRestaurant extends AppCompatActivity {
    Button save, cancel;
    EditText name;
    Spinner style, type, price;
    Restaurant res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        Intent intent = getIntent();


        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.edit_rest);


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

        res = ((Restaurant)intent.getSerializableExtra("restaurant"));
        if(res != null)
            editRestaurant();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String restName = name.getText().toString();
                String restStyle = style.getSelectedItem().toString();
                String restType = type.getSelectedItem().toString();
                //We will default the price to 1 if they do not select anything.
                String restPrice = "$";
                if(!price.getSelectedItem().toString().equals("Select the price!"))
                     restPrice = price.getSelectedItem().toString();



                Restaurant newRest = new Restaurant(restName, restStyle, restType, restPrice);
                Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                Bundle b = new Bundle();
                b.putSerializable("newRest", newRest);
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                finish();
            }
        });
    }

    public void editRestaurant(){
        name.setText(res.name);
        style.setSelection(looping(R.array.styles_array, res.style));
        type.setSelection(looping(R.array.dining_array, res.diningType));
        price.setSelection(looping(R.array.price_array, res.priceRange));
    }

    public int looping(int array, String type){
        Resources resources = getResources();
        String[] resArray = resources.getStringArray(array);
        int index = 0;
        for(int i=0; i<resArray.length; i++){
            if(resArray[i].equals(type))
                index = i;
        }
        return index;
    }

}
