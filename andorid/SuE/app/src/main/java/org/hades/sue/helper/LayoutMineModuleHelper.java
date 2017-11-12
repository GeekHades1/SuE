package org.hades.sue.helper;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.hades.sue.App;
import org.hades.sue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by j1017 on 2017/9/28.
 */

public class LayoutMineModuleHelper implements View.OnClickListener {

    @BindView(R.id.lin_layout_mine_module)
    LinearLayout mLinMine;
    @BindView(R.id.mine_module_title)
    TextView mMineTitle;
    @BindView(R.id.mine_module_icon)
    ImageView mMineIcon;

    private View mContentView = null;

    OnMineClickListener mListener;

    public LayoutMineModuleHelper(View contentView) {
        mContentView = contentView;
        ButterKnife.bind(this, contentView);
    }


    public void setIcon(@DrawableRes int res) {
        Glide.with(App.mContext).load(res).into(mMineIcon);
    }

    public void setTitle(String title) {
        mMineTitle.setText(title);
    }


    public boolean isMineLinOnClick(@NonNull View view) {
        return isMineClickListenr(view);
    }

    public View.OnClickListener setOnLinClickListener(OnMineClickListener listener) {
        if (listener != null) {
            mLinMine.setOnClickListener(this);
            mListener = listener;
        }
        return this;
    }

    public boolean isMineClickListenr(View view) {
        return view == mLinMine;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onLinClickListener(view);
        }
    }

    public interface OnMineClickListener {
        public void onLinClickListener(View view);
    }
}
