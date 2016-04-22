package com.tiv.swipetap.callback;

import com.tiv.swipetap.ItemHolder;

/**
 * Created by tiv on 22.04.2016.
 */
public interface OnUpperViewGestureListener {
    void OnLongClick(int position);
    void onClick(int position);
    void swipedAction(ItemHolder item);

}
