package org.hades.sue.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taro.headerrecycle.adapter.HeaderRecycleAdapter;
import com.taro.headerrecycle.adapter.HeaderRecycleViewHolder;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.bean.HeathNews;

/**
 * Created by Hades on 2017/10/29.
 */

public class MoreNewsAdapterOption extends SimpleRecycleAdapter.
        SimpleAdapterOption<HeathNews>{

    @Override
    public int getViewType(int position) {
        return position;
    }

    @Override
    public void setViewHolder(HeathNews itemData, int position,
                              @NonNull HeaderRecycleViewHolder holder) {
        ImageView icon = holder.getView(R.id.news_img);
        TextView title = holder.getView(R.id.news_title);
        TextView detail = holder.getView(R.id.news_details);
        TextView date = holder.getView(R.id.news_date);
        //TODO:icon 保留

        Glide.with(holder.getRootView())
                .load(itemData.img)
                .into(icon);
        title.setText(itemData.title);
        detail.setText(itemData.detail);
        String dateStr = itemData.date;
        String year = dateStr.substring(0,4);
        String month = dateStr.substring(4,6);
        String day = dateStr.substring(6, 8);

        String dateF = String.format(App.mContext.getString(R.string.news_date_f),
                year, month, day);
        Log.d("date", dateF);
        date.setText(dateF);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.more_news_item;
    }

    @Override
    public void onCreateViewEverytime(@NonNull View itemView, @NonNull ViewGroup parentView, @NonNull HeaderRecycleAdapter adapter, int viewType) {
        super.onCreateViewEverytime(itemView, parentView, adapter, viewType);
        //this.setAdjustCount(adapter.getItemCount());
    }
}
