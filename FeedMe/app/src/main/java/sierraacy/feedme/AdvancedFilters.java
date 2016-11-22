package sierraacy.feedme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class AdvancedFilters extends AppCompatActivity {

    Spinner style_view, dining_type_view, price_view;
//    CheckBox sitdown_view, takeout_view, fastfood_view;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_filters);

        save = (Button) findViewById(R.id.btn_save_advanced);
        cancel = (Button) findViewById(R.id.btn_cancel_advanced);

        style_view = (Spinner) findViewById(R.id.style_advanced);
        ArrayAdapter<CharSequence> styleAdapter = ArrayAdapter.createFromResource(this,
                R.array.styles_array, android.R.layout.simple_spinner_item);
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        style_view.setAdapter(styleAdapter);

        dining_type_view = (Spinner) findViewById(R.id.dining_type_advanced);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.dining_array, android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dining_type_view.setAdapter(typeAdapter);

//        sitdown_view = (CheckBox) findViewById(R.id.chk_sit_down);
//        takeout_view = (CheckBox) findViewById(R.id.chk_takeout);
//        fastfood_view = (CheckBox) findViewById(R.id.chk_fast_food);

        price_view = (Spinner) findViewById(R.id.max_price);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_array, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_view.setAdapter(priceAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int capacity = getResources().getStringArray(R.array.styles_array).length;
                capacity += getResources().getStringArray(R.array.price_array).length;
                capacity += getResources().getStringArray(R.array.dining_array).length;
                HashMap<String, Boolean> filters = new HashMap<String, Boolean>(capacity);

                String style = style_view.getSelectedItem().toString();
                filters.put(style, true);

//                filters.put("sitdown", sitdown_view.isChecked());
//                filters.put("takeout", takeout_view.isChecked());
//                filters.put("fastfood", fastfood_view.isChecked());

                String type = dining_type_view.getSelectedItem().toString();
                filters.put(type, true);

                String price = price_view.getSelectedItem().toString();
                filters.put(price, true);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("advanced_filters", filters);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
