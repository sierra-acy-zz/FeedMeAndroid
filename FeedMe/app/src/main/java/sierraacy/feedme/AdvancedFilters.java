package sierraacy.feedme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class AdvancedFilters extends AppCompatActivity {

    Spinner style;
    CheckBox sitdown, takeout, fastfood;
    //slider??
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_filters);



    }
}
