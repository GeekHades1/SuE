package org.hades.sue.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import org.hades.sue.R;

/**
 * Created by Hades on 2017/10/31.
 *
 * 翻页动画View
 */

public class RotatePageView extends View implements Animator.AnimatorListener{

    private int duration; //动画持续时间

    //Y轴方向旋转角度
    private float degreeY;
    //不变的那一半，Y轴方向旋转角度
    private float fixDegreeY;
    //Z轴方向（平面内）旋转的角度
    private float degreeZ;

    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;
    AnimatorSet set = new AnimatorSet();

    private OnAnimatorEndListener listener;

    public interface OnAnimatorEndListener{
        void animEnd();
    }

    public RotatePageView(Context context) {
        this(context,null);
    }

    public RotatePageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotatePageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RotatePageView);
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.RotatePageView_rpv_background);
        duration = a.getInteger(R.styleable.RotatePageView_rpv_duration, 1000);
        a.recycle();

        if (drawable != null) {
            bitmap = drawable.getBitmap();
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sue_top);
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
        initAnimator();
    }

    public void setEndListener(OnAnimatorEndListener listener){
        this.listener = listener;
    }

    public void startAnim(){
        if (!set.isRunning()){
            set.start();
        }
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "degreeY", 0, -45);
        animator1.setDuration(duration);
        //animator1.setStartDelay(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "degreeZ", 0, 270);
        animator2.setDuration(duration-200);
        //animator2.setStartDelay(500);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(this, "fixDegreeY", 0, 30);
        animator3.setDuration(duration-500);
        //animator3.setStartDelay(500);

        set.addListener(this);
        set.playSequentially(animator1, animator2, animator3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //画变换的一半
        //先旋转，再裁切，再使用camera执行3D动效,**然后保存camera效果**,最后再旋转回来
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        camera.rotateY(degreeY);
        camera.applyToCanvas(canvas);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //画不变换的另一半
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
        camera.rotateY(fixDegreeY);
        camera.applyToCanvas(canvas);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }

    /**
     * 启动动画之前调用，把参数reset到初始状态
     */
    public void reset() {
        degreeY = 0;
        fixDegreeY = 0;
        degreeZ = 0;
    }

    @Keep
    public void setFixDegreeY(float fixDegreeY) {
        this.fixDegreeY = fixDegreeY;
        invalidate();
    }

    @Keep
    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @Keep
    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (listener != null) {
            listener.animEnd();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
