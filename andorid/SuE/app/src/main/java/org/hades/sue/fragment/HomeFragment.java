package org.hades.sue.fragment;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.taro.headerrecycle.adapter.SimpleRecycleAdapter;
import com.taro.headerrecycle.helper.RecycleViewOnClickHelper;

import org.greenrobot.eventbus.EventBus;
import org.hades.sue.App;
import org.hades.sue.R;
import org.hades.sue.activity.HomeActivity;
import org.hades.sue.activity.RegisterActivity;
import org.hades.sue.activity.WebActivity;
import org.hades.sue.adapter.GridLocationAdapter;
import org.hades.sue.adapter.HomeDoctorAdapterOption;
import org.hades.sue.adapter.HomeEssayAdapterOption;
import org.hades.sue.adapter.HomeHospitalAdapterOption;
import org.hades.sue.base.BaseFragment;
import org.hades.sue.bean.AdBean;
import org.hades.sue.bean.DoctorBean;
import org.hades.sue.bean.HeathNews;
import org.hades.sue.bean.HospitalBean;
import org.hades.sue.bean.RData;
import org.hades.sue.helper.LayoutPopularModuleHelper;
import org.hades.sue.utils.POIUtils;
import org.hades.sue.utils.SnackUtils;
import org.hades.sue.utils.ToastUtils;
import org.hades.sue.utils.Values;
import org.hades.sue.utils.ViewUtils;
import org.hades.sue.view.PopularTitleView;
import org.hades.sue.view.SmileyHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hades on 2017/9/21.
 */

public class HomeFragment extends BaseFragment implements
        PopularTitleView.OnTitleMoreClickListener
,POIUtils.HospitalCallBack,OnItemClickListener{

    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.xref_view)
    XRefreshView xRefreshView;


    boolean dataLoad = false; //文章是否加载完成
    boolean isRefresh = false; //正在加载

    private BGATitleBar mTitleBar;
//    private SlidingMenu mSlidingMenu;
//
//    private GridView    gv_location;
    private GridLocationAdapter mGridAdapter;

    //获取附近医院
    private POIUtils poiUtils;

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

    private Handler mHandler = new Handler();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        mTitleBar = (BGATitleBar) mHomeActivity.getTitleBar();
        initBar();
        //initSlidingMenu();
        initRefView();
        initPopular();

        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
