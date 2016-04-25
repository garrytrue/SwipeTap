package com.tiv.swipetap;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tiv.swipetap.callback.OnItemViewClickListener;
import com.tiv.swipetap.callback.OnUpperViewGestureListener;

/**
 * Created by tiv on 22.04.2016.
 */
public class ItemHolder extends RecyclerView.ViewHolder {
    private static final String TAG = ItemHolder.class.getSimpleName();
    private View swipedView, downView;
    private TextView swipedViewTextLabel;
    private OnUpperViewGestureListener onUpperViewGestureListener;

    private ItemHolder(View itemView, OnItemViewClickListener listener, OnUpperViewGestureListener onUpperViewGestureListener) {
        super(itemView);
        initView(itemView, listener);
        this.onUpperViewGestureListener = onUpperViewGestureListener;

    }

    public static ItemHolder makeHolder(View item, OnItemViewClickListener listener, OnUpperViewGestureListener onUpperViewGestureListener) {
        return new ItemHolder(item, listener, onUpperViewGestureListener);
    }

    private void initView(View itemView, final OnItemViewClickListener listener) {
        swipedView = itemView.findViewById(R.id.swiped_view);
        final GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(swipedView.getContext(), simpleOnGestureListener);
        swipedView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return true;
            }
        });
        downView = itemView.findViewById(R.id.down_view);
        downView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDownViewTap(getAdapterPosition());
            }
        });
        swipedViewTextLabel = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void setText(String text) {
        swipedViewTextLabel.setText(text);
    }

    public void makeSelected(boolean isSelected) {
        swipedView.setBackgroundColor(isSelected ? swipedView.getResources().getColor(R.color.colorAccent) : swipedView.getResources().getColor(android.R.color.darker_gray));
    }

    public void triggerSwipeAction(boolean isOpen) {
        swipedView.animate().x(isOpen ? -(swipedView.getContext().getResources().getDimension(R.dimen.list_item_height)) : 0).setDuration(50);
        if (isOpen) {
            onUpperViewGestureListener.swipedAction(this);
        }
    }

    private final GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public static final int SCROLL_Y_THRESHOLD = 15;
        public static final int SCROLL_X_THRESHOLD = 10;
        private boolean isSelectMode = false;

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onUpperViewGestureListener.onClick(getAdapterPosition());
            if (isSelectMode) {
                Log.d(TAG, "onSingleTapConfirmed: SELECT MODE");
            } else {
                Log.d(TAG, "onSingleTapConfirmed() called with: " + "e = [" + e + "]");
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll() called with: " + "e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
            if (Math.abs(distanceY) > SCROLL_Y_THRESHOLD) {
                Log.d(TAG, "onScroll: ON List");
                return false;
            }
            if (Math.abs(distanceX) > SCROLL_X_THRESHOLD) {
                Log.d(TAG, "onScroll: OPEN ITEM");
                triggerSwipeAction(distanceX > 0);
                return true;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress() called with: " + "e = [" + e + "]");
            onUpperViewGestureListener.OnLongClick(getAdapterPosition());
        }

    };
}
