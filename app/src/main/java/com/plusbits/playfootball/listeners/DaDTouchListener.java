package com.plusbits.playfootball.listeners;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mberengueras on 27/09/2016.
 */

public class DaDTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            return true;
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}
