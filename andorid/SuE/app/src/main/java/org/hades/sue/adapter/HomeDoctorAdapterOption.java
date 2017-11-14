package org.hades.sue.adapter;

import android.annotation.SuppressLint;
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

public class HomeDoctorAdapterOption extends SimpleRecycleAdapter.
        SimpleAdapterOption<DoctorBean> {
    @Override
    public int getViewType(int position) {
        return position;
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void setViewHolder(DoctorBean itemData, int position, @NonNull HeaderRecycleViewHolder holder) {
        if (itemData != null){
            ImageView ivDoctor = holder.getView(R.id.iv_item_popular_doctor_pic);
            TextView tvReserve = holder.getView(R.id.tv_item_popular_doctor_reserve);
            TextView tvName = holder.getView(R.id.tv_item_popular_doctor_name);
            TextView tvPro = holder.getView(R.id.tv_item_popular_doctor_pro);
            tvName.setText(itemData.name);
            tvPro.setText(itemData.department);
            tvReserve.setText("预约");

            //预约操作

            Glide.with(App.mContext)
                    .load(itemData.img)
                    .into(ivDoctor);
        }

    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_home_popular_doctor;
    }

    @Override
    public void onCreateViewEverytime(@NonNull View itemView, @NonNull ViewGroup parentView, @NonNull HeaderRecycleAdapter adapter, int viewType) {
        super.onCreateViewEverytime(itemView, parentView, adapter, viewType);
    }
}
