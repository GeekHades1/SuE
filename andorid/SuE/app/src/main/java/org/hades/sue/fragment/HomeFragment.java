package org.hades.sue.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;
import com.taro.headerrecycle.helper.RecycleViewOnClickHelper;

import org.hades.sue.R;
import org.hades.sue.adapter.HomeAdapter;
import org.hades.sue.adapter.HomeDoctorAdapterOption;
import org.hades.sue.adapter.HomeEssayAdapterOption;
import org.hades.sue.adapter.HomeHospitalAdapterOption;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.AdBean;
import org.hades.sue.bean.DoctorBean;
import org.hades.sue.bean.EssayBean;
import org.hades.sue.helper.LayoutPopularModuleHelper;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.ViewUtils;
import org.hades.sue.view.PopularTitleView;
import org.hades.sue.view.SmileyHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by Hades on 2017/9/21.
 */

public class HomeFragment extends BaseFragment implements PopularTitleView.OnTitleMoreClickListener {


    @BindView(R.id.xref_view)
    XRefreshView xRefreshView;

    private RecyclerView.LayoutManager mLayoutManager;
    private HomeAdapter mAdapter;

    private BGATitleBar mTitleBar;

    @BindView(R.id.cb_index_banner)
    ConvenientBanner mCbBanner;

    @BindView(R.id.layout_popular_module_home_doctor)
    View mViewDoctor;
    @BindView(R.id.layout_popular_module_home_hospital)
    View mViewHospital;
    @BindView(R.id.layout_popular_module_essay)
    View mViewEssay;

    private LayoutPopularModuleHelper mPopularDoctorHelper;
    private LayoutPopularModuleHelper mPopularHospitalHelper;
    private LayoutPopularModuleHelper mPopularEssayHelper;

    private SimpleRecycleAdapter mDoctorAdapter;
    private SimpleRecycleAdapter mHospitalAdapter;
    private SimpleRecycleAdapter mEssayAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        //初始化名医专栏
        mPopularDoctorHelper = new LayoutPopularModuleHelper(mViewDoctor);
        mPopularDoctorHelper.setTitle("名医预约");
        mPopularDoctorHelper.setRecyclerViewGridLayout
                (mHomeActivity, false, 4);
        mPopularDoctorHelper.setMoreClickListener(this);
        //预约名医
        RecycleViewOnClickHelper doctorClickHelper = new RecycleViewOnClickHelper(getActivity());
        doctorClickHelper.attachToRecycleView(mPopularDoctorHelper.mRvContent);
        doctorClickHelper.setOnItemClickListener(new RecycleViewOnClickHelper.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, RecyclerView.ViewHolder holder) {
                DoctorBean bean = (DoctorBean) mDoctorAdapter.getItem(position);
                if (bean != null) {
                    ToastUtils.showShort(mHomeActivity, "预约");
                }
                return false;
            }
        });
        //初始化附近医院专栏
        mPopularHospitalHelper = new LayoutPopularModuleHelper(mViewHospital);
        mPopularHospitalHelper.setTitle("附近医院");
        mPopularHospitalHelper.setTagColor(R.color.green_bg_base);
        mPopularHospitalHelper.setRecyclerViewGridLayout
                (mHomeActivity, false, 4);
        mPopularHospitalHelper.setMoreClickListener(this);
        //预约医院
        RecycleViewOnClickHelper hospitalClickHelper = new RecycleViewOnClickHelper(mHomeActivity);
        hospitalClickHelper.attachToRecycleView(mPopularHospitalHelper.mRvContent);
        hospitalClickHelper.setOnItemClickListener(new RecycleViewOnClickHelper.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, RecyclerView.ViewHolder holder) {
                ToastUtils.showShort(mHomeActivity, "预约 h");
                return false;
            }
        });
        //初始化健康资讯专栏
        mPopularEssayHelper = new LayoutPopularModuleHelper(mViewEssay);
        mPopularEssayHelper.setTitle("健康资讯");
        mPopularEssayHelper.setTagColor(R.color.colorAccent);
        mPopularEssayHelper.setRecyclerViewLinearLayout(mHomeActivity, false);
        mPopularEssayHelper.setMoreClickListener(this);
        //资讯点击
        RecycleViewOnClickHelper essayClickHelper = new RecycleViewOnClickHelper(getActivity());
        essayClickHelper.attachToRecycleView(mPopularEssayHelper.mRvContent);
        essayClickHelper.setOnItemClickListener(new RecycleViewOnClickHelper.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, RecyclerView.ViewHolder holder) {
                EssayBean bean = (EssayBean) mEssayAdapter.getItem(position);
                if (bean != null) {
                    ToastUtils.showShort(mHomeActivity, "点击文章");
                }
                return false;
            }
        });

        //设置布局
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        initRefView();
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();

        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                ToastUtils.showLong(mHomeActivity, "点击左侧");
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {
                ToastUtils.showLong(mHomeActivity, "点击右侧");
            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });

    }

    private void initRefView() {
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(getContext()));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }
        });
    }

    @Override
    public void initData() {
        ViewUtils.setBanner(mCbBanner, new ViewUtils.DefaultBannerHolder(), getAdData());
        //init doctor
        mDoctorAdapter = new SimpleRecycleAdapter
                (mHomeActivity, new HomeDoctorAdapterOption(), getData());
        mPopularDoctorHelper.mRvContent.setAdapter(mDoctorAdapter);

        //init hospital
        mHospitalAdapter = new SimpleRecycleAdapter(mHomeActivity
                , new HomeHospitalAdapterOption(), getData());
        mPopularHospitalHelper.mRvContent.setAdapter(mHospitalAdapter);
        //init essay
        mEssayAdapter = new SimpleRecycleAdapter(mHomeActivity,
                new HomeEssayAdapterOption(), getEssay());
        mPopularEssayHelper.mRvContent.setAdapter(mEssayAdapter);

    }

    private List<DoctorBean> getData() {
        List<DoctorBean> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            data.add(new DoctorBean("Hades"));
        }
        return data;
    }

    private List<AdBean> getAdData() {
        List<AdBean> data = new ArrayList<>();
        data.add(new AdBean(R.drawable.banner_test_0));
        data.add(new AdBean(R.drawable.banner_test_1));
        data.add(new AdBean(R.drawable.banner_test_2));
        return data;
    }

    private List<EssayBean> getEssay() {
        List<EssayBean> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new EssayBean(true, "健康资讯测试文章----" + i, ""));
        }
        return data;
    }

    @Override
    public void onMoreClickListener(View view) {
        if (mPopularDoctorHelper.isMoreIconOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "更多名医");
        } else if (mPopularHospitalHelper.isMoreIconOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "更多医院");
        } else if (mPopularEssayHelper.isMoreIconOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "更多文章");
        } else {

        }
    }
}
