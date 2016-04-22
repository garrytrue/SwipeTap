package com.tiv.swipetap.callback;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by tiv on 22.04.2016.
 */
    public class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener {
    private static final String TAG = RecyclerItemTouchListener.class.getSimpleName();
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent() called with: " + "rv = [" + rv + "], e = [" + e + "]");
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onTouchEvent() called with: " + "rv = [" + rv + "], e = [" + e + "]");

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(TAG, "onRequestDisallowInterceptTouchEvent() called with: " + "disallowIntercept = [" + disallowIntercept + "]");

    }
}
