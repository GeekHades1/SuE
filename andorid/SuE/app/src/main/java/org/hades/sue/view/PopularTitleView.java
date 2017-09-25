package org.hades.sue.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.hades.sue.R;


/**
 * Created by taro on 2017/3/20.
 */

public class PopularTitleView extends FrameLayout implements View.OnClickListener {
    View mVContainer;
    TextView mTvTitle;
    TextView mTvMore;
    View mVTag;

    Rect mMoreBtnPadding;
    Drawable mMoreBtnBackground;
    int mMoreBtnTextColor = Integer.MIN_VALUE;
    int mMoreTextColor = Integer.MIN_VALUE;

    OnTitleMoreClickListener mListener;

    public PopularTitleView(Context context) {
        super(context);
        init(context, null);
    }

    public PopularTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PopularTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null && mVContainer == null) {
            mVContainer = LayoutInflater.from(context).inflate(R.layout.layout_popular_module_title, this);
            mTvTitle = (TextView) mVContainer.findViewById(R.id.tv_layout_popular_module_title);
            mTvMore = (TextView) mVContainer.findViewById(R.id.tv_layout_popular_module_more);
            mVTag = mVContainer.findViewById(R.id.v_layout_popular_module_tag);

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PopularTitleView);
            String title = ta.getString(R.styleable.PopularTitleView_titleText);
            String more = ta.getString(R.styleable.PopularTitleView_moreText);
            int accentColorTint = ta.getColor(R.styleable.PopularTitleView_accentColorTint, Integer.MIN_VALUE);
            int tagColor = ta.getColor(R.styleable.PopularTitleView_tagColor, Integer.MIN_VALUE);
            mMoreTextColor = ta.getColor(R.styleable.PopularTitleView_moreTextColor, Integer.MIN_VALUE);
            mMoreBtnTextColor = ta.getColor(R.styleable.PopularTitleView_moreBtnTextColor, Integer.MIN_VALUE);
            boolean enableBtn = ta.getBoolean(R.styleable.PopularTitleView_enableMoreBtn, false);
            boolean hideMore = ta.getBoolean(R.styleable.PopularTitleView_hideMoreText, false);
            mMoreBtnBackground = ta.getDrawable(R.styleable.PopularTitleView_moreBtnBackground);
            Drawable containerBackground = ta.getDrawable(R.styleable.PopularTitleView_backgroundDrawable);

            if (containerBackground != null) {
                mVContainer.setBackgroundDrawable(containerBackground);
            }
            if (title != null) {
                mTvTitle.setText(title);
            }
            if (more != null) {
                mTvMore.setText(more);
            }
            if (tagColor != Integer.MIN_VALUE) {
                mVTag.setBackgroundColor(tagColor);
            }
            if (mMoreTextColor != Integer.MIN_VALUE) {
                mTvMore.setTextColor(mMoreTextColor);
            }

            if (accentColorTint != Integer.MIN_VALUE) {
                setAccentColor(accentColorTint);
            }
            if (enableBtn) {
                setEnableMoreBtn(true);
            }
            if (hideMore) {
                setMoreVisibility(false);
            }

            ta.recycle();
        }
    }

    public PopularTitleView setTitle(@StringRes int res) {
        mTvTitle.setText(res);
        return this;
    }

    public PopularTitleView setMore(@StringRes int res) {
        mTvMore.setText(res);
        return this;
    }

    public PopularTitleView setTitle(String text) {
        mTvTitle.setText(text);
        return this;
    }

    public PopularTitleView setMore(String text) {
        mTvMore.setText(text);
        return this;
    }

    public PopularTitleView setTagColor(@ColorRes int colorRes) {
        int color = getContext().getResources().getColor(colorRes);
        mVTag.setBackgroundColor(color);
        return this;
    }

    public PopularTitleView setAccentColorTint(@ColorRes int colorRes) {
        int color = getContext().getResources().getColor(colorRes);
        mVTag.setBackgroundColor(color);
        mTvMore.setTextColor(color);
        return this;
    }

    public PopularTitleView setAccentColor(@ColorInt int color) {
        mVTag.setBackgroundColor(color);
        mTvMore.setTextColor(color);
        return this;
    }

    public PopularTitleView setOnMoreClickListener(OnTitleMoreClickListener listener) {
        if (listener != null) {
            mTvMore.setOnClickListener(this);
            mListener = listener;
        }
        return this;
    }

    public PopularTitleView setMoreVisibility(boolean isVisible) {
        mTvMore.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public boolean isMoreClickListenr(View view) {
        return view == mTvMore;
    }

    public PopularTitleView setEnableMoreBtn(boolean isEnable) {
        if (isEnable) {
            if (mMoreBtnPadding == null || mMoreBtnPadding.isEmpty()) {
                mMoreBtnPadding = new Rect();
                mMoreBtnPadding.left = (int) convertDp2Px(10);
                mMoreBtnPadding.top = (int) convertDp2Px(2);
                mMoreBtnPadding.right = (int) convertDp2Px(10);
                mMoreBtnPadding.bottom = (int) convertDp2Px(2);
            }
            mTvMore.setPadding(mMoreBtnPadding.left, mMoreBtnPadding.top, mMoreBtnPadding.right, mMoreBtnPadding.bottom);
            if (mMoreBtnBackground != null) {
                mTvMore.setBackgroundDrawable(mMoreBtnBackground);
            }
            if (mMoreBtnTextColor != Integer.MIN_VALUE) {
                mTvMore.setTextColor(mMoreBtnTextColor);
            }
        } else {
            mTvMore.setPadding(0, 0, 0, 0);
            mTvMore.setBackgroundDrawable(null);
            if (mMoreTextColor != Integer.MIN_VALUE) {
                mTvMore.setTextColor(mMoreTextColor);
            }
        }
        return this;
    }

    public float convertDp2Px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onMoreClickListener(v);
        }
    }

    public interface OnTitleMoreClickListener {
        public void onMoreClickListener(View view);
    }
}
