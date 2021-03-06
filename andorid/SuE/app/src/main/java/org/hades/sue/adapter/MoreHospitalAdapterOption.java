package org.hades.sue.adapter;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taro.headerrecycle.adapter.HeaderRecycleViewHolder;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;

import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.bean.HospitalBean;

/**
 * Created by Hades on 2017/11/9.
 */

public class MoreHospitalAdapterOption extends SimpleRecycleAdapter.
        SimpleAdapterOption<HospitalBean>{
    @Override
    public int getViewType(int position) {
        return position;
    }

    @Override
    public void setViewHolder(HospitalBean itemData, int position, @NonNull HeaderRecycleViewHolder holder) {
        if (itemData != null) {
            ImageView ivDoctor = holder.getView(R.id.iv_item_popular_doctor_pic);
            TextView tvReserve = holder.getView(R.id.tv_item_popular_doctor_reserve);
            TextView tvName = holder.getView(R.id.tv_item_popular_hospital_name);
            TextView tvDistance = holder.getView(R.id.tv_item_popular_hospital_distance);
            tvName.setText(itemData.name);
            //增加距离
            tvDistance.setText(String.format(App.mContext.getResources().
                            getString(R.string.hospital_distance),
                    itemData.distance));
            tvReserve.setText("详情");
            Glide.with(App.mContext)
                    .load(itemData.photo)
                    .into(ivDoctor);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_more_hospital;
    }
}
