package tech.xiaosuo.com.viewdraghelpersample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DragViewSample extends LinearLayout  {

    private static final String TAG = "DragViewSample";
    ImageView dragImgView = null;
    ViewDragHelper viewDragHelper = null;
    public DragViewSample(Context context) {
        super(context);
        initView();
    }

    public DragViewSample(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewSample(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        viewDragHelper = ViewDragHelper.create(this,callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG," onFinishInflate");
        dragImgView = findViewById(R.id.drag_img_view);
    }



    Callback callback = new Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            boolean result = false;
            if(dragImgView == view){
                result = true;
            }
            Log.d(TAG," tryCaptureView result: " + result);
            return result;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if(dragImgView.getLeft() < 500){
                viewDragHelper.smoothSlideViewTo(dragImgView,0,0);
                invalidate();
            }else{
                viewDragHelper.smoothSlideViewTo(dragImgView,300,0);
                invalidate();
            }
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG," onInterceptTouchEvent");
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG," onTouchEvent");
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            Log.d(TAG," computeScroll");
            invalidate();
        }
    }

/*
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
*/


}
