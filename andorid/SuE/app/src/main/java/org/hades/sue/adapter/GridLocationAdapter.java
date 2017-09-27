package org.hades.sue.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.hades.sue.R;

import java.util.List;

/**
 * Created by Hades on 2017/9/27.
 */

public class GridLocationAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> data;

    public GridLocationAdapter(Context context, List<String> data){
        mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View containerView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (containerView == null) {
            containerView = View.inflate(mContext, R.layout.item_location, null);
            holder = new ViewHolder(containerView);
            containerView.setTag(holder);
        }else {
            holder = (ViewHolder) containerView.getTag();
        }
        holder.tv_location.setText(data.get(position));
        return containerView;
    }

    class ViewHolder{
        public TextView tv_location;
        public ViewHolder(View view){
            tv_location = view.findViewById(R.id.tv_item_location);
        }
    }
}
