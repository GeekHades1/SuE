package org.hades.sue.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import org.hades.sue.R;
import org.hades.sue.bean.AdBean;

import java.util.List;

/**
 * Created by Hades on 2017/9/25.
 */

public class ViewUtils {

    public static final <T> void setBanner(@NonNull ConvenientBanner banner, @NonNull CBViewHolderCreator<Holder<T>> creator, List<T> data) {
        banner.setPageIndicator(new int[]{R.drawable.shape_dot_light_gray_indicator, R.drawable.shape_dot_bright_green_indicator});
        banner.startTurning(2000);
        banner.setPages(creator, data);
    }

    public static class DefaultBannerHolder implements CBViewHolderCreator<Holder<AdBean>> {
        @Override
        public Holder<AdBean> createHolder() {
            return new DefaultHolder();
        }

        public static class DefaultHolder implements Holder<AdBean> {
            private ImageView mIvBanner = null;

            @Override
            public View createView(Context context) {
                mIvBanner = new ImageView(context);
                mIvBanner.setScaleType(ImageView.ScaleType.FIT_XY);
                return mIvBanner;
            }

            @Override
            public void UpdateUI(Context context, int position, AdBean data) {
                Glide.with(context)
                        .load(data.logo)
                        .into(mIvBanner);
            }
        }
    }
}
