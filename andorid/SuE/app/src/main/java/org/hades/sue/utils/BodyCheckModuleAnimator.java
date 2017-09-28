package org.hades.sue.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

/**
 * Created by Hades on 2017/9/28.
 */

public class BodyCheckModuleAnimator implements Animator.AnimatorListener{

    private View mView;
    private Context mContext;
    private long    mDelay;

    private AnimatorSet mAnimSet;

    private ObjectAnimator mTransAnim;
    private ObjectAnimator mAlphaAnim ;
    private ObjectAnimator mScaleAnim;
    private ObjectAnimator mRotateAnim;

    private OnAnimatorCallBack mCallBack;
    public interface OnAnimatorCallBack{
        void onEnd();
        void onStart();
    }

    /**
     * 动画构造器
     * @param view 需要执行动画的View
     * @param context 上下文
     * @param delay 动画执行时间
     */
    public BodyCheckModuleAnimator(View view, Context context, Long delay) {
        this.mView = view;
        this.mContext = context;
        this.mDelay = delay;
        mAnimSet = new AnimatorSet();
        mAnimSet.addListener(this);
    }

    public void setCallBack(OnAnimatorCallBack callBack){
        if (callBack instanceof OnAnimatorCallBack){
            mCallBack = callBack;
        }else {
            throw new ClassCastException();
        }
    }

    /**
     * 设置平移动画
     * @param args 平移位置参数集合
     */
    public void setTransAnimX(float ...args){
        mTransAnim = ObjectAnimator.ofFloat(mView,"translationX",
                args);
        mTransAnim.setDuration(mDelay);
    }
    public void setTransAnimY(float ...args){
        mTransAnim = ObjectAnimator.ofFloat(mView,"translationY",
                args);
        mTransAnim.setDuration(mDelay);
    }

    public void setScaleAnimY(float ...args){
        mScaleAnim = ObjectAnimator.ofFloat(mView, "ScaleY", args);
    }
    public void setScaleAnimX(float ...args){
        mScaleAnim = ObjectAnimator.ofFloat(mView, "ScaleX", args);
    }

    public void setAlpha(float ...args){
        mAlphaAnim = ObjectAnimator.ofFloat(mView, "alpha", args);
    }

    public void startAnim(){
        mAnimSet.play(mTransAnim).with(mScaleAnim).with(mAlphaAnim);
        mAnimSet.start();
    }


    @Override
    public void onAnimationStart(Animator animator) {
        if (mCallBack != null) {
            mCallBack.onStart();
        }
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (mCallBack != null) {
            mCallBack.onEnd();
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
