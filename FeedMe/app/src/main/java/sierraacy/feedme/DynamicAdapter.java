package sierraacy.feedme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sisis on 10/25/2016.
 */

public class DynamicAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Restaurant> restaruantList = new ArrayList<>();
    ImageButton edit, delete;
    Context context;

    public DynamicAdapter(Context mContext) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = mContext;
    }

    public void createList(ArrayList<Restaurant> restaurantList) {
        this.restaruantList = restaurantList;

    }
    public void addItem(final Restaurant restaurant) {
        restaruantList.add(restaurant);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        restaruantList.remove(position);
        if (restaruantList.size() > 0) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    @Override
    public int getCount() {
        return restaruantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaruantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return restaruantList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.restaurant_item, parent, false);
        }
        return bindView(convertView, position, parent);
    }

    protected View bindView(View theView, int position, ViewGroup parent) {
        Restaurant restaurant = (Restaurant) getItem(position);
        final int restPos = position;


        TextView theTextView = (TextView) theView.findViewById(R.id.restaurant_name);
        theTextView.setText(restaurant.name);

        edit = (ImageButton) theView.findViewById(R.id.btn_edit);
        delete = (ImageButton) theView.findViewById(R.id.btn_delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddRestaurant.class);
                intent.putExtra("restaurant", restaruantList.get(restPos));
                ((Activity) context).startActivityForResult(intent, 1);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaruantList.remove(restPos);
                notifyDataSetChanged();
            }
        });


        return theView;
    }
}
