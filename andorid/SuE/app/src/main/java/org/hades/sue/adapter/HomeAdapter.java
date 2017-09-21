package org.hades.sue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hades.sue.R;

import java.util.List;

/**
 * Created by Hades on 2017/9/21.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<String>   data;
    Context        mContext;

    public HomeAdapter(Context context,List<String> data){
        this.data = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.test_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;


        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_test);
        }
    }

}
