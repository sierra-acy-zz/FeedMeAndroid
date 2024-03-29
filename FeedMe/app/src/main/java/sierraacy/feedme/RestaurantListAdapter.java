package sierraacy.feedme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sisis on 10/25/2016.
 */

public class RestaurantListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Restaurant> restaurantList = new ArrayList<>();
    ImageButton edit, delete;
    Context context;

    final int EDIT_CODE = 3;

    public RestaurantListAdapter(Context mContext) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = mContext;
    }

    public void createList(ArrayList<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;

    }
    public void addItem(final Restaurant restaurant) {
        restaurantList.add(restaurant);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        restaurantList.remove(position);
        if (restaurantList.size() > 0) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return restaurantList;
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
                intent.putExtra("restaurant", restaurantList.get(restPos));
                intent.putExtra("position", restPos);
                ((Activity) context).startActivityForResult(intent, EDIT_CODE);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantList.remove(restPos);
                notifyDataSetChanged();
            }
        });


        return theView;
    }
}
