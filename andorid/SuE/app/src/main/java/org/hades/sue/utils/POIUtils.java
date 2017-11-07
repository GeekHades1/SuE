package org.hades.sue.utils;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import org.hades.sue.App;
import org.hades.sue.bean.HospitalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/11/7.
 * 地图检索工具
 */

public class POIUtils implements PoiSearch.OnPoiSearchListener{

    private PoiSearch.Query query;

    private Context context;

    private PoiSearch poiSearch;

    private HospitalCallBack callBack;

    public interface HospitalCallBack{
        void onLoad(List<HospitalBean> data);
    }

    public POIUtils(Context context,HospitalCallBack callBack){
        this.context = context;
        this.callBack = callBack;
    }


    /**
     * 获取附近医院
     * @param keyWord
     * @param cityCode
     * @param page
     * @param count
     */
    public  void getPOI(String keyWord,String cityCode,int page,int count){
        query = new PoiSearch.Query(keyWord, "", cityCode);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(count);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码
        poiSearch = new PoiSearch(context, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(App.mShareP.
                getFloat(Values.LATITUDE,22.58f),
                App.mShareP.getFloat(Values.LONGITUDE,113.08f)), 10000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 回调接口
     * @param poiResult
     * @param i
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        List<HospitalBean> data = new ArrayList<>();
        for (PoiItem item:poiResult.getPois()){
            HospitalBean bean = new HospitalBean("");
            bean.name = item.getTitle();
            bean.tel = item.getTel();
            bean.distance = item.getDistance();
            for (Photo photo : item.getPhotos()){
                //Log.d("地图", "相片"+photo.getUrl()+photo.getTitle());
                bean.photo = photo.getUrl();
                break;
            }
            data.add(bean);
        }
        callBack.onLoad(data);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
