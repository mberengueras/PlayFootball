package com.plusbits.playfootball.listeners;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.plusbits.playfootball.activities.DetailPlayerActivity;

/**
 * Created by mberengueras on 27/09/2016.
 */

public class DaDTouchListener implements View.OnTouchListener {
    private boolean shouldClick;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("onTouch", String.valueOf(motionEvent.getAction()));
        Log.i("onTouch", "MotionEvent.ACTION_DOWN: " + String.valueOf(MotionEvent.ACTION_DOWN));
        Log.i("onTouch", "MotionEvent.ACTION_MOVE: " + String.valueOf(MotionEvent.ACTION_MOVE));
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);

            shouldClick = true;
            return true;
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            shouldClick = false;
            return true;
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (shouldClick) view.performClick();
            return true;
        }
        else {
            return false;
        }
    }

    private void goToDetailPlayer(View view){
        Intent intent=new Intent(view.getContext(),DetailPlayerActivity.class);
        view.getContext().startActivity(intent);
    }
}
