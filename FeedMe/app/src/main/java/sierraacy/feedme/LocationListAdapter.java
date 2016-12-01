package sierraacy.feedme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Location;

import java.util.ArrayList;

/**
 * Created by sisis on 11/28/2016.
 */

public class LocationListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Business> locationList = new ArrayList<>();
    Context context;

    public LocationListAdapter(Context mContext) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = mContext;
    }

    public void createList(ArrayList<Business> locationList) {
        this.locationList = locationList;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.location_item, parent, false);
        }
        return bindView(convertView, position, parent);
    }

    protected View bindView(View theView, int position, ViewGroup parent) {
        final Business restaurant = (Business) getItem(position);
        final int restPos = position;

        TextView name = (TextView) theView.findViewById(R.id.name);
        TextView location = (TextView) theView.findViewById(R.id.location);
        TextView phone = (TextView) theView.findViewById(R.id.phone);
        RatingBar rating = (RatingBar) theView.findViewById(R.id.location_rating);
        TextView web = (TextView) theView.findViewById(R.id.website);

        name.setText(restaurant.name());
        Location loc = restaurant.location();
        ArrayList<String> address = loc.address();
        String str = "";
        for(int i = 0; i < address.size(); i++) {
            str += address.get(i) + " ";
        }
        String addr = "" + str + ", " + loc.city() + ", " + loc.stateCode() + " "
                + loc.postalCode();

        location.setText(addr);
        phone.setText(restaurant.displayPhone());
        double newRating = restaurant.rating();
        float usedRating = (float) newRating;
        rating.setRating(usedRating);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + restaurant.displayPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                context.startActivity(callIntent);
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(restaurant.mobileUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                context.startActivity(webIntent);
            }
        });

        return theView;
    }
}
