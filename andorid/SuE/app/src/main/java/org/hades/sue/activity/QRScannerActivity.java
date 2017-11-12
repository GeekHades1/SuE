package org.hades.sue.activity;

import android.Manifest;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.api.Permissions4M;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class QRScannerActivity extends BaseActivity implements QRCodeView.Delegate{

    private static final String TAG = QRScannerActivity.class.getSimpleName();

    private static final int CAMERA_CODE = 1;

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;


    @BindView(R.id.my_title_bar_back)
    BGATitleBar mTitle;

    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrscanner;
    }

    @Override
    public View getTitleBar() {
        return mTitle;
    }

    @Override
    public void initViews() {
        initBar();
        mQRCodeView.setDelegate(this);
    }

    private void initBar() {
        mTitle.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                QRScannerActivity.this.finish();
                addScaleOut();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {

            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }


    @PermissionsGranted(CAMERA_CODE)
    public void granted(){
        //获取成功
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @PermissionsDenied(CAMERA_CODE)
    public void denied(){
        //获取权限失败
        this.finish();
        addScaleOut();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    /**
     * 获取摄像头权限
     */
    private void requestCodeQRCodePermissions() {
        Permissions4M.get(this)
                .requestPermissions(Manifest.permission.CAMERA)
                .requestCodes(CAMERA_CODE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        //Log.i(TAG, "result:" + result);
        //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        doResult(result);
        //mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    /**
     * 在浏览器中打开链接
     * @param url
     */
    private void doResult(String url) {
        WebActivity.startActivity(this, url);
        this.finish();
        addScaleEnter();
    }

    private void addScaleEnter(){
        overridePendingTransition(R.anim.enter_scale,0);
    }

    private void addScaleOut(){
        overridePendingTransition(0,R.anim.out_scale);
    }
}
