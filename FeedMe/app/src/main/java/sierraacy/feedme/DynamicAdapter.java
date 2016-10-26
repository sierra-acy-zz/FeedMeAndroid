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
import org.json.JSONObject;

/**
 * Created by sisis on 10/25/2016.
 */

public class DynamicAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private JSONArray restaruantList = new JSONArray();

    public DynamicAdapter(Context mContext) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final JSONObject restaurant) {
        restaruantList.put(restaurant);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        restaruantList.remove(position);
        if (restaruantList.length() > 0) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }
    }

    @Override
    public int getCount() {
        return restaruantList.length();
    }

    @Override
    public Object getItem(int position) {
        return restaruantList.length();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.restaurant_item, parent, false);
        }
        return bindView(convertView, position, parent);
    }

    protected View bindView(View theView, int position, ViewGroup parent) {
        String text = (String) String.valueOf(getItem(position));
        Restaurant restaurant = (Restaurant) getItem(position);

        TextView theTextView = (TextView) theView.findViewById(R.id.restaurant_name);
        theTextView.setText(restaurant.name);

        ImageButton editBtn = (ImageButton) theView.findViewById(R.id.btn_edit);
        editBtn.setImageResource(R.drawable.edit_icon);

        ImageButton deleteBtn = (ImageButton) theView.findViewById(R.id.btn_delete);
        deleteBtn.setImageResource(R.drawable.delete_icon);

        return theView;
    }
}