//                mSlidingMenu.toggle();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {
                EventBus.getDefault().post(HomeActivity.OPEN_QR);
            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });

    }

    /**
     * 初始化专栏
     */
    private void initPopular() {
        //初始化名医专栏
        mPopularDoctorHelper = new LayoutPopularModuleHelper(mViewDoctor);
        mPopularDoctorHelper.setTitle("名医预约");
        mPopularDoctorHelper.setRecyclerViewGridLayout
                (mHomeActivity, false, 3);
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
                (mHomeActivity, false, 3);
        mPopularHospitalHelper.setMoreClickListener(this);
        //预约医院
        RecycleViewOnClickHelper hospitalClickHelper = new RecycleViewOnClickHelper(mHomeActivity);
        hospitalClickHelper.attachToRecycleView(mPopularHospitalHelper.mRvContent);
        hospitalClickHelper.setOnItemClickListener(new RecycleViewOnClickHelper.OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position,
                                       RecyclerView.ViewHolder holder) {
                HospitalBean bean = (HospitalBean)
                        mHospitalAdapter.getItem(position);
                if (bean != null) {
                    //TODO:点击医院详情
                }
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
                HeathNews bean = (HeathNews) mEssayAdapter.getItem(position);
                if (bean != null) {
                    WebActivity.startActivity(App.mContext,bean);
                }
                return false;
            }
        });
    }

    /**
     * 初始化侧滑栏
     */
    private void initSlidingMenu() {
//        mSlidingMenu = new SlidingMenu(mHomeActivity);
//        mSlidingMenu.setMode(SlidingMenu.LEFT);
//        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
//        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
//        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        mSlidingMenu.setFadeDegree(0.35f);
//
//        mSlidingMenu.attachToActivity(mHomeActivity,
//                SlidingMenu.SLIDING_CONTENT,true);
//        //菜单view
//        View view = View.inflate(mHomeActivity, R.layout.location, null);
//        mSlidingMenu.setMenu(view);
//        gv_location = view.findViewById(R.id.gv_location);
//        gv_location.setOnItemClickListener(this);
    }

    /**
     * 初始化标题
     */
    private void initBar() {
        mTitleBar.setTitleText("首页");
        mTitleBar.getLeftCtv().setVisibility(View.VISIBLE);
        mTitleBar.getRightCtv().setVisibility(View.VISIBLE);

        //设置广告Banner
        mCbBanner.setOnItemClickListener(this);
    }

    private void initRefView() {
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setMoveForHorizontal(true);//拦截横移事件
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(getContext()));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                //如果正在刷新，并且是人为的
                if (isRefresh && isPullDown){
                    SnackUtils.showSnack(mCbBanner,"别着急喔,正在拼命加载中...");
                }else {
                    isRefresh = true; //刷新
                    initData();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!dataLoad){
                                xRefreshView.stopRefresh();
                                isRefresh = false;
                                SnackUtils.showSnack(mCbBanner, "网络差～");
                            }
                        }
                    }, 10 * 1000);
                }
            }
            @Override
            public void onLoadMore(boolean isSilence) {

            }
        });
        xRefreshView.startRefresh(); //刷新数据
        isRefresh = true;
        //如果超过10秒还没有停止刷新的话说明网络差
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!dataLoad){
                    xRefreshView.stopRefresh();
                    isRefresh = false;
                    SnackUtils.showSnack(mCbBanner, "网络差～");
                }
            }
        }, 10 * 1000);
    }

    @Override
    public void initData() {

        //获取附近医院
        poiUtils = new POIUtils(getContext(),this);
        HospitalThread hospitalThread = new HospitalThread();
        hospitalThread.start();
        //

        ViewUtils.setBanner(mCbBanner, new ViewUtils.DefaultBannerHolder(), getAdData());
        //init doctor
        getDoctorInfo();


        //init essay
        getEssay();


        mGridAdapter = new GridLocationAdapter(mHomeActivity,getLocationData());
        //gv_location.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
    }

    /**
     * 获取医生资讯
     */
    private void getDoctorInfo() {
        App.mSueService.getDoctorInfo(0,3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<List<DoctorBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RData<List<DoctorBean>> data) {
                        mDoctorAdapter = new SimpleRecycleAdapter
                                (mHomeActivity, new HomeDoctorAdapterOption(), data.data);
                        mPopularDoctorHelper.mRvContent.setAdapter(mDoctorAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<String> getLocationData() {
        List<String> data = new ArrayList<>();
        data.add("江门市");
        return data;
    }



    private List<AdBean> getAdData() {
        List<AdBean> data = new ArrayList<>();
        data.add(new AdBean(R.drawable.banner_test_0));
        data.add(new AdBean(R.drawable.banner_test_1));
        data.add(new AdBean(R.drawable.banner_test_2));
        return data;
    }

    private void getEssay() {
        App.mSueService.getHeathNews(0,5)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RData<List<HeathNews>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe 健康咨询");
                    }

                    @Override
                    public void onNext(RData<List<HeathNews>> listRData){
                        List<HeathNews> data = listRData.data;
                        if (data != null) {
                            mEssayAdapter = new SimpleRecycleAdapter(mHomeActivity,
                                    new HomeEssayAdapterOption(), data);
                            mPopularEssayHelper.mRvContent.setAdapter(mEssayAdapter);
                        } else {
                            Log.d(TAG, "资讯数据获取为空");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "资讯数据获取失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    public void onMoreClickListener(View view) {
        if (mPopularDoctorHelper.isMoreIconOnClick(view)) {
            ToastUtils.showShort(mHomeActivity, "更多名医");
        } else if (mPopularHospitalHelper.isMoreIconOnClick(view)) {
            EventBus.getDefault().post(HomeActivity.MORE_HOSPITAL);
        } else if (mPopularEssayHelper.isMoreIconOnClick(view)) {
            EventBus.getDefault().post(HomeActivity.MORE_NEWS);
        } else {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            initBar();
        }
        super.onHiddenChanged(hidden);
    }



    /**
     * 医院信息回调函数
     * @param data
     */
    @Override
    public void onLoad(List<HospitalBean> data) {
        //init hospital
        mHospitalAdapter = new SimpleRecycleAdapter(mHomeActivity
                , new HomeHospitalAdapterOption(), data);
        mPopularHospitalHelper.mRvContent.setAdapter(mHospitalAdapter);
        //停止刷新
        xRefreshView.stopRefresh();
        isRefresh = false;//不再刷新
        dataLoad = true;//设置刷新完成
    }

    /**
     * 广告Banner的点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        if (position == 1) {
            RegisterActivity.startActivity(getContext());
        }
    }

    final class HospitalThread extends Thread{
        @Override
        public void run() {
            while(App.mShareP.getFloat(Values.LATITUDE,-1f) == -1f ){
                //阻塞线程
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    poiUtils.getPOI("医院",App.mShareP.getString(
                            Values.LAST_LOCATION,"江门市"
                    ),0,3);
                }
            }, 150);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
