package sierraacy.feedme;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sisis on 10/25/2016.
 */

public class DynamicAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Restaurant> restaruantList = new ArrayList<>();

    public DynamicAdapter(Context mContext) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Restaurant r = new Restaurant("Taco Bell", "fast-food", "fast-food", 1);
        addItem(r);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.restaurant_item, parent, false);
        }
        return bindView(convertView, position, parent);
    }

    protected View bindView(View theView, int position, ViewGroup parent) {
        Restaurant restaurant = (Restaurant) getItem(position);

        TextView theTextView = (TextView) theView.findViewById(R.id.restaurant_name);
        theTextView.setText(restaurant.name);

        return theView;
    }
}
