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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class AdvancedFilters extends AppCompatActivity {

    Spinner style_view, dining_type_view, price_view;
    Button apply, cancel, clear;
    AppliedFilters filters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_filters);

        apply = (Button) findViewById(R.id.btn_apply_advanced);
        cancel = (Button) findViewById(R.id.btn_cancel_advanced);
        clear = (Button) findViewById(R.id.btn_clear_filters);

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

        price_view = (Spinner) findViewById(R.id.max_price);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_array, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_view.setAdapter(priceAdapter);

        Intent intent = getIntent();
        filters = (AppliedFilters) intent.getSerializableExtra("filters");
        if(filters != null) {
            populate();
        }

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
                String selected = style_view.getSelectedItem().toString();
                if(!selected.equals("Select a style!"))
                    filters.styles.add(selected);

                selected = dining_type_view.getSelectedItem().toString();
                if(!selected.equals("Select the dining type!"))
                    filters.dining.add(selected);

                selected = price_view.getSelectedItem().toString();
                if(!selected.equals("Select the price!"))
                    filters.price.add(selected);

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

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style_view.setSelection(0);
                dining_type_view.setSelection(0);
                price_view.setSelection(0);
                clearFilters();
            }
        });
    }

    public void populate() {
        String[] arr = getResources().getStringArray(R.array.styles_array);
        for(int i = 0; i < arr.length; i++) {
            String style = arr[i];
            if(filters.styles.contains(style)) {
                style_view.setSelection(getPosition(style, arr));
            }
        }

        arr = getResources().getStringArray(R.array.dining_array);
        for(int i = 0; i < arr.length; i++) {
            String dining = arr[i];
            if(filters.dining.contains(dining)) {
                dining_type_view.setSelection(getPosition(dining, arr));
            }
        }
        arr = getResources().getStringArray(R.array.price_array);
        for(int i = 0; i < arr.length; i++) {
            String price = arr[i];
            if(filters.price.contains(price)) {
                price_view.setSelection(getPosition(price, arr));
            }
        }
    }

    public int getPosition(String match, String[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].equals(match))
                return i;
        }
        return -1;
    }

    public void clearFilters(){
        filters.styles.clear();
        filters.dining.clear();
        filters.price.clear();
    }
}
