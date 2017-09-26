package org.hades.sue.adapter;

import android.support.annotation.NonNull;
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
import org.hades.sue.bean.DoctorBean;

/**
 * Created by taro on 16/10/17.
 */

public class HomeHospitalAdapterOption extends SimpleRecycleAdapter.SimpleAdapterOption<DoctorBean> {
    @Override
    public int getViewType(int position) {
        return position;
    }

    @Override
    public void setViewHolder(DoctorBean itemData, int position, @NonNull HeaderRecycleViewHolder holder) {
        ImageView ivDoctor = holder.getView(R.id.iv_item_popular_doctor_pic);
        TextView tvReserve = holder.getView(R.id.tv_item_popular_doctor_reserve);
        tvReserve.setText("详情");
        Glide.with(App.mContext)
                .load(R.drawable.icon_normal_kobe)
                .into(ivDoctor);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_popular_doctor;
    }

    @Override
    public void onCreateViewEverytime(@NonNull View itemView, @NonNull ViewGroup parentView, @NonNull HeaderRecycleAdapter adapter, int viewType) {
        super.onCreateViewEverytime(itemView, parentView, adapter, viewType);
        this.setAdjustCount(adapter.getItemCount());
    }
}
