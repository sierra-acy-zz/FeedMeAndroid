package sierraacy.feedme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class AddRestaurant extends AppCompatActivity {
    Button save, cancel;
    EditText name;
    Spinner style, type, price;
    CheckBox hasMeal, hasDessert, hasDrinks;
    Restaurant res;

    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        Intent intent = getIntent();

        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        name = (EditText) findViewById(R.id.edit_rest);
        hasMeal = (CheckBox) findViewById(R.id.meal);
        hasDessert = (CheckBox) findViewById(R.id.dessert);
        hasDrinks = (CheckBox) findViewById(R.id.drinks);

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
                finish();
            }
        });
    }

    @Override
//  http://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exiting Edit")
                .setMessage("Are you sure you want to exit without saving?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
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
}