package org.hades.sue.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hades.sue.R;
import org.hades.sue.utils.BodyCheckModuleAnimator;

/**
 * Created by Hades on 2017/9/28.
 */

public class BodyCheckModuleView extends LinearLayout implements BodyCheckModuleAnimator.
        OnAnimatorCallBack{

    private ImageView mLeftIcon;
    private TextView mMainTv;
    private TextView mDetailsTv;
    private Context mContext;

    private BodyCheckModuleAnimator mAnim;

    public BodyCheckModuleView(Context context) {
        this(context,null);
    }

    public BodyCheckModuleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BodyCheckModuleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = inflate(context, R.layout.item_bodycheck_module,null);
        mLeftIcon = view.findViewById(R.id.iv_item_bodycheck_left_icon);
        mMainTv = view.findViewById(R.id.tv_item_bodycheck_main);
        mDetailsTv = view.findViewById(R.id.tv_item_bodycheck_details);
        addView(view);
        mAnim = new BodyCheckModuleAnimator(this,mContext,(long)2000);
        //initAnim();
    }

    private void initAnim() {
        mAnim.setAlpha(1f,0f,1f);
        mAnim.setScaleAnimY(1f,1.5f,1f);
        mAnim.setTransAnimX(0f,-getWidth(),0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initAnim();
    }

    public void setLeftIcon(@DrawableRes int res){
        mLeftIcon.setImageResource(res);
    }

    public void setMainText(@StringRes int res){
        mMainTv.setText(res);
    }

    public void setMainText(String text){
        mMainTv.setText(text);
    }

    public void setDetailsText(@StringRes int res){
        mDetailsTv.setText(res);
    }

    public void setDetailsText(String text){
        mDetailsTv.setText(text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mAnim.startAnim();
        return super.onTouchEvent(event);
    }

    @Override
    public void onEnd() {
        setEnabled(true);
    }

    @Override
    public void onStart() {
        //setEnabled(false);
    }
}
