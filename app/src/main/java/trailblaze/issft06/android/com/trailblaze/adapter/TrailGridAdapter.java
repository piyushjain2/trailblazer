package trailblaze.issft06.android.com.trailblaze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.model.Trail;

import java.util.ArrayList;

/**
 * Created by flip on 13/03/18.
 */

public class TrailGridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Trail> mTrails;

    public TrailGridAdapter(Context context, ArrayList<Trail> trails) {
        mContext = context;
        mTrails = trails;
    }

    @Override
    public int getCount() {
        return mTrails.size();
    }

    @Override
    public Object getItem(int i) {
        return mTrails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.trail_item, null);
            holder = new ViewHolder();
            holder.trail_id = (TextView)convertView.findViewById(R.id.tv_list_trail_id);
            holder.trail_name=(TextView) convertView.findViewById(R.id.tv_list_trail_name);
            holder.trail_stations = convertView.findViewById(R.id.tv_num_trail_stations);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trail_id.setText(mTrails.get(i).getId());
        holder.trail_name.setText(mTrails.get(i).getName());
//        holder.trail_stations.setText(mTrails.get(i));

        return convertView;
    }


    private static class ViewHolder {
        TextView trail_id;
        TextView trail_name;
        TextView trail_stations;
    }
}