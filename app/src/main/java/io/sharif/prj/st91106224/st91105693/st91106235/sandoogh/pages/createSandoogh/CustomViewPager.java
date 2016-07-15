package io.sharif.prj.st91106224.st91105693.st91106235.sandoogh.pages.createSandoogh;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.enabled = true;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return this.enabled && detectSwipeToRight(event) && super.onTouchEvent(event);
//
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return this.enabled && detectSwipeToRight(event) && super.onInterceptTouchEvent(event);
//    }

    public boolean detectSwipeToRight(MotionEvent event) {

        int initialXValue = 0;
        final int SWIPE_THRESHOLD = 100;
        boolean result = false;

        try {
            float diffX = event.getX() - initialXValue;
            if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                result = diffX <= 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}