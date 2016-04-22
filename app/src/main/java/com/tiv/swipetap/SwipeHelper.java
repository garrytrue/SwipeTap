package com.tiv.swipetap;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.tiv.swipetap.callback.OnUpperViewGestureListener;

/**
 * Created by tiv on 22.04.2016.
 */
public class SwipeHelper extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = SwipeHelper.class.getSimpleName();
    public static final int SCROLL_Y_THRESHOLD = 15;
    public static final int SCROLL_X_THRESHOLD = 10;
    private View swipedView;
    private boolean isSelectMode = false;
    private int touchedPosition;
    private OnUpperViewGestureListener listener;


    private SwipeHelper(OnUpperViewGestureListener listener) {
        this.listener = listener;
    }

    public static SwipeHelper makeSwipeHelper( OnUpperViewGestureListener listener) {
        return new SwipeHelper(listener);
    }
    public void setTargetView(View targetView){
        swipedView = targetView.findViewById(R.id.swiped_view);
    }

    public void setTouchedPosition(int touchedPosition) {
        this.touchedPosition = touchedPosition;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        listener.onClick(touchedPosition);
        if(isSelectMode){
            Log.d(TAG, "onSingleTapConfirmed: SELECT MODE");
        }else{
            Log.d(TAG, "onSingleTapConfirmed() called with: " + "e = [" + e + "]");
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll() called with: " + "e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
        if(Math.abs(distanceY) > SCROLL_Y_THRESHOLD){
            Log.d(TAG, "onScroll: ON List");
            return false;
        }
        if(Math.abs(distanceX) > SCROLL_X_THRESHOLD){
            Log.d(TAG, "onScroll: OPEN ITEM");
            swipedView.animate().x(distanceX > 0 ?  -(swipedView.getContext().getResources().getDimension(R.dimen.list_item_height)):0).setDuration(50);
            return true;
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress() called with: " + "e = [" + e + "]");
        isSelectMode = true;
//        listener.onClick(touchedPosition, isSelectMode);
    }
}
