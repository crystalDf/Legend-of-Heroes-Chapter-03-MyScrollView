package com.star.myscrollview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;


public class MyScrollView extends ViewGroup {

    private int mScreenHeight;
    private Scroller mScroller;

    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeight * childCount;

        setLayoutParams(marginLayoutParams);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            if (childView.getVisibility() != View.GONE) {
                childView.layout(l, mScreenHeight * i, r, mScreenHeight * (i + 1));
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();

                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                int dy = mLastY - y;

                if ((getScrollY() < 0) || (getScrollY() > (getHeight() - mScreenHeight))) {
                    dy = 0;
                }

                scrollBy(0, dy);

                mLastY = y;

                break;


        }



        return super.onTouchEvent(event);
    }

    private void init(Context context) {

        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        mScreenHeight = displayMetrics.heightPixels;
        mScroller = new Scroller(context);

    }

}
