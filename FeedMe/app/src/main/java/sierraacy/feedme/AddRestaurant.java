package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddRestaurant extends AppCompatActivity {
    Button save, cancel;
    EditText name, price;
    Spinner style, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.edit_rest);
        price = (EditText) findViewById(R.id.edit_price);

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

        Intent intent = getIntent();

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String restName = name.getText().toString();
                String restStyle = style.getSelectedItem().toString();
                String restType = type.getSelectedItem().toString();
                int restPrice = price.getText().length();

                Restaurant newRest = new Restaurant(restName, restStyle, restType, restPrice);
                Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                Bundle b = new Bundle();
                b.putSerializable("newRest", newRest);
                intent.putExtras(b);
                startActivity(intent);
            }

        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EditRestaurant.class);
                startActivity(intent);
            }
        });
    }


}
