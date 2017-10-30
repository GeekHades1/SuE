package org.hades.sue.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taro.headerrecycle.adapter.HeaderRecycleAdapter;
import com.taro.headerrecycle.adapter.HeaderRecycleViewHolder;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;

import org.hades.sue.R;
import org.hades.sue.bean.HeathNews;

/**
 * Created by taro on 16/10/17.
 */

public class HomeEssayAdapterOption extends SimpleRecycleAdapter.
        SimpleAdapterOption<HeathNews> {
    @Override
    public int getViewType(int position) {
        return position;
    }

    @Override
    public void setViewHolder(HeathNews itemData, int position, @NonNull HeaderRecycleViewHolder holder) {

        TextView tvReserve = holder.getView(R.id.tv_popular_essay);
        if (true){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tvReserve.setCompoundDrawables(null
                ,null,null,null);
            }

        }
        tvReserve.setText(itemData.title);

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_popular_essay;
    }

    @Override
    public void onCreateViewEverytime(@NonNull View itemView, @NonNull ViewGroup parentView, @NonNull HeaderRecycleAdapter adapter, int viewType) {
        super.onCreateViewEverytime(itemView, parentView, adapter, viewType);
        this.setAdjustCount(adapter.getItemCount());
    }
}
